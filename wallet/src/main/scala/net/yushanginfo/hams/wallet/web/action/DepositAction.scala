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

package net.yushanginfo.hams.wallet.web.action

import net.yushanginfo.hams.base.model.*
import net.yushanginfo.hams.base.service.InpatientService
import net.yushanginfo.hams.code.model.InpatientStatus
import net.yushanginfo.hams.wallet.helper.DepositImportListener
import net.yushanginfo.hams.wallet.model.Deposit
import org.beangle.commons.activation.MediaTypes
import org.beangle.commons.lang.Strings
import org.beangle.data.excel.schema.ExcelSchema
import org.beangle.data.transfer.importer.ImportSetting
import org.beangle.web.action.view.{Stream, View}
import org.beangle.webmvc.support.action.{ExportSupport, ImportSupport, RestfulAction}

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

class DepositAction extends RestfulAction[Deposit], ImportSupport[Deposit], ExportSupport[Deposit] {

  var inpatientService: InpatientService = _

  override protected def indexSetting(): Unit = {
    put("wards", entityDao.getAll(classOf[Ward]))
    put("statues", entityDao.getAll(classOf[InpatientStatus]))
  }

  override protected def saveAndRedirect(deposit: Deposit): View = {
    if (!deposit.persisted && !Strings.isEmpty(deposit.inpatient.code)) {
      entityDao.findBy(classOf[Inpatient], "code", deposit.inpatient.code).headOption match {
        case None => return redirect("index", "不正确的住院号")
        case Some(i) => deposit.inpatient = i
      }
    }
    super.saveAndRedirect(deposit)
  }

  def downloadTemplate(): View = {
    val schema = new ExcelSchema()
    val sheet = schema.createScheet("数据模板")
    sheet.title("住院押金信息模板")
    sheet.remark("特别说明：\n1、不可改变本表格的行列结构以及批注，否则将会导入失败！\n2、必须按照规格说明的格式填写。\n3、可以多次导入，重复的信息会被新数据更新覆盖。\n4、保存的excel文件名称可以自定。")
    sheet.add("住院号", "deposit.inpatient.code").length(10).required().remark("≤10位")
    sheet.add("缴费日期", "deposit.payAt").remark("yyyy-MM-dd").required()
    sheet.add("缴费金额", "deposit.amount")

    val os = new ByteArrayOutputStream()
    schema.generate(os)
    Stream(new ByteArrayInputStream(os.toByteArray), MediaTypes.ApplicationXlsx.toString, "住院押金.xlsx")
  }

  protected override def configImport(setting: ImportSetting): Unit = {
    setting.listeners = List(new DepositImportListener(inpatientService, entityDao))
  }
}
