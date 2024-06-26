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

package net.yushanginfo.hams.leave.web.action

import net.yushanginfo.hams.account.model.AttendFee
import net.yushanginfo.hams.base.model.{Inpatient, Ward, Yuan}
import net.yushanginfo.hams.base.service.InpatientService
import net.yushanginfo.hams.code.model.LeaveCategory
import net.yushanginfo.hams.wallet.model.{LeaveApply, Wallet, WalletType}
import net.yushanginfo.hams.wallet.service.WalletService
import org.beangle.data.dao.OqlBuilder
import org.beangle.web.action.view.View
import org.beangle.webmvc.support.action.RestfulAction

import java.time.format.DateTimeFormatter
import java.time.{Instant, LocalDateTime}

class AdminAction extends RestfulAction[LeaveApply] {

  var inpatientService: InpatientService = _

  var walletService: WalletService = _

  override protected def indexSetting(): Unit = {
    put("wards", entityDao.getAll(classOf[Ward]))
    super.indexSetting()
  }

  override protected def getQueryBuilder: OqlBuilder[LeaveApply] = {
    val query = super.getQueryBuilder
    query
  }

  override protected def saveAndRedirect(apply: LeaveApply): View = {
    if (!apply.persisted) {
      apply.code = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now())
      entityDao.findBy(classOf[Inpatient], "code", apply.inpatient.code).headOption match {
        case None => return redirect("index", "不正确的住院号")
        case Some(i) => apply.inpatient = i
      }
    }

    super.saveAndRedirect(apply)
  }

  override protected def simpleEntityName: String = "apply"

  override def editSetting(entity: LeaveApply): Unit = {
    put("categories", entityDao.getAll(classOf[LeaveCategory]))
    super.editSetting(entity)
  }

  def dischargeForm(): View = {
    val inpatientCode = get("inpatient.code")
    val apply = new LeaveApply()
    if (inpatientCode.nonEmpty) {
      val p = inpatientService.getInpatient(inpatientCode.get)
      if (p.isEmpty) {
        addError("查不到该住院号")
      } else {
        if (p.get.endAt.isEmpty) {
          addError("该病人尚未出院")
        } else {
          val inpatient = p.get
          val mealBill = walletService.generateDischangeBill(inpatient)
          val changes = entityDao.findBy(classOf[Wallet], "inpatient" -> inpatient, "walletType" -> WalletType.Change)
          val attendFees = entityDao.findBy(classOf[AttendFee], "inpatient", inpatient)
          put("inpatient", inpatient)
          put("meals", List(mealBill.wallet))
          put("changes", changes)
          put("attendFees", attendFees)
          apply.inpatient = inpatient
        }
      }
    }

    put("apply", apply)
    put("categories", entityDao.findBy(classOf[LeaveCategory], "name", "出院"))
    forward()
  }

  def discharge(): View = {
    val inpatientCode = get("inpatient.code").get
    val inpatient = inpatientService.getInpatient(inpatientCode).get
    val apply = new LeaveApply()
    apply.inpatient = inpatient
    apply.leaveAt = inpatient.endAt.get
    apply.updatedAt = Instant.now
    apply.reasons = "出院"
    apply.category = entityDao.get(classOf[LeaveCategory], getIntId("apply.category"))
    apply.code = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now())
    val meals = entityDao.findBy(classOf[Wallet], "inpatient" -> inpatient, "walletType" -> WalletType.Meal)
    val changes = entityDao.findBy(classOf[Wallet], "inpatient" -> inpatient, "walletType" -> WalletType.Change)
    val attendFees = entityDao.findBy(classOf[AttendFee], "inpatient", inpatient)
    get("meal_withdrawal") foreach { w =>
      val withdrawal = Yuan(w)
      if (withdrawal.value > 0) {
        meals foreach { meal =>
          val bill = meal.newBill(withdrawal, inpatient.endAt.get, "出院退费")
          entityDao.saveOrUpdate(bill)
          walletService.adjustBalance(meal, bill.payAt)
        }
      }
    }
    entityDao.saveOrUpdate(apply)
    redirect("search", "办理成功")
  }
}
