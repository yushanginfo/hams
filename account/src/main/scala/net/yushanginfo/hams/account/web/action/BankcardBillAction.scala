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

import net.yushanginfo.hams.account.model.{Bankcard, BankcardBill}
import net.yushanginfo.hams.base.model.Ward
import net.yushanginfo.hams.base.service.InpatientService
import net.yushanginfo.hams.code.model.IncomeChannel
import net.yushanginfo.hams.wallet.model.WalletType
import net.yushanginfo.hams.wallet.service.WalletService
import org.beangle.commons.collection.Properties
import org.beangle.commons.lang.Strings
import org.beangle.data.dao.OqlBuilder
import org.beangle.web.action.annotation.response
import org.beangle.web.action.view.View
import org.beangle.webmvc.support.action.{ExportSupport, ImportSupport, RestfulAction}
import org.beangle.webmvc.support.helper.QueryHelper

/**
 * 银行卡支出
 */
class BankcardBillAction extends RestfulAction[BankcardBill], ImportSupport[BankcardBill], ExportSupport[BankcardBill] {
  var walletService: WalletService = _

  var inpatientService: InpatientService = _

  override protected def simpleEntityName: String = "bill"

  override protected def indexSetting(): Unit = {
    put("wards", entityDao.getAll(classOf[Ward]))
    super.indexSetting()
  }

  override protected def editSetting(bill: BankcardBill): Unit = {
    if (!bill.persisted) bill.expenses = "转零用金"
    super.editSetting(bill)
  }

  override protected def getQueryBuilder: OqlBuilder[BankcardBill] = {
    val query = super.getQueryBuilder
    QueryHelper.dateBetween(query, null, "payAt", "beginAt", "endAt")
    query
  }

  override protected def saveAndRedirect(bill: BankcardBill): View = {
    if (!bill.persisted && null != bill.account && bill.account.persisted) {
      bill.account = entityDao.get(classOf[Bankcard], bill.account.id)
    }
    bill.fixBillAmount()
    if (!bill.persisted) {
      val bankcard = entityDao.get(classOf[Bankcard], bill.account.id)
      val newBill = bankcard.newBill(bill.amount, bill.payAt, bill.expenses)
      entityDao.saveOrUpdate(bankcard, newBill)
      bill.expenses.trim match
        case "转零用金" =>
          walletService.getWallet(bankcard.inpatient.code, WalletType.Change) foreach { w =>
            val income = w.newIncome(bill.amount, bill.payAt, new IncomeChannel(IncomeChannel.FromBank))
            entityDao.saveOrUpdate(income, w)
            walletService.adjustBalance(w, bill.payAt)
          }
          bill.toWallet = Some(WalletType.Change)
        case _ =>
      super.saveAndRedirect(newBill)
    } else {
      super.saveAndRedirect(bill)
    }
  }

  @response
  def cardNos(): Properties = {
    val rs = new Properties()
    val code = get("inpatientCode", "")
    if (Strings.isNotBlank(code)) {
      inpatientService.getInpatient(code) foreach { p =>
        rs.put("person", p.name + " " + p.person.idcard.getOrElse(""))
        val cards = entityDao.findBy(classOf[Bankcard], "inpatient", p)
        val cardInfos = cards.map { x =>
          val p = new Properties(x, "id", "bank", "cardNo")
          p.put("balance", x.balance.toString())
          p
        }
        rs.put("cards", cardInfos)
      }
    }
    rs
  }

}
