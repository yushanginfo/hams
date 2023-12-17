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

import net.yushanginfo.hams.base.model.{Inpatient, Yuan}
import org.beangle.data.model.LongId

/**
 * 每个病人的采购订单项
 */
class OrderLine extends LongId {
  /** 采购批次 */
  var order: EbuyOrder = _
  /** 采购人 */
  var inpatient: Inpatient = _
  /** 商品名 */
  var commodity: Commodity = _
  /** 品牌 */
  var brand: Option[CommodityBrand] = None
  /** 量词 */
  var unit: CommodityUnit = _
  /** 数量 */
  var amount: Float = _
  /** 单个价格 */
  var price: Option[Yuan] = None
  /** 应收 */
  var payable: Option[Yuan] = None
  /** 实付 */
  var payment: Option[Yuan] = None
}
