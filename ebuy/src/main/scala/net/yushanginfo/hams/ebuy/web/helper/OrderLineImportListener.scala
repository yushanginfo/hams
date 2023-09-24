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

package net.yushanginfo.hams.ebuy.web.helper

import net.yushanginfo.hams.base.service.InpatientService
import net.yushanginfo.hams.ebuy.model.{EbuyOrder, OrderLine}
import net.yushanginfo.hams.ebuy.service.OrderLineService
import org.beangle.data.dao.EntityDao
import org.beangle.data.transfer.importer.{ImportListener, ImportResult}

class OrderLineImportListener(val order: EbuyOrder, entityDao: EntityDao, orderLineService: OrderLineService, inpatientService: InpatientService) extends ImportListener {
  override def onItemFinish(tr: ImportResult): Unit = {
    val data = tr.transfer.curData
    val l = tr.transfer.current.asInstanceOf[OrderLine]
    val inpatientName = data.getOrElse("orderLine.inpatient.name", "").toString

    val inpatient = inpatientService.getInpatientByName(inpatientName).orNull
    if (null == inpatient) {
      tr.addFailure("错误的病人姓名", inpatientName)
      return
    }
    val commodityName = data.getOrElse("orderLine.commodity.name", "").toString
    val brandName = data.getOrElse("orderLine.brand.name", "").toString
    val unitName = data.getOrElse("orderLine.unit.name", "").toString

    val line = orderLineService.createLine(order, inpatient, commodityName, brandName, unitName, l.amount, l.price, l.payable, l.payment)
    if (!line.persisted) tr.addFailure("缺少物品单位信息", inpatientName)
  }

  override def onFinish(tr: ImportResult): Unit = {
    entityDao.refresh(order)
    order.calcPayment()
    entityDao.saveOrUpdate(order)
  }
}
