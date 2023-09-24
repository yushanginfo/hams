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

package net.yushanginfo.hams.ebuy.service.impl

import net.yushanginfo.hams.base.model.{Inpatient, Yuan}
import net.yushanginfo.hams.ebuy.model.{EbuyOrder, OrderLine}
import net.yushanginfo.hams.ebuy.service.{CommodityService, OrderLineService}
import org.beangle.data.dao.EntityDao

class OrderLineServiceImpl extends OrderLineService {

  var commodityService: CommodityService = _

  var entityDao: EntityDao = _

  override def createLine(order: EbuyOrder, inpatient: Inpatient, commodityName: String,
                          brandName: String, unitName: String, amount: Int, price: Option[Yuan], payable: Option[Yuan], payment: Option[Yuan]): OrderLine = {

    require(null != inpatient && null != order)
    val commodity = commodityService.getOrCreateCommodity(commodityName)
    val line = order.lines.find(l => l.inpatient == inpatient && l.commodity == commodity).getOrElse(new OrderLine)
    line.order = order
    price foreach { p =>
      line.price = Some(p)
      payable match
        case None => line.payable = Some(new Yuan(p.value * line.amount))
        case Some(p) => line.payable = payable
    }
    payment foreach { p =>
      line.payment = Some(p)
    }
    line.inpatient = inpatient
    line.commodity = commodity
    line.amount = amount
    line.unit = commodityService.getOrCreateUnit(unitName)
    line.brand = Option(commodityService.getOrCreateBrand(brandName))
    if (!line.persisted && line.unit != null) {
      order.lines.addOne(line)
      entityDao.saveOrUpdate(order, line)
    }
    line
  }

}
