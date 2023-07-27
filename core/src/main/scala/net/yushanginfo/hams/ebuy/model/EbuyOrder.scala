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

  var ward: Option[Ward] = None

  /** 购买时间 */
  var orderOn: Option[LocalDate] = None

  /** 总花销 */
  var cost: Option[Yuan] = None

  /** 订单明细 */
  var lines: mutable.Buffer[OrderLine] = Collections.newBuffer[OrderLine]

  /** 物品价格 */
  var prices: mutable.Buffer[OrderCommodityPrice] = Collections.newBuffer[OrderCommodityPrice]

  /** 是否生成订单 */
  var billGenerated: Boolean = _

  def convertLineToPrice(): Unit = {
    val products = this.lines.map(x => (x.commodity, x.brand, x.unit)).distinct
    val newer = products.filter { x => !this.prices.exists(y => y.commodity == x._1 && y.unit == x._3 && y.brand == x._2) }
    newer foreach { x =>
      val p = new OrderCommodityPrice()
      p.order = this
      p.commodity = x._1
      p.brand = x._2
      p.unit = x._3
      this.prices.addOne(p)
    }
  }

  def calcCost(): Unit = {
    val groupLines = this.lines groupBy (x => (x.commodity, x.brand, x.unit))
    var totalCost = Yuan(0)
    groupLines.foreach { case (g, lines) =>
      this.prices.find(p => p.commodity == g._1 && p.brand == g._2 && p.unit == g._3) foreach { p =>
        p.amount = lines.map(_.amount).sum
        p.actual = new Yuan((p.price.value * p.discount / 10).toLong)
        p.cost = new Yuan(p.actual.value * p.amount)
        lines foreach { line =>
          val lc = line.cost match {
            case None => new Yuan(p.actual.value * line.amount)
            case Some(c) => c
          }
          line.cost = Some(lc)
          totalCost += lc
        }
      }
    }
    this.cost = Some(totalCost)
  }

  def inputable(): Boolean = {
    this.within(LocalDate.now)
  }
}
