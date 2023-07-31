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

package net.yushanginfo.hams.ebuy.web.action

import net.yushanginfo.hams.base.model.{Inpatient, Ward, Yuan}
import net.yushanginfo.hams.ebuy.model.*
import net.yushanginfo.hams.wallet.model.{Bill, Wallet, WalletType}
import org.beangle.commons.collection.{Collections, Properties}
import org.beangle.data.dao.OqlBuilder
import org.beangle.web.action.annotation.response
import org.beangle.web.action.view.View
import org.beangle.webmvc.support.action.RestfulAction

import java.time.ZoneId

/** 随心E购订单
 */
class EbuyOrderAction extends RestfulAction[EbuyOrder] {

  override protected def getQueryBuilder: OqlBuilder[EbuyOrder] = {
    val query = super.getQueryBuilder
    getInt("ebuyOrerYear") foreach { y =>
      query.where("year(ebuyOrder.orderOn) = :year", y)
    }
    query
  }

  override protected def editSetting(entity: EbuyOrder): Unit = {
    put("wards", entityDao.getAll(classOf[Ward]))
    super.editSetting(entity)
  }

  def inputRequire(): View = {
    val order = entityDao.get(classOf[EbuyOrder], getLongId("ebuyOrder"))

    val query = OqlBuilder.from(classOf[Inpatient], "inpatient")
    query.where("inpatient.endAt is null")
    populateConditions(query)
    query.orderBy("inpatient.ward.id,inpatient.bedNo")
    val inpatients = entityDao.search(query)

    put("orderLines", order.lines.groupBy(_.inpatient))
    put("wards", entityDao.getAll(classOf[Ward]))
    put("inpatients", inpatients)
    put("ebuyOrder", order)
    forward()
  }

  def saveRequire(): View = {
    forward()
  }

  def inputPrice(): View = {
    val order = entityDao.get(classOf[EbuyOrder], getLongId("ebuyOrder"))
    order.convertLineToPrice()
    put("ebuyOrder", order)
    forward()
  }

  def savePrice(): View = {
    val order = entityDao.get(classOf[EbuyOrder], getLongId("ebuyOrder"))
    order.convertLineToPrice()
    order.prices foreach { p =>
      val key = s"commodity_${p.commodity.id}_brand_${p.brand.map(_.id.toString).getOrElse("")}_unit_${p.unit.id}"
      p.price = Yuan(get(key + "_price", ""))
      p.discount = getFloat(key + "_discount").getOrElse(1f)
      p.discountPrice = new Yuan((p.price.value * p.discount).toLong)
    }
    order.calcPayable()
    order.calcPayment()
    entityDao.saveOrUpdate(order)
    redirect("priceReport", s"ebuyOrder.id=${order.id}", "info.save.success")
  }

  def requireReport(): View = {
    val order = entityDao.get(classOf[EbuyOrder], getLongId("ebuyOrder"))
    val wardOrderLines = order.lines.groupBy(_.inpatient.ward)
    val wardInpatientOrderLines = wardOrderLines.map(x => (x._1, x._2.groupBy(_.inpatient)))
    put("ebuyOrder", order)
    put("wardInpatientOrderLines", wardInpatientOrderLines)
    put("wards", wardOrderLines.keys.toBuffer.sortBy(_.code))
    forward()
  }

  def priceReport(): View = {
    val order = entityDao.get(classOf[EbuyOrder], getLongId("ebuyOrder"))
    put("ebuyOrder", order)
    forward()
  }

  /** 生成零用金流水
   *
   * @return
   */
  def generateBill(): View = {
    val order = entityDao.get(classOf[EbuyOrder], getLongId("ebuyOrder"))
    if (order.orderOn.isEmpty) return redirect("search", "缺少订购日期")
    val orderAt = order.orderOn.get.atTime(0, 0).atZone(ZoneId.systemDefault).toInstant
    val inpatientCost = order.lines.groupBy(_.inpatient).map(x => (x._1, new Yuan(x._2.map(_.payment.getOrElse(Yuan.Zero).value).sum)))
    val fails = Collections.newBuffer[String]
    inpatientCost foreach { case (i, c) =>
      val walletQ = OqlBuilder.from(classOf[Wallet], "w")
      walletQ.where("w.inpatient=:inpatient", i)
      walletQ.where("w.walletType=:walletType", WalletType.Change)
      entityDao.search(walletQ).headOption match {
        case Some(w) =>
          val query = OqlBuilder.from(classOf[Bill], "bill")
          query.where("bill.inpatient=:inpatient", i)
          query.where("bill.wallet=:w", w)
          query.where("to_char(bill.payAt,'yyyy-MM-dd')=:payAt", order.orderOn.toString)
          val bills = entityDao.search(query)
          if (bills.isEmpty) {
            val bill = w.newBill(c, orderAt, "随心E购")
            entityDao.saveOrUpdate(bill)
          }
        case None => fails.addOne(s"${i.bedNo} ${i.name}")
      }
    }
    if (fails.nonEmpty) {
      redirect("search", s"找不到${fails.size}位病人的零用金账户，扣款失败.")
    } else {
      order.billGenerated = true
      entityDao.saveOrUpdate(order)
      redirect("search", "生成成功")
    }
  }

  @response
  def commodities(): Seq[Properties] = {
    val query = OqlBuilder.from(classOf[Commodity], "b")
    get("q") foreach { q =>
      query.where("b.name like :name", "%" + q + "%")
    }
    entityDao.search(query).map { b =>
      new Properties(b, "id", "name")
    }
  }

  @response
  def brands(): Seq[Properties] = {
    val query = OqlBuilder.from(classOf[CommodityBrand], "b")
    get("q") foreach { q =>
      query.where("b.name like :name", "%" + q + "%")
    }
    entityDao.search(query).map { b =>
      new Properties(b, "id", "name")
    }
  }

  @response
  def units(): Seq[Properties] = {
    val query = OqlBuilder.from(classOf[CommodityUnit], "b")
    get("q") foreach { q =>
      query.where("b.name like :name", "%" + q + "%")
    }
    entityDao.search(query).map { b =>
      new Properties(b, "id", "name")
    }
  }
}
