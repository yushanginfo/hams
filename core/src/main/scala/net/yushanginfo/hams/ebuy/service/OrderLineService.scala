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

package net.yushanginfo.hams.ebuy.service

import net.yushanginfo.hams.base.model.{Inpatient, Yuan}
import net.yushanginfo.hams.ebuy.model.{EbuyOrder, OrderLine}

trait OrderLineService {

  def createLine(order: EbuyOrder, inpatient: Inpatient, commodityName: String,
                 brandName: String, unitName: String, amount: Float,
                 price: Option[Yuan], payable: Option[Yuan],payment: Option[Yuan]): OrderLine

}
