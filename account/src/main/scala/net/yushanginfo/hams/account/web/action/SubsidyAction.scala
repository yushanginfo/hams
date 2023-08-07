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
import net.yushanginfo.hams.account.web.helper.SubsidyImportListener
import net.yushanginfo.hams.base.model.{Inpatient, Ward}
import net.yushanginfo.hams.base.service.InpatientService
import org.beangle.commons.activation.MediaTypes
import org.beangle.commons.collection.Collections
import org.beangle.commons.lang.Strings
import org.beangle.data.dao.OqlBuilder
import org.beangle.data.excel.schema.ExcelSchema
import org.beangle.data.transfer.importer.ImportSetting
import org.beangle.web.action.view.{Stream, View}
import org.beangle.webmvc.support.action.{ExportSupport, ImportSupport, RestfulAction}

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import java.time.{LocalDate, Year, YearMonth}
import scala.collection.mutable

class SubsidyAction extends RestfulAction[Subsidy], ImportSupport[Subsidy], ExportSupport[Subsidy] {

  var inpatientService: InpatientService = _
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

  def downloadTemplate(): View = {
    val schema = new ExcelSchema()
    val sheet = schema.createScheet("数据模板")
    sheet.title("养护补贴信息模板")
    sheet.remark("特别说明：\n1、不可改变本表格的行列结构以及批注，否则将会导入失败！\n2、必须按照规格说明的格式填写。\n3、可以多次导入，重复的信息会被新数据更新覆盖。\n4、保存的excel文件名称可以自定。")
    sheet.add("姓名", "subsidy.inpatient.name").length(10).required().remark("≤10位")
    sheet.add("当前余额", "subsidy.balance")
    sheet.add("起始余额", "subsidy.initBalance")
    sheet.add("起始日期", "subsidy.createdOn")

    val os = new ByteArrayOutputStream()
    schema.generate(os)
    Stream(new ByteArrayInputStream(os.toByteArray), MediaTypes.ApplicationXlsx.toString, "养护补贴.xlsx")
  }

  protected override def configImport(setting: ImportSetting): Unit = {
    setting.listeners = List(new SubsidyImportListener(inpatientService, entityDao))
  }

}
