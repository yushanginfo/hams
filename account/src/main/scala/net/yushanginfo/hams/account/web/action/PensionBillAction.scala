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
import net.yushanginfo.hams.account.service.PensionService
import net.yushanginfo.hams.base.model.{Inpatient, Ward}
import net.yushanginfo.hams.code.model.IncomeChannel
import net.yushanginfo.hams.wallet.model.{Wallet, WalletType}
import net.yushanginfo.hams.wallet.service.WalletService
import org.beangle.data.dao.OqlBuilder
import org.beangle.web.action.view.View
import org.beangle.webmvc.support.action.{ExportSupport, ImportSupport, RestfulAction}
import org.beangle.webmvc.support.helper.QueryHelper

/**
 * 养老金支出
 */
class PensionBillAction extends RestfulAction[PensionBill], ImportSupport[PensionBill], ExportSupport[PensionBill] {
  override protected def simpleEntityName: String = "bill"

  var pensionService: PensionService = _

  var walletService: WalletService = _

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
    val payAt = getInstant("payAt").get
    val minPayAt = bill.updatePayAt(payAt)

    val inpatientId = getLong("inpatient.id")

    bill.fixBillAmount()
    if (!bill.persisted && inpatientId.nonEmpty) {
      val i = entityDao.get(classOf[Inpatient], inpatientId.get)
      val q = OqlBuilder.from(classOf[Pension], "p")
      q.where("p.inpatient=:inpatient", i)
      val p = entityDao.search(q).headOption match {
        case Some(w) => w
        case None => return redirect("index", s"不存在${i.name}的养老金账户")
      }
      bill.account = p
    }

    entityDao.saveOrUpdate(bill)
    pensionService.adjustBalance(bill.account, minPayAt)

    val pension = bill.account
    bill.expenses.trim match
      case "转零用金" =>
        val wallet = entityDao.findBy(classOf[Wallet], "inpatient" -> pension.inpatient, "walletType" -> WalletType.Change).headOption
        wallet foreach { w =>
          val income = w.newIncome(bill.amount, bill.payAt, new IncomeChannel(IncomeChannel.FromPension))
          entityDao.saveOrUpdate(income, wallet)
          walletService.adjustBalance(w, payAt)
        }
        bill.toWallet = Some(WalletType.Change)
      case "转伙食费" =>
        val wallet = entityDao.findBy(classOf[Wallet], "inpatient" -> pension.inpatient, "walletType" -> WalletType.Meal).headOption
        wallet foreach { w =>
          val income = w.newIncome(bill.amount, bill.payAt, new IncomeChannel(IncomeChannel.FromPension))
          entityDao.saveOrUpdate(income, wallet)
          walletService.adjustBalance(w, payAt)
        }
        bill.toWallet = Some(WalletType.Meal)
      case _ =>
    super.saveAndRedirect(bill)
  }

}
