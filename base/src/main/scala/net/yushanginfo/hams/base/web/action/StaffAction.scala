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

import net.yushanginfo.hams.base.model.{Department, Staff}
import net.yushanginfo.hams.base.service.CodeService
import net.yushanginfo.hams.base.web.helper.{ActiveSearchHelper, StaffImportListener}
import net.yushanginfo.hams.code.model.{Gender, StaffType}
import org.beangle.commons.lang.Strings
import org.beangle.data.dao.{Operation, OqlBuilder}
import org.beangle.data.excel.schema.ExcelSchema
import org.beangle.data.transfer.importer.ImportSetting
import org.beangle.data.transfer.importer.listener.ForeignerListener
import org.beangle.web.action.annotation.response
import org.beangle.web.action.context.ActionContext
import org.beangle.web.action.view.{Stream, View}
import org.beangle.webmvc.execution.MappingHandler
import org.beangle.webmvc.support.action.{ImportSupport, RestfulAction}
import org.beangle.webmvc.support.helper.QueryHelper

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import java.time.Instant

class StaffAction extends RestfulAction[Staff], ImportSupport[Staff] {

  var codeService: CodeService = _
  //  var urpUserHelper: UrpUserHelper = _

  override def getQueryBuilder: OqlBuilder[Staff] = {
    ActiveSearchHelper.addTemporalOn(super.getQueryBuilder, getBoolean("active"))
  }

  override def editSetting(staff: Staff) = {
    put("departments", entityDao.getAll(classOf[Department]))
    put("genders", codeService.get(classOf[Gender]))
    put("staffTypes", codeService.get(classOf[StaffType]))
    super.editSetting(staff)
  }

  override protected def indexSetting(): Unit = {
    put("departments", entityDao.getAll(classOf[Department]))
    put("staffTypes", entityDao.getAll(classOf[StaffType]))
  }

  override protected def saveAndRedirect(staff: Staff): View = {
    staff.updatedAt = Instant.now
    try {
      entityDao.saveOrUpdate(staff)
      //      //sychronize name to teacher/mentor/tutor
      //      val teachers = entityDao.findBy(classOf[Teacher], "staff", staff)
      //      teachers foreach (t => t.name = staff.name)
      //      entityDao.saveOrUpdate(teachers)
      //
      //      val mentors = entityDao.findBy(classOf[Mentor], "staff", staff)
      //      mentors foreach (t => t.name = staff.name)
      //      entityDao.saveOrUpdate(mentors)
      //      urpUserHelper.createStaffUser(staff)
      redirect("search", "info.save.success")
    } catch {
      case e: Exception => {
        val mapping = ActionContext.current.handler.asInstanceOf[MappingHandler].mapping
        val redirectTo = mapping.method.getName match {
          case "save" => "editNew"
          case "update" => "edit"
        }
        logger.info("save forwad failure", e)
        redirect(redirectTo, "info.save.failure")
      }
    }
  }

  @response
  def downloadTemplate(): Any = {
    val genders = codeService.get(classOf[Gender]).map(x => x.code + " " + x.name)
    val departs = entityDao.search(OqlBuilder.from(classOf[Department], "bt").orderBy("bt.code")).map(x => x.code + " " + x.name)
    val staffTypes = codeService.get(classOf[StaffType]).map(x => x.code + " " + x.name)

    val schema = new ExcelSchema()
    val sheet = schema.createScheet("数据模板")
    sheet.title("职工信息模板")
    sheet.remark("特别说明：\n1、不可改变本表格的行列结构以及批注，否则将会导入失败！\n2、必须按照规格说明的格式填写。\n3、可以多次导入，重复的信息会被新数据更新覆盖。\n4、保存的excel文件名称可以自定。")
    sheet.add("工号", "staff.code").length(10).required().remark("≤10位")
    sheet.add("姓名", "staff.name").length(100).required()
    sheet.add("性别", "staff.gender.code").ref(genders).required()
    sheet.add("教职工类别", "staff.staffType.code").ref(staffTypes).required()
    sheet.add("所在部门", "staff.department.code").ref(departs).required()
    sheet.add("移动电话", "staff.mobile").length(100)
    val os = new ByteArrayOutputStream()
    schema.generate(os)
    Stream(new ByteArrayInputStream(os.toByteArray), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "职工信息.xlsx")
  }

  protected override def configImport(setting: ImportSetting): Unit = {
    val fl = new ForeignerListener(entityDao)
    fl.addForeigerKey("name")
    setting.listeners = List(fl, new StaffImportListener(entityDao))
  }
}
