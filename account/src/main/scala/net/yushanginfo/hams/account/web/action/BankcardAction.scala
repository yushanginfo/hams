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

import net.yushanginfo.hams.account.model.{Bankcard, BankcardBill, BankcardIncome}
import net.yushanginfo.hams.account.service.BankcardService
import net.yushanginfo.hams.base.model.{Inpatient, Ward, Yuan}
import net.yushanginfo.hams.base.service.InpatientService
import net.yushanginfo.hams.code.model.BankcardIncomeCategory
import org.beangle.commons.collection.Properties
import org.beangle.commons.lang.Strings
import org.beangle.web.action.annotation.response
import org.beangle.web.action.view.View
import org.beangle.webmvc.support.action.{ExportSupport, ImportSupport, RestfulAction}

import java.time.Instant

class BankcardAction extends RestfulAction[Bankcard], ImportSupport[Bankcard], ExportSupport[Bankcard] {

  var bankcardService: BankcardService = _
  var inpatientService: InpatientService = _

  override protected def indexSetting(): Unit = {
    put("wards", entityDao.getAll(classOf[Ward]))
    super.indexSetting()
  }

  override protected def saveAndRedirect(bankcard: Bankcard): View = {
    if (!bankcard.persisted && !Strings.isEmpty(bankcard.inpatient.code)) {
      entityDao.findBy(classOf[Inpatient], "code", bankcard.inpatient.code).headOption match {
        case None => return redirect("index", "不正确的住院号")
        case Some(i) => bankcard.inpatient = i
      }
      bankcard.balance = bankcard.initBalance
    }
    entityDao.saveOrUpdate(bankcard)
    get("current_balance") foreach { b =>
      if (b != bankcard.balance.toString()) {
        val cur = Yuan(b)
        val offset = cur.value - bankcard.balance.value
        if (offset < 0) {
          val bill = new BankcardBill()
          bill.account = bankcard
          bill.payAt = Instant.now
          bill.amount = new Yuan(offset)
          bill.expenses = "余额差值"
          bill.updatedAt = Instant.now
          bill.balance = cur
          bankcard.balance = cur
          entityDao.saveOrUpdate(bankcard, bill)
        } else {
          val in = new BankcardIncome
          in.account = bankcard
          in.payAt = Instant.now
          in.amount = new Yuan(offset)
          in.category = new BankcardIncomeCategory(BankcardIncomeCategory.Mics)
          in.updatedAt = Instant.now
          in.balance = cur
          bankcard.balance = cur
          entityDao.saveOrUpdate(bankcard, in)
        }
      }
    }
    super.saveAndRedirect(bankcard)
  }

  @response
  def inpatients(): Properties = {
    val rs = new Properties()
    val code = get("inpatientCode", "")
    if (Strings.isNotBlank(code)) {
      inpatientService.getInpatient(code) foreach { p =>
        rs.put("id", p.id.toString)
        rs.put("person", p.name + " " + p.person.idcard.getOrElse(""))
      }
    }
    rs
  }
}
