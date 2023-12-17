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

import net.yushanginfo.hams.base.model.{Inpatient, Yuan}
import net.yushanginfo.hams.base.service.InpatientService
import net.yushanginfo.hams.ebuy.model.{EbuyOrder, OrderLine}
import net.yushanginfo.hams.ebuy.service.OrderLineService
import org.beangle.commons.collection.Collections
import org.beangle.commons.lang.Strings
import org.beangle.data.dao.EntityDao
import org.beangle.data.transfer.importer.{ImportListener, ImportResult}

class OrderLineImportListener(val order: EbuyOrder, entityDao: EntityDao, orderLineService: OrderLineService, inpatientService: InpatientService) extends ImportListener {
  var lastInpatient: Option[Inpatient] = None
  var payments = Collections.newMap[Inpatient, Yuan]

  override def onItemFinish(tr: ImportResult): Unit = {
    val data = tr.transfer.curData
    val l = tr.transfer.current.asInstanceOf[OrderLine]
    val inpatientName = data.getOrElse("orderLine.inpatient.name", "").toString

    var inpatient: Inpatient = null
    if (Strings.isEmpty(inpatientName)) {
      inpatient = lastInpatient.orNull
    } else {
      inpatient = inpatientService.getInpatientByName(inpatientName, order.ward).orNull
      lastInpatient = Option(inpatient)
    }

    if (null == inpatient) {
      val tmp = inpatientService.getInpatientByName(inpatientName).orNull
      if (null != tmp && tmp.ward != order.ward) {
        tr.addFailure(s"病人${tmp.name}在${tmp.ward.name},不在${order.ward.name}", inpatientName)
        inpatient = tmp
        //        return
      } else {
        tr.addFailure("错误的病人姓名", inpatientName)
        return
      }
    }

    val commodityName = data.getOrElse("orderLine.commodity.name", "").toString
    val brandName = data.getOrElse("orderLine.brand.name", "").toString
    val unitName = data.getOrElse("orderLine.unit.name", "").toString

    val totalPayment = data.getOrElse("totalPayment", "").toString
    if (Strings.isNotBlank(totalPayment)) {
      payments.get(inpatient) match
        case None => payments.put(inpatient, Yuan(totalPayment))
        case Some(y) => payments.put(inpatient, Yuan(totalPayment) + y)
    }
    val line = orderLineService.createLine(order, inpatient, commodityName, brandName, unitName, l.amount, l.price, l.payable, l.payment)
    if (!line.persisted) tr.addFailure("缺少物品单位信息", inpatientName)
  }

  override def onFinish(tr: ImportResult): Unit = {
    entityDao.refresh(order)
    payments foreach { case (i, p) =>
      val lines = order.lines.filter(l => l.inpatient == i)
      val payment = new Yuan(lines.map(_.payment.map(_.value).getOrElse(0L)).sum)
      val delta = p - payment
      if (delta.value != 0) {
        val head = lines.head
        val headPayment = head.payment.getOrElse(Yuan.Zero)
        // p = payment + delta(p - payment)
        head.payment = Some(headPayment + delta)
      }
    }
    order.calcPayment()
    order.convertLineToPrice()
    entityDao.saveOrUpdate(order)

  }
}
