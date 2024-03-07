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

import net.yushanginfo.hams.account.model.SubsidyIncome
import net.yushanginfo.hams.account.service.SubsidyService
import net.yushanginfo.hams.account.web.helper.SubsidyIncomeImportListener
import net.yushanginfo.hams.base.model.{Inpatient, Ward}
import net.yushanginfo.hams.base.service.InpatientService
import org.beangle.commons.activation.MediaTypes
import org.beangle.data.dao.OqlBuilder
import org.beangle.data.excel.schema.ExcelSchema
import org.beangle.data.transfer.importer.ImportSetting
import org.beangle.web.action.view.{Stream, View}
import org.beangle.webmvc.support.action.{ExportSupport, ImportSupport, RestfulAction}
import org.beangle.webmvc.support.helper.QueryHelper

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

class SubsidyIncomeAction extends RestfulAction[SubsidyIncome], ImportSupport[SubsidyIncome], ExportSupport[SubsidyIncome] {
  var subsidyService: SubsidyService = _
  var inpatientService: InpatientService = _

  override protected def simpleEntityName: String = "income"

  override protected def indexSetting(): Unit = {
    put("wards", entityDao.getAll(classOf[Ward]))
    super.indexSetting()
  }

  override protected def getQueryBuilder: OqlBuilder[SubsidyIncome] = {
    val query = super.getQueryBuilder
    QueryHelper.dateBetween(query, null, "payAt", "beginAt", "endAt")
    query
  }

  override protected def saveAndRedirect(income: SubsidyIncome): View = {
    val payAt = getInstant("payAt").get
    val minPayAt = income.updatePayAt(payAt)

    val inpatientId = getLong("inpatient.id")
    if (!income.persisted && inpatientId.nonEmpty) {
      val inpatient = entityDao.get(classOf[Inpatient], inpatientId.get)
      val account = subsidyService.getOrCreate(inpatient, payAt)
      val newI = account.newIncome(income.amount, income.payAt, income.channel)
      entityDao.saveOrUpdate(account, newI)
      subsidyService.adjustBalance(account, minPayAt)
      redirect("search", "info.save.success")
    } else {
      subsidyService.adjustBalance(income.account, minPayAt)
      super.saveAndRedirect(income)
    }
  }

  def downloadTemplate(): View = {
    val schema = new ExcelSchema()
    val sheet = schema.createScheet("数据模板")
    sheet.title("养护补贴入账模板")
    sheet.remark("特别说明：\n1、不可改变本表格的行列结构以及批注，否则将会导入失败！\n2、必须按照规格说明的格式填写。\n3、可以多次导入，重复的信息会被新数据更新覆盖。\n4、保存的excel文件名称可以自定。")
    sheet.add("姓名", "subsidyIncome.account.inpatient.name").length(10).required().remark("≤10位")
    sheet.add("数额", "subsidyIncome.amount")
    sheet.add("入账时间", "subsidyIncome.payAt")
    sheet.add("来源渠道", "subsidyIncome.channel")

    val os = new ByteArrayOutputStream()
    schema.generate(os)
    Stream(new ByteArrayInputStream(os.toByteArray), MediaTypes.ApplicationXlsx.toString, "养护补贴入账.xlsx")
  }

  protected override def configImport(setting: ImportSetting): Unit = {
    setting.listeners = List(new SubsidyIncomeImportListener(inpatientService, subsidyService, entityDao))
  }
}
