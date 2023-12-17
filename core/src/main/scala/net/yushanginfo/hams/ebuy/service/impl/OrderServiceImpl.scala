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

import net.yushanginfo.hams.base.model.Yuan
import net.yushanginfo.hams.ebuy.model.EbuyOrder
import net.yushanginfo.hams.ebuy.service.OrderService
import net.yushanginfo.hams.wallet.model.WalletType
import net.yushanginfo.hams.wallet.service.{BillService, WalletService}
import org.beangle.commons.collection.Collections
import org.beangle.data.dao.{EntityDao, OqlBuilder}

import java.time.format.DateTimeFormatter
import java.time.{YearMonth, ZoneId}

class OrderServiceImpl extends OrderService {

  var entityDao: EntityDao = _
  var walletService: WalletService = _
  var billService: BillService = _

  def generateBills(orders: Iterable[EbuyOrder]): Unit = {
    orders foreach { order => generateBills(order) }
  }

  override def generateBills(order: EbuyOrder): Unit = {
    if (order.billOn.isEmpty) return;

    //we need update billGenerated first,for sumup all generated orderLine
    order.billGenerated = true
    entityDao.saveOrUpdate(order)

    val yearMonth = YearMonth.from(order.billOn.get)
    val query = OqlBuilder.from(classOf[EbuyOrder], "o")
    query.where("o.billOn is not null and o.ward=:ward", order.ward)
    query.where("o.billGenerated=true and to_char(o.billOn,'yyyyMM')=:billOn", DateTimeFormatter.ofPattern("yyyyMM").format(yearMonth))
    val sameMonthOrders = entityDao.search(query)

    val billOn = order.billOn.get
    val orderAt = billOn.atTime(0, 0).atZone(ZoneId.systemDefault).toInstant
    val inpatientCost = order.lines.groupBy(_.inpatient).map(x => (x._1, new Yuan(x._2.map(_.payment.getOrElse(Yuan.Zero).value).sum)))
    val fails = Collections.newBuffer[String]
    inpatientCost foreach { case (i, c) =>
      val wallet = walletService.getOrCreateWallet(i, WalletType.Change, i.beginAt)
      val bills = billService.getBills(wallet, yearMonth, "随心E购")
      if (bills.isEmpty) {
        val bill = wallet.newBill(c, orderAt, "随心E购")
        entityDao.saveOrUpdate(bill)
        walletService.adjustBalance(wallet, orderAt)
      } else {
        var cost = Yuan.Zero
        var minPayon = billOn
        sameMonthOrders foreach { o =>
          cost += new Yuan(o.lines.filter(_.inpatient == i).map(_.payment.getOrElse(Yuan.Zero).value).sum)
          if (o.billOn.get.isBefore(minPayon)) minPayon = o.billOn.get
        }
        val updatedAt = minPayon.atTime(0, 0).atZone(ZoneId.systemDefault).toInstant
        bills.head.update(Yuan.Zero - cost, updatedAt)
        entityDao.saveOrUpdate(bills)
        walletService.adjustBalance(wallet, updatedAt)
      }

    }

  }

}
