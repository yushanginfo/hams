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
import org.beangle.commons.activation.MediaTypes
import org.beangle.commons.lang.{Strings, SystemInfo}
import org.beangle.data.dao.OqlBuilder
import org.beangle.data.excel.schema.ExcelSchema
import org.beangle.data.transfer.importer.listener.ForeignerListener
import org.beangle.data.transfer.importer.{ImportSetting, MultiEntityImporter}
import org.beangle.web.action.view.{Stream, View}
import org.beangle.webmvc.support.action.{ExportSupport, ImportSupport, RestfulAction}
import org.beangle.webmvc.support.helper.PopulateHelper

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

class InpatientAction extends RestfulAction[Inpatient], ExportSupport[Inpatient], ImportSupport[Inpatient] {

  var codeService: CodeService = _

  override protected def indexSetting(): Unit = {
    put("wards", entityDao.getAll(classOf[Ward]))
    put("statuses", codeService.get(classOf[InpatientStatus]))
    put("feeOrigins", codeService.get(classOf[FeeOrigin]))
  }

  override protected def editSetting(inpatient: Inpatient): Unit = {
    put("wards", entityDao.getAll(classOf[Ward]))
    put("genders", codeService.get(classOf[Gender]))
    put("statuses", codeService.get(classOf[InpatientStatus]))
    put("relationships", codeService.get(classOf[Relationship]))

    val relation1 = inpatient.relations.find(_.idx == 1)
    val relation2 = inpatient.relations.find(_.idx == 2)
    put("relation1", relation1)
    put("relation2", relation2)

    super.editSetting(inpatient)
  }

  def downloadTemplate(): View = {
    val cardTypes = codeService.get(classOf[CardType]).map(x => x.code + " " + x.name)
    val genders = codeService.get(classOf[Gender]).map(x => x.code + " " + x.name)
    val maritalStatuses = codeService.get(classOf[MaritalStatus]).map(x => x.code + " " + x.name)
    val idTypes = codeService.get(classOf[IdType]).map(x => x.code + " " + x.name)
    val nations = codeService.get(classOf[Nation]).map(x => x.code + " " + x.name)
    val countries = codeService.get(classOf[Country]).map(x => x.code + " " + x.name)
    val feeOrigins = codeService.get(classOf[FeeOrigin]).map(x => x.code + " " + x.name)
    val relationships = codeService.get(classOf[Relationship]).map(x => x.code + " " + x.name)
    val criticalLevels = codeService.get(classOf[CriticalLevel]).map(x => x.code + " " + x.name)
    val diseaseTypes = codeService.get(classOf[DiseaseType]).map(x => x.code + " " + x.name)
    val wards = entityDao.search(OqlBuilder.from(classOf[Ward], "bt").orderBy("bt.name").orderBy("bt.name"))
      .map(x => x.code + " " + x.name)

    val schema = new ExcelSchema()
    val sheet = schema.createScheet("数据模板")
    sheet.title("住院病人信息模板")
    sheet.remark("特别说明：\n1、不可改变本表格的行列结构以及批注，否则将会导入失败！\n2、必须按照规格说明的格式填写。\n3、可以多次导入，重复的信息会被新数据更新覆盖。\n4、保存的excel文件名称可以自定。")
    sheet.add("住院号", "inpatient.code").length(10).required().remark("≤10位")
    sheet.add("卡号", "inpatient.card").length(50).required()
    sheet.add("卡类型", "inpatient.cardType.code").ref(cardTypes)
    sheet.add("患者姓名", "inpatient.name").length(100).required()
    sheet.add("费用类别", "inpatient.feeOrigin.code").ref(feeOrigins).required()
    sheet.add("危重级别", "inpatient.criticalLevel.code").ref(criticalLevels)
    sheet.add("入院日期", "inpatient.beginAt").date("yyyyMMdd!THH:mm:ss").required()
    sheet.add("出院日期", "inpatient.endAt").date("yyyyMMdd!THH:mm:ss")
    sheet.add("门诊诊断", "inpatient.diseaseType.code").ref(diseaseTypes).required()
    sheet.add("病区", "inpatient.ward.code").ref(wards).required()
    sheet.add("床位", "inpatient.bedNo")
    sheet.add("床位医生", "inpatient.bedDoctor")

    sheet.add("姓名拼音", "person.phoneticName").length(30)
    sheet.add("姓名五笔", "person.wubiName").length(30)
    sheet.add("性别", "person.gender.code").ref(genders)
    sheet.add("出生年月", "person.birthday").date("yyyyMMdd").required()
    sheet.add("婚姻状况", "person.maritalStatus.code").ref(maritalStatuses)
    sheet.add("证件类型", "person.idType.code").ref(idTypes)
    sheet.add("证件号码", "person.idcard").length(18)
    sheet.add("职业", "person.profession").length(30)
    sheet.add("民族", "person.nation.code").ref(nations)
    sheet.add("国家", "person.country.code").ref(countries)
    sheet.add("出生地(省)", "person.birthProvince").length(20)
    sheet.add("出生地(区、县)", "person.birthDistrict").length(50)
    sheet.add("籍贯", "person.homeTown").length(100)

    sheet.add("户籍地址", "contact.residenceAddress").length(100)
    sheet.add("现住址", "contact.address").length(100)
    sheet.add("联系电话", "contact.phone").length(20)
    sheet.add("邮编", "contact.postcode").length(10)
    sheet.add("单位名称", "contact.orgName").length(100)
    sheet.add("单位电话", "contact.orgPhone").length(20)
    sheet.add("单位邮编", "contact.orgPostcode").length(10)

    sheet.add("联系人姓名", "relation.name").length(50)
    sheet.add("联系人关系", "relation.relationship.code").ref(relationships)
    sheet.add("联系人地址", "relation.address").length(100)
    sheet.add("联系人电话", "relation.phone").length(20)

    sheet.add("登记人", "inpatient.createdBy").length(50)
    sheet.add("病人ID", "inpatient.patientId").required()

    val os = new ByteArrayOutputStream()
    schema.generate(os)
    Stream(new ByteArrayInputStream(os.toByteArray), MediaTypes.ApplicationXlsx.toString, "住院信息模板.xlsx")
  }

