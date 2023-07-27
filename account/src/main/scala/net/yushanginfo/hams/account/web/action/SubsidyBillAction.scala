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

import net.yushanginfo.hams.account.model.{Subsidy, SubsidyBill}
import net.yushanginfo.hams.base.model.{Inpatient, Ward}
import net.yushanginfo.hams.code.model.IncomeChannel
import net.yushanginfo.hams.wallet.model.{Deposit, Income, Wallet, WalletType}
import org.beangle.commons.lang.Strings
import org.beangle.data.dao.OqlBuilder
import org.beangle.web.action.view.View
import org.beangle.webmvc.support.action.{ExportSupport, ImportSupport, RestfulAction}
import org.beangle.webmvc.support.helper.QueryHelper

/**
 * 伙食费支出
 */
class SubsidyBillAction extends RestfulAction[SubsidyBill], ImportSupport[SubsidyBill], ExportSupport[SubsidyBill] {
  override protected def simpleEntityName: String = "bill"

  override protected def indexSetting(): Unit = {
    put("wards", entityDao.getAll(classOf[Ward]))
    super.indexSetting()
  }

  override protected def getQueryBuilder: OqlBuilder[SubsidyBill] = {
    val query = super.getQueryBuilder
    QueryHelper.dateBetween(query, null, "payAt", "beginAt", "endAt")
    query
  }
  override protected def saveAndRedirect(bill: SubsidyBill): View = {
    if (!bill.persisted && !Strings.isEmpty(bill.account.inpatient.code)) {
      entityDao.findBy(classOf[Inpatient], "code", bill.account.inpatient.code).headOption match {
        case None => return redirect("index", "不正确的住院号")
        case Some(i) =>
          val q = OqlBuilder.from(classOf[Subsidy], "wallet")
          q.where("wallet.inpatient=:inpatient", i)
          val s = entityDao.search(q).headOption match {
            case Some(w) => w
            case None => return redirect("index", s"不存在${i.name}的养护补贴账户")
          }
          bill.account = s
      }
    }
    val subsidy = entityDao.get(classOf[Subsidy], bill.account.id)
    bill.balance = subsidy.balance - bill.amount
    subsidy.balance = bill.balance
    entityDao.saveOrUpdate(subsidy, bill)

    bill.expenses.trim match
      case "转零用金" =>
        val wallet = entityDao.findBy(classOf[Wallet], "inpatient" -> subsidy.inpatient, "walletType" -> WalletType.Change).headOption
        wallet foreach { w =>
          val income = w.newIncome(bill.amount, bill.payAt, new IncomeChannel(IncomeChannel.FromSubsidy))
          entityDao.saveOrUpdate(income, wallet)
        }
        bill.toWallet = Some(WalletType.Change)
      case "转伙食费" =>
        val wallet = entityDao.findBy(classOf[Wallet], "inpatient" -> subsidy.inpatient, "walletType" -> WalletType.Meal).headOption
        wallet foreach { w =>
          val income = w.newIncome(bill.amount, bill.payAt, new IncomeChannel(IncomeChannel.FromSubsidy))
          entityDao.saveOrUpdate(income, wallet)
        }
        bill.toWallet = Some(WalletType.Meal)
      case _ =>
    super.saveAndRedirect(bill)
  }

}
