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

import net.yushanginfo.hams.account.model.{Pension, PensionBill}
import net.yushanginfo.hams.base.model.{Inpatient, Ward}
import net.yushanginfo.hams.code.model.IncomeChannel
import net.yushanginfo.hams.wallet.model.{Bill, Deposit, Wallet, WalletType}
import org.beangle.commons.lang.Strings
import org.beangle.data.dao.OqlBuilder
import org.beangle.web.action.view.View
import org.beangle.webmvc.support.action.{ExportSupport, ImportSupport, RestfulAction}
import org.beangle.webmvc.support.helper.QueryHelper

/**
 * 养老金支出
 */
class PensionBillAction extends RestfulAction[PensionBill], ImportSupport[PensionBill], ExportSupport[PensionBill] {
  override protected def simpleEntityName: String = "bill"

  override protected def indexSetting(): Unit = {
    put("wards", entityDao.getAll(classOf[Ward]))
    super.indexSetting()
  }

  override protected def getQueryBuilder: OqlBuilder[PensionBill] = {
    val query = super.getQueryBuilder
    QueryHelper.dateBetween(query, null, "payAt", "beginAt", "endAt")
    query
  }
  override protected def saveAndRedirect(bill: PensionBill): View = {
    if (!bill.persisted && !Strings.isEmpty(bill.account.inpatient.code)) {
      entityDao.findBy(classOf[Inpatient], "code", bill.account.inpatient.code).headOption match {
        case None => return redirect("index", "不正确的住院号")
        case Some(i) =>
          val q = OqlBuilder.from(classOf[Pension], "wallet")
          q.where("wallet.inpatient=:inpatient", i)
          val p = entityDao.search(q).headOption match {
            case Some(w) => w
            case None => return redirect("index", s"不存在${i.name}的养老金账户")
          }
          bill.account = p
      }
    }
    val pension = entityDao.get(classOf[Pension], bill.account.id)
    bill.balance = pension.balance - bill.amount
    pension.balance = bill.balance
    entityDao.saveOrUpdate(pension, bill)

    bill.expenses.trim match
      case "转零用金" =>
        val wallet = entityDao.findBy(classOf[Wallet], "inpatient" -> pension.inpatient, "walletType" -> WalletType.Change).headOption
        wallet foreach { w =>
          val income = w.newIncome(bill.amount, bill.payAt, new IncomeChannel(IncomeChannel.FromPension))
          entityDao.saveOrUpdate(income, wallet)
        }
        bill.toWallet = Some(WalletType.Change)
      case "转伙食费" =>
        val wallet = entityDao.findBy(classOf[Wallet], "inpatient" -> pension.inpatient, "walletType" -> WalletType.Meal).headOption
        wallet foreach { w =>
          val income = w.newIncome(bill.amount, bill.payAt, new IncomeChannel(IncomeChannel.FromPension))
          entityDao.saveOrUpdate(income, wallet)
        }
        bill.toWallet = Some(WalletType.Meal)
      case _ =>
    super.saveAndRedirect(bill)
  }

}
