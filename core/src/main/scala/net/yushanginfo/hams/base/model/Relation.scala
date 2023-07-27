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

package net.yushanginfo.hams.base.model

import net.yushanginfo.hams.code.model.Relationship
import org.beangle.data.model.LongId
import org.beangle.data.model.pojo.Named

/** 联系人
 */
class Relation extends LongId, Named {
  var inpatient: Inpatient = _
  /** 序号，从1开始 */
  var idx: Int = _
  /** 关系 */
  var relationship: Relationship = _
  /** 联系电话 */
  var phone: Option[String] = None
  /** 联系地址 */
  var address: Option[String] = None
  /** 街道 */
  var subdistrict: Option[String] = None
}
