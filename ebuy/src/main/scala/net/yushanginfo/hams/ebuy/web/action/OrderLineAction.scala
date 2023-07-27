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

import net.yushanginfo.hams.base.model.Inpatient
import net.yushanginfo.hams.ebuy.model.*
import org.beangle.commons.collection.Properties
import org.beangle.commons.lang.Strings
import org.beangle.data.dao.OqlBuilder
import org.beangle.web.action.annotation.response
import org.beangle.web.action.view.View
import org.beangle.webmvc.support.action.RestfulAction

import java.time.format.DateTimeFormatter
import java.time.{Instant, LocalDate, LocalDateTime}

class OrderLineAction extends RestfulAction[OrderLine] {

  override def search(): View = {
    getLong("orderLine.order.id") foreach { orderId =>
      put("order", entityDao.get(classOf[EbuyOrder], orderId))
    }
    super.search()
  }

  override protected def indexSetting(): Unit = {
    super.indexSetting()
    val query = OqlBuilder.from(classOf[EbuyOrder], "o")
    query.orderBy("o.beginOn desc")
    val orders = entityDao.search(query)
    put("orders", orders)
  }

  override protected def editSetting(line: OrderLine): Unit = {
    if (!line.persisted) {
      line.amount = 1
      getLong("orderLine.order.id") foreach { orderId =>
        line.order = entityDao.get(classOf[EbuyOrder], orderId)
      }
    }
    super.editSetting(line)
  }

  override protected def saveAndRedirect(line: OrderLine): View = {
    get("brand.id") foreach { i =>
      if (Strings.isBlank(i)) {
        line.brand = None
      } else {
        line.brand = Some(getOrCreateBrand(i))
      }
    }
    get("commodity.id") foreach { i =>
      line.commodity = getOrCreateCommodity(i)
    }
    get("unit.id") foreach { i =>
      line.unit = getOrCreateUnit(i)
    }
    super.saveAndRedirect(line)
  }

  private def getOrCreateBrand(value: String): CommodityBrand = {
    if (value.startsWith("0:")) {
      val name = Strings.substringAfter(value, "0:").trim()
      val query = OqlBuilder.from(classOf[CommodityBrand], "b")
      query.where("b.name=:name", name)
      entityDao.search(query).headOption match {
        case None =>
          val n = new CommodityBrand()
          n.name = name
          n.code = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now)
          n.beginOn = LocalDate.now
          n.updatedAt = Instant.now
          entityDao.saveOrUpdate(n)
          n
        case Some(b) => b
      }
    } else {
      entityDao.get(classOf[CommodityBrand], value.toInt)
    }
  }

  private def getOrCreateCommodity(value: String): Commodity = {
    if (value.startsWith("0:")) {
      val name = Strings.substringAfter(value, "0:").trim()
      val query = OqlBuilder.from(classOf[Commodity], "b")
      query.where("b.name=:name", name)
      entityDao.search(query).headOption match {
        case None =>
          val n = new Commodity()
          n.name = name
          n.code = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now)
          n.beginOn = LocalDate.now
          n.updatedAt = Instant.now
          entityDao.saveOrUpdate(n)
          n
        case Some(b) => b
      }
    } else {
      entityDao.get(classOf[Commodity], value.toInt)
    }
  }

  private def getOrCreateUnit(value: String): CommodityUnit = {
    if (value.startsWith("0:")) {
      val name = Strings.substringAfter(value, "0:").trim()
      val query = OqlBuilder.from(classOf[CommodityUnit], "b")
      query.where("b.name=:name", name)
      entityDao.search(query).headOption match {
        case None =>
          val n = new CommodityUnit()
          n.name = name
          n.code = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now)
          n.beginOn = LocalDate.now
          n.updatedAt = Instant.now
          entityDao.saveOrUpdate(n)
          n
        case Some(b) => b
      }
    } else {
      entityDao.get(classOf[CommodityUnit], value.toInt)
    }
  }

  @response
  def inpatients(): Seq[Properties] = {
    val query = OqlBuilder.from(classOf[Inpatient], "b")
    query.where("b.endAt is null")
    get("q") foreach { q =>
      query.where("b.name like :name or b.bedNo like :name", "%" + q + "%")
    }
    entityDao.search(query).map { b =>
      new Properties(b, "id", "description")
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
