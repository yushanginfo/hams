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

package net.yushanginfo.hams.ebuy.web.action

import net.yushanginfo.hams.base.model.{Inpatient, Ward}
import net.yushanginfo.hams.base.service.InpatientService
import net.yushanginfo.hams.ebuy.model.*
import net.yushanginfo.hams.ebuy.service.{CommodityService, OrderLineService}
import net.yushanginfo.hams.ebuy.web.helper.OrderLineImportListener
import org.beangle.commons.activation.MediaTypes
import org.beangle.commons.collection.Properties
import org.beangle.commons.lang.Strings
import org.beangle.data.dao.OqlBuilder
import org.beangle.data.excel.schema.ExcelSchema
import org.beangle.data.transfer.importer.ImportSetting
import org.beangle.web.action.annotation.response
import org.beangle.web.action.view.{Stream, View}
import org.beangle.webmvc.support.action.{ExportSupport, ImportSupport, RestfulAction}

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

class OrderLineAction extends RestfulAction[OrderLine], ImportSupport[OrderLine], ExportSupport[OrderLine] {

  var commodityService: CommodityService = _
  var inpatientService: InpatientService = _
  var orderLineService: OrderLineService = _

  override def search(): View = {
    getLong("orderLine.order.id") foreach { orderId =>
      put("order", entityDao.get(classOf[EbuyOrder], orderId))
    }
    super.search()
  }

  override protected def indexSetting(): Unit = {
    super.indexSetting()
    val query = OqlBuilder.from(classOf[EbuyOrder], "o")
    query.orderBy("o.beginOn desc")
    val orders = entityDao.search(query)
    put("orders", orders)

    put("wards", entityDao.getAll(classOf[Ward]))
  }

  override protected def editSetting(line: OrderLine): Unit = {
    if (!line.persisted) {
      line.amount = 1
      getLong("orderLine.order.id") foreach { orderId =>
        line.order = entityDao.get(classOf[EbuyOrder], orderId)
      }
    }
    super.editSetting(line)
  }

  override protected def saveAndRedirect(line: OrderLine): View = {
    get("brand.id") foreach { i =>
      if (Strings.isBlank(i)) {
        line.brand = None
      } else {
        line.brand = Some(commodityService.getOrCreateBrand(i))
      }
    }
    get("commodity.id") foreach { i =>
      line.commodity = commodityService.getOrCreateCommodity(i)
    }
    get("unit.id") foreach { i =>
      line.unit = commodityService.getOrCreateUnit(i)
    }
    super.saveAndRedirect(line)
  }

  @response
  def inpatients(): Seq[Properties] = {
    val query = OqlBuilder.from(classOf[Inpatient], "b")
    query.limit(getPageLimit)
    query.where("b.endAt is null")
    get("q") foreach { q =>
      query.where("b.name like :name or b.bedNo like :name", "%" + q + "%")
    }
    entityDao.search(query).map { b =>
      new Properties(b, "id", "description")
    }
  }

  @response
  def commodities(): Seq[Properties] = {
    val query = OqlBuilder.from(classOf[Commodity], "b")
    query.limit(getPageLimit)
    get("q") foreach { q =>
      query.where("b.name like :name", "%" + q + "%")
    }
    entityDao.search(query).map { b =>
      new Properties(b, "id", "name")
    }
  }

  @response
  def brands(): Seq[Properties] = {
    val query = OqlBuilder.from(classOf[CommodityBrand], "b")
    query.limit(getPageLimit)
    get("q") foreach { q =>
      query.where("b.name like :name", "%" + q + "%")
    }
    entityDao.search(query).map { b =>
      new Properties(b, "id", "name")
    }
  }

  @response
  def units(): Seq[Properties] = {
    val query = OqlBuilder.from(classOf[CommodityUnit], "b")
    query.limit(getPageLimit)
    get("q") foreach { q =>
      query.where("b.name like :name", "%" + q + "%")
    }
    entityDao.search(query).map { b =>
      new Properties(b, "id", "name")
    }
  }

  @response
  def downloadTemplate(): Any = {
    val schema = new ExcelSchema()
    val sheet = schema.createScheet("数据模板")
    sheet.title("订购明细信息模板")
    sheet.remark("特别说明：\n1、不可改变本表格的行列结构以及批注，否则将会导入失败！\n2、必须按照规格说明的格式填写。\n3、可以多次导入，重复的信息会被新数据更新覆盖。\n4、保存的excel文件名称可以自定。")
    sheet.add("姓名", "orderLine.inpatient.name").length(10).required().remark("≤10位")
    sheet.add("物品", "orderLine.commodity.name").length(100).required()
    sheet.add("数量", "orderLine.amount").required().decimal()
    sheet.add("单位", "orderLine.unit.name").required()
    sheet.add("单价", "orderLine.price")
    sheet.add("实收金额", "orderLine.payment")

    val os = new ByteArrayOutputStream()
    schema.generate(os)
    Stream(new ByteArrayInputStream(os.toByteArray), MediaTypes.ApplicationXlsx.toString, "订购明细.xlsx")
  }

  protected override def configImport(setting: ImportSetting): Unit = {
    val order = entityDao.get(classOf[EbuyOrder], getLong("orderLine.order.id").get)
    setting.listeners = List(new OrderLineImportListener(order, orderLineService, inpatientService))
  }
}
