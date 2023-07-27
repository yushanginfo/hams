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

import net.yushanginfo.hams.ebuy.model.{Commodity, CommodityBrand, CommodityUnit}
import org.beangle.commons.collection.Properties
import org.beangle.data.dao.OqlBuilder
import org.beangle.web.action.annotation.response
import org.beangle.web.action.view.View
import org.beangle.webmvc.support.action.RestfulAction

import java.time.LocalDate

class CommodityAction extends RestfulAction[Commodity] {

  override protected def editSetting(entity: Commodity): Unit = {
    put("brands", entityDao.getAll(classOf[CommodityBrand]))
    put("units", entityDao.getAll(classOf[CommodityUnit]))

    if (!entity.persisted) entity.beginOn = LocalDate.now
    super.editSetting(entity)
  }

  override protected def saveAndRedirect(commodify: Commodity): View = {
    val brands = entityDao.find(classOf[CommodityBrand], getIntIds("brand"))
    val units = entityDao.find(classOf[CommodityUnit], getIntIds("unit"))
    commodify.brands.clear()
    commodify.units.clear()
    commodify.brands.addAll(brands)
    commodify.units.addAll(units)
    super.saveAndRedirect(commodify)
  }

}
