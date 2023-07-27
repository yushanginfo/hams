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

package net.yushanginfo.hams.ebuy.model

import net.yushanginfo.hams.base.model.Yuan
import org.beangle.data.model.LongId

class OrderCommodityPrice extends LongId {
  /** 采购批次 */
  var order: EbuyOrder = _

  var commodity: Commodity = _

  var brand: Option[CommodityBrand] = None

  var unit: CommodityUnit = _

  var amount: Int = _

  var price: Yuan = _

  var discount: Float = _

  var actual: Yuan = _

  var cost: Yuan = _

  def commodityDescription: String = {
    brand.map(_.name).getOrElse("") + commodity.name + "(" + unit.name + ")"
  }
}
