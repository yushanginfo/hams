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

import net.yushanginfo.hams.base.model.{Inpatient, Ward}
import net.yushanginfo.hams.base.service.InpatientService
import net.yushanginfo.hams.wallet.helper.BillImportListener
import net.yushanginfo.hams.wallet.model.{Bill, WalletType}
import net.yushanginfo.hams.wallet.service.WalletService
import org.beangle.commons.activation.MediaTypes
import org.beangle.commons.lang.Strings
import org.beangle.data.dao.OqlBuilder
import org.beangle.data.excel.schema.ExcelSchema
import org.beangle.data.transfer.importer.ImportSetting
import org.beangle.web.action.view.{Stream, View}
import org.beangle.webmvc.support.action.{ExportSupport, ImportSupport, RestfulAction}
import org.beangle.webmvc.support.helper.QueryHelper

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import java.time.YearMonth

/**
 * 伙食费支出
 */
class MealBillAction extends RestfulAction[Bill], ImportSupport[Bill], ExportSupport[Bill] {
  var walletService: WalletService = _
  var inpatientService: InpatientService = _

  override protected def indexSetting(): Unit = {
    put("wards", entityDao.getAll(classOf[Ward]))
    super.indexSetting()
  }

  override protected def saveAndRedirect(bill: Bill): View = {
    val payAt = getInstant("payAt").get
    val minPayAt = bill.updatePayAt(payAt)

    if (!bill.persisted && !Strings.isEmpty(bill.wallet.inpatient.code)) {
      inpatientService.getInpatient(bill.wallet.inpatient.code).headOption match {
        case None => redirect("index", "不正确的住院号")
        case Some(inpatient) =>
          val wallet = walletService.getOrCreateWallet(inpatient, WalletType.Meal, payAt)
          val nbill = wallet.newBill(bill.amount, bill.payAt, bill.goods)
          entityDao.saveOrUpdate(wallet, nbill)
          walletService.adjustBalance(wallet, minPayAt)
          super.saveAndRedirect(nbill)
      }
    } else {
      entityDao.saveOrUpdate(bill)
      walletService.adjustBalance(bill.wallet, minPayAt)
      super.saveAndRedirect(bill)
    }
  }

  override protected def getQueryBuilder: OqlBuilder[Bill] = {
    val query = super.getQueryBuilder
    QueryHelper.dateBetween(query, null, "payAt", "beginAt", "endAt")
    query.where("bill.wallet.walletType=:walletType", WalletType.Meal)
    query
  }

  def calcBill(): View = {
    val ym = YearMonth.parse(get("yearMonth").get)
    walletService.generateMealBills(ym)
    redirect("search", "支出流水产生完成")
  }

  def downloadTemplate(): View = {
    val schema = new ExcelSchema()
    val sheet = schema.createScheet("数据模板")
    sheet.title("伙食费支出模板")
    sheet.remark("特别说明：\n1、不可改变本表格的行列结构以及批注，否则将会导入失败！\n2、必须按照规格说明的格式填写。\n3、可以多次导入，重复的信息会被新数据更新覆盖。\n4、保存的excel文件名称可以自定。")
    sheet.add("姓名", "bill.wallet.inpatient.name").length(10).required().remark("≤10位")
    sheet.add("数额", "bill.amount").remark("支出请填写负值")
    sheet.add("支出时间", "bill.payAt")
    sheet.add("资金去向", "bill.goods")

    val os = new ByteArrayOutputStream()
    schema.generate(os)
    Stream(new ByteArrayInputStream(os.toByteArray), MediaTypes.ApplicationXlsx.toString, "伙食费支出.xlsx")
  }

  protected override def configImport(setting: ImportSetting): Unit = {
    setting.listeners = List(new BillImportListener(WalletType.Meal, inpatientService, walletService, entityDao))
  }
}
