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

import net.yushanginfo.hams.ebuy.model.{Commodity, CommodityBrand, CommodityUnit}
import net.yushanginfo.hams.ebuy.service.CommodityService
import org.beangle.commons.lang.{Numbers, Strings}
import org.beangle.data.dao.{EntityDao, OqlBuilder}

import java.time.format.DateTimeFormatter
import java.time.{Instant, LocalDate, LocalDateTime}

class CommodityServiceImpl extends CommodityService {

  var entityDao: EntityDao = _

  private def processName(value: String): String = {
    if (value.startsWith("0:")) {
      Strings.substringAfter(value, "0:").trim()
    } else {
      value
    }
  }

  override def getOrCreateBrand(value: String): CommodityBrand = {
    if (Strings.isBlank(value)) return null
    val name = processName(value)
    if (Numbers.isDigits(name)) {
      entityDao.get(classOf[CommodityBrand], name.toInt)
    } else {
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
    }
  }

  override def getOrCreateCommodity(value: String): Commodity = {
    if (Strings.isBlank(value)) return null
    val name = processName(value)
    if (Numbers.isDigits(name)) {
      entityDao.get(classOf[Commodity], name.toInt)
    } else {
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
    }
  }

  override def getOrCreateUnit(value: String): CommodityUnit = {
    if (Strings.isBlank(value)) return null
    val name = processName(value)
    if (Numbers.isDigits(name)) {
      entityDao.get(classOf[CommodityUnit], name.toInt)
    } else {
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
    }
  }

}
