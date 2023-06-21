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

package net.yushanginfo.hams.base.web.action

import net.yushanginfo.hams.base.model.*
import net.yushanginfo.hams.base.service.CodeService
import net.yushanginfo.hams.base.web.helper.InpatientImporterListener
import net.yushanginfo.hams.code.model.*
import net.yushanginfo.hams.wallet.model.LeaveApply
import org.beangle.commons.activation.{MediaType, MediaTypes}
import org.beangle.commons.lang.Strings
import org.beangle.data.dao.OqlBuilder
import org.beangle.data.excel.schema.ExcelSchema
import org.beangle.data.transfer.importer.listener.ForeignerListener
import org.beangle.data.transfer.importer.{ImportSetting, MultiEntityImporter}
import org.beangle.web.action.annotation.response
import org.beangle.web.action.view.{Stream, View}
import org.beangle.webmvc.support.action.{ExportSupport, ImportSupport, RestfulAction}
import org.beangle.webmvc.support.helper.PopulateHelper

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import java.time.{Instant, LocalDateTime, ZoneId}

class InpatientSearchAction extends RestfulAction[Inpatient], ExportSupport[Inpatient], ImportSupport[Inpatient] {

  var codeService: CodeService = _

  override protected def indexSetting(): Unit = {
    put("wards", entityDao.getAll(classOf[Ward]))
    put("statuses", codeService.get(classOf[InpatientStatus]))
    put("feeOrigins", codeService.get(classOf[FeeOrigin]))
  }

  /** 在院统计
   *
   * @return
   */
  def inhospitalStat(): View = {
    val today = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant
    val q = OqlBuilder.from(classOf[Inpatient], "i")
    q.where("i.endAt is null or i.endAt > :today", today)
    q.select("i.ward.id,i.ward.name,count(*)")
    q.groupBy("i.ward.id,i.ward.name")
    q.orderBy("i.ward.id")
    val data = entityDao.search(q)

    val q1 = OqlBuilder.from(classOf[Inpatient], "i")
    q1.where("i.endAt > :today", today)
    q1.select("count(*)")
    val discharging = entityDao.search(q1)

    val q2 = OqlBuilder.from(classOf[LeaveApply], "i")
    q2.where(":today between i.leaveAt and i.backAt", today)
    q2.select("count(*)")
    val leaving = entityDao.search(q2)

    val q3 = OqlBuilder.from(classOf[Inpatient], "i")
    q3.where("i.beginAt > :today", today)
    q3.select("count(*)")
    val newer = entityDao.search(q3)

    put("data", data)
    put("discharging", discharging.headOption.getOrElse(0))
    put("leaving", leaving.headOption.getOrElse(0))
    put("newer", newer.headOption.getOrElse(0))

    forward()
  }

}
