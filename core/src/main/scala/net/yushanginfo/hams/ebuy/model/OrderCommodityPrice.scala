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

/** 订购商品价格
 */
class OrderCommodityPrice extends LongId {
  /** 采购批次 */
  var order: EbuyOrder = _
  /** 物品 */
  var commodity: Commodity = _
  /** 品牌 */
  var brand: Option[CommodityBrand] = None
  /** 单位 */
  var unit: CommodityUnit = _
  /** 数量 */
  var amount: Int = _
  /** 单价 */
  var price: Yuan = _
  /** 折扣 <=10 */
  var discount: Float = _
  /** 折后价格 */
  var discountPrice: Yuan = _

  def payable: Yuan = {
    new Yuan(price.value * amount)
  }

  def commodityDescription: String = {
    brand.map(_.name).getOrElse("") + commodity.name + "(" + unit.name + ")"
  }
}
