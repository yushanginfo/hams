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

import net.yushanginfo.hams.account.model.{Subsidy, SubsidyBill}
import net.yushanginfo.hams.account.service.SubsidyService
import net.yushanginfo.hams.account.web.helper.SubsidyBillImportListener
import net.yushanginfo.hams.base.model.{Inpatient, Ward, Yuan}
import net.yushanginfo.hams.base.service.InpatientService
import net.yushanginfo.hams.code.model.IncomeChannel
import net.yushanginfo.hams.wallet.model.{Income, Wallet, WalletType}
import org.beangle.commons.activation.MediaTypes
import org.beangle.commons.lang.Strings
import org.beangle.data.dao.OqlBuilder
import org.beangle.data.excel.schema.ExcelSchema
import org.beangle.data.transfer.importer.ImportSetting
import org.beangle.web.action.view.{Stream, View}
import org.beangle.webmvc.support.action.{ExportSupport, ImportSupport, RestfulAction}
import org.beangle.webmvc.support.helper.QueryHelper

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

/**
 * 伙食费支出
 */
class SubsidyBillAction extends RestfulAction[SubsidyBill], ImportSupport[SubsidyBill], ExportSupport[SubsidyBill] {
  var inpatientService: InpatientService = _
  var subsidyService: SubsidyService = _

  override protected def simpleEntityName: String = "bill"

  override protected def indexSetting(): Unit = {
    put("wards", entityDao.getAll(classOf[Ward]))
    super.indexSetting()
  }

  override protected def getQueryBuilder: OqlBuilder[SubsidyBill] = {
    val query = super.getQueryBuilder
    QueryHelper.dateBetween(query, null, "payAt", "beginAt", "endAt")
    query
  }

  override protected def saveAndRedirect(bill: SubsidyBill): View = {
    val payAt = getInstant("payAt").get
    val minPayAt = bill.updatePayAt(payAt)

    if (!bill.persisted && !Strings.isEmpty(bill.account.inpatient.code)) {
      entityDao.findBy(classOf[Inpatient], "code", bill.account.inpatient.code).headOption match {
        case None => return redirect("index", "不正确的住院号")
        case Some(i) =>
          val s = subsidyService.getOrCreate(i)
          bill.account = s
      }
    }
    if (bill.amount.value > 0) {
      bill.amount = Yuan.Zero - bill.amount
    }
    val subsidy = entityDao.get(classOf[Subsidy], bill.account.id)
    if (null == bill.balance) bill.balance = subsidy.balance + bill.amount
    entityDao.saveOrUpdate(subsidy, bill)
    subsidyService.adjustBalance(bill.account, minPayAt)

    bill.expenses.trim match
      case "转零用金" =>
        val wallet = entityDao.findBy(classOf[Wallet], "inpatient" -> subsidy.inpatient, "walletType" -> WalletType.Change).headOption
        wallet foreach { w =>
          val income = w.newIncome(bill.amount, bill.payAt, new IncomeChannel(IncomeChannel.FromSubsidy))
          entityDao.saveOrUpdate(income, wallet)
        }
        bill.toWallet = Some(WalletType.Change)
      case "转伙食费" =>
        val wallet = entityDao.findBy(classOf[Wallet], "inpatient" -> subsidy.inpatient, "walletType" -> WalletType.Meal).headOption
        wallet foreach { w =>
          val income = w.newIncome(bill.amount, bill.payAt, new IncomeChannel(IncomeChannel.FromSubsidy))
          entityDao.saveOrUpdate(income, wallet)
        }
        bill.toWallet = Some(WalletType.Meal)
      case _ =>
    entityDao.saveOrUpdate(bill)

    super.saveAndRedirect(bill)
  }

  def downloadTemplate(): View = {
    val schema = new ExcelSchema()
    val sheet = schema.createScheet("数据模板")
    sheet.title("养护补贴支出模板")
    sheet.remark("特别说明：\n1、不可改变本表格的行列结构以及批注，否则将会导入失败！\n2、必须按照规格说明的格式填写。\n3、可以多次导入，重复的信息会被新数据更新覆盖。\n4、保存的excel文件名称可以自定。")
    sheet.add("姓名", "subsidyBill.account.inpatient.name").length(10).required().remark("≤10位")
    sheet.add("数额", "subsidyBill.amount").remark("支出请填写负值")
    sheet.add("支出时间", "subsidyBill.payAt")
    sheet.add("资金去向", "subsidyBill.expenses")
    sheet.add("备注", "subsidyBill.remark")

    val os = new ByteArrayOutputStream()
    schema.generate(os)
    Stream(new ByteArrayInputStream(os.toByteArray), MediaTypes.ApplicationXlsx.toString, "养护补贴支出.xlsx")
  }

  protected override def configImport(setting: ImportSetting): Unit = {
    setting.listeners = List(new SubsidyBillImportListener(inpatientService, subsidyService, entityDao))
  }
}