  private def processRelation(inpatient: Inpatient, idx: Int): Unit = {
    val r1 = inpatient.relations.find(_.idx == idx).getOrElse(new Relation)
    populate(r1, s"relation$idx")
    r1.idx = idx
    if (r1.relationship != null && Strings.isNotBlank(r1.name) && (r1.phone.nonEmpty || r1.address.nonEmpty)) {
      if (!r1.persisted) {
        r1.inpatient = inpatient
        inpatient.relations.addOne(r1)
      }
    } else {
      if (r1.persisted) inpatient.relations.subtractOne(r1)
    }
  }

  override protected def saveAndRedirect(inpatient: Inpatient): View = {
    processRelation(inpatient, 1)
    processRelation(inpatient, 2)
    processRelation(inpatient, 3)
    super.saveAndRedirect(inpatient)
  }

  protected override def configImport(setting: ImportSetting): Unit = {
    val fl = new ForeignerListener(entityDao)
    fl.addForeigerKey("name")
    val importer = new MultiEntityImporter
    importer.domain = this.entityDao.domain
    importer.populator = PopulateHelper.populator
    importer.addEntity("inpatient", classOf[Inpatient])
    importer.addEntity("person", classOf[Person])
    importer.addEntity("contact", classOf[Contact])
    importer.addEntity("relation", classOf[Relation])
    setting.importer = importer
    setting.listeners = List(fl, new InpatientImporterListener(entityDao))
    setting.listeners foreach { l =>
      importer.addListener(l)
    }
  }

  def manualSync(): View = {
    val pb = new ProcessBuilder(SystemInfo.user.home + "/transport/sync.sh")
    pb.inheritIO()
    val pro = pb.start()
    pro.waitFor()
    redirect("search", "同步成功")
  }
}
