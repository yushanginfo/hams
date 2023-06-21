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

import net.yushanginfo.hams.account.model.Subsidy
import net.yushanginfo.hams.account.service.SubsidyService
import net.yushanginfo.hams.base.model.{Inpatient, Ward}
import org.beangle.commons.collection.Collections
import org.beangle.commons.lang.Strings
import org.beangle.data.dao.OqlBuilder
import org.beangle.web.action.view.View
import org.beangle.webmvc.support.action.{ExportSupport, ImportSupport, RestfulAction}

import java.time.{LocalDate, Year, YearMonth}
import scala.collection.mutable

class SubsidyAction extends RestfulAction[Subsidy], ImportSupport[Subsidy], ExportSupport[Subsidy] {

  var subsidyService: SubsidyService = _

  override protected def indexSetting(): Unit = {
    put("wards", entityDao.getAll(classOf[Ward]))
    super.indexSetting()
  }

  override protected def saveAndRedirect(subsidy: Subsidy): View = {
    if (!subsidy.persisted && !Strings.isEmpty(subsidy.inpatient.code)) {
      entityDao.findBy(classOf[Inpatient], "code", subsidy.inpatient.code).headOption match {
        case None => return redirect("index", "不正确的住院号")
        case Some(i) => subsidy.inpatient = i
      }
      subsidy.balance = subsidy.initBalance
    }
    super.saveAndRedirect(subsidy)
  }

}
