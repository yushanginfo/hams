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

import net.yushanginfo.hams.base.model.{Ward, Yuan}
import org.beangle.commons.collection.Collections
import org.beangle.data.model.LongId
import org.beangle.data.model.pojo.{DateRange, Named}

import java.time.LocalDate
import scala.collection.mutable

/**
 * 采购批次
 */
class EbuyOrder extends LongId, Named, DateRange {

  var ward: Ward = _

  /** 购买时间 */
  var orderOn: Option[LocalDate] = None

  /** 总花销 */
  var payable: Option[Yuan] = None

  /** 总花销 */
  var payment: Option[Yuan] = None

  /** 订单明细 */
  var lines: mutable.Buffer[OrderLine] = Collections.newBuffer[OrderLine]

  /** 物品价格 */
  var prices: mutable.Buffer[OrderCommodityPrice] = Collections.newBuffer[OrderCommodityPrice]

  /** 是否生成订单 */
  var billGenerated: Boolean = _

  /** 入账时间 */
  var billOn: Option[LocalDate] = None

  /** 如果先有了订单，可以生成没有的价格
   */
  def convertLineToPrice(): Unit = {
    val products = this.lines.map(x => (x.commodity, x.brand, x.unit)).distinct
    val newer = products.filter { x => !this.prices.exists(y => y.commodity == x._1 && y.unit == x._3 && y.brand == x._2) }
    newer foreach { x =>
      val p = new OrderCommodityPrice()
      p.order = this
      p.commodity = x._1
      p.brand = x._2
      p.unit = x._3
      this.lines.find(y => y.commodity == x._1 && y.unit == x._3 && y.brand == x._2 && y.price.nonEmpty) foreach { line =>
        p.price = line.price.get
      }
      this.prices.addOne(p)
    }
    //删除商品中没有的价格
    this.prices.dropWhileInPlace(x => !this.lines.exists(y => y.commodity == x.commodity && y.unit == x.unit && y.brand == x.brand))

    //统计每个商品的数量
    val groupLines = this.lines groupBy (x => (x.commodity, x.brand, x.unit))
    groupLines.foreach { case (g, lines) =>
      this.prices.find(p => p.commodity == g._1 && p.brand == g._2 && p.unit == g._3) foreach { p =>
        p.amount = lines.map(_.amount).sum
      }
    }
  }

  def calcPayable(): Unit = {
    val groupLines = this.lines groupBy (x => (x.commodity, x.brand, x.unit))
    var totalPayable = Yuan(0)
    var totalPayment = Yuan(0)
    groupLines.foreach { case (g, lines) =>
      this.prices.find(p => p.commodity == g._1 && p.brand == g._2 && p.unit == g._3) foreach { p =>
        p.amount = lines.map(_.amount).sum
        p.discountPrice = new Yuan((p.price.value * p.discount / 10).toLong)
        lines foreach { line =>
          line.price = Some(p.price)
          line.payable = Some(new Yuan(p.price.value * line.amount))
          line.payment = Some(new Yuan(p.discountPrice.value * line.amount))
          totalPayment += line.payment.get
          totalPayable += line.payable.get
        }
      }
    }
    this.payable = Some(totalPayable)
    this.payment = Some(totalPayment)
  }

  def calcPayment(): Unit = {
    var totalPayment = Yuan(0)
    lines foreach { l =>
      totalPayment += l.payment.getOrElse(Yuan.Zero)
    }
    this.payment = Some(totalPayment)
  }

  def inputable(): Boolean = {
    this.within(LocalDate.now)
  }
}
