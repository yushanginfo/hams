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
  var amount: Float = _
  /** 单价 */
  var price: Yuan = _
  /** 折扣 <=10 */
  var discount: Float = _
  /** 折后价格 */
  var discountPrice: Yuan = _
  /** 实收 */
  var payment: Yuan = Yuan.Zero
  /** 应收 */
  var payable: Yuan = Yuan.Zero

  def update(amount: Float, payable: Yuan, payment: Yuan): Unit = {
    this.amount = amount
    this.payable = payable
    this.payment = payment
    if (amount == 0) {
      this.price = payable
      this.discountPrice = payable
    } else {
      this.price = payable / amount
      this.discountPrice = payment / amount
    }
    if (payable.value != 0) {
      this.discount = payment.value * 10.0f / payable.value
    }
  }

  def commodityDescription: String = {
    brand.map(_.name).getOrElse("") + commodity.name + "(" + unit.name + ")"
  }
}
