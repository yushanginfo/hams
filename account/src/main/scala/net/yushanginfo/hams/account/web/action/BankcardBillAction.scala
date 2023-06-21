/*
 * Copyright (C) 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.yushanginfo.hams.account.web.action

import net.yushanginfo.hams.account.model.{Bankcard, BankcardBill, Pension}
import net.yushanginfo.hams.base.model.{Inpatient, Ward}
import net.yushanginfo.hams.code.model.IncomeChannel
import net.yushanginfo.hams.wallet.model.{Bill, Deposit, Wallet, WalletType}
import org.beangle.commons.lang.Strings
import org.beangle.data.dao.OqlBuilder
import org.beangle.web.action.view.View
import org.beangle.webmvc.support.action.{ExportSupport, ImportSupport, RestfulAction}

/**
 * 银行卡支出
 */
class BankcardBillAction extends RestfulAction[BankcardBill], ImportSupport[BankcardBill], ExportSupport[BankcardBill] {
  override protected def simpleEntityName: String = "bill"

  override protected def indexSetting(): Unit = {
    put("wards", entityDao.getAll(classOf[Ward]))
    super.indexSetting()
  }

  override protected def editSetting(bill: BankcardBill): Unit = {
    if (!bill.persisted) bill.expenses = "转零用金"
    super.editSetting(bill)
  }

  override protected def saveAndRedirect(bill: BankcardBill): View = {
    if (!bill.persisted && !Strings.isEmpty(bill.account.cardNo)) {
      entityDao.findBy(classOf[Bankcard], "cardNo", bill.account.cardNo).headOption match {
        case None => return redirect("index", "不正确的银行卡号")
        case Some(i) => bill.account = i
      }
    }
    val bankcard = entityDao.get(classOf[Bankcard], bill.account.id)
    bill.balance = bankcard.balance - bill.amount
    bankcard.balance = bill.balance
    entityDao.saveOrUpdate(bankcard, bill)

    bill.expenses.trim match
      case "转零用金" =>
        val wallet = entityDao.findBy(classOf[Wallet], "inpatient" -> bankcard.inpatient, "walletType" -> WalletType.Change).headOption
        wallet foreach { w =>
          val income = w.income(bill.amount, bill.payAt, new IncomeChannel(IncomeChannel.FromBank))
          entityDao.saveOrUpdate(income, wallet)
        }
        bill.toWallet = Some(WalletType.Change)
      case _ =>
    super.saveAndRedirect(bill)
  }

}
