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

import net.yushanginfo.hams.base.model.{Inpatient, Ward}
import net.yushanginfo.hams.base.service.InpatientService
import net.yushanginfo.hams.code.model.LeaveCategory
import net.yushanginfo.hams.wallet.model.LeaveApply
import net.yushanginfo.hams.wallet.service.WalletService
import org.beangle.data.dao.OqlBuilder
import org.beangle.web.action.view.View
import org.beangle.webmvc.support.action.RestfulAction

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AuditAction extends RestfulAction[LeaveApply] {

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

  def audit(): View = {
    val applies = entityDao.find(classOf[LeaveApply], getLongIds("apply"))
    getBoolean("approved") foreach { result =>
      applies foreach { apply =>
        apply.approved = Some(result)
      }
    }
    entityDao.saveOrUpdate(applies)
    redirect("search", "审核完毕")
  }
}
