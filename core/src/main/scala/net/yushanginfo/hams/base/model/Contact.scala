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

import org.beangle.data.model.LongId

/** 联系信息 */
class Contact extends LongId {

  /** 户籍地址 */
  var residenceAddress: Option[String] = None

  /** 现住址 */
  var address: Option[String] = None

  /** 联系电话 */
  var phone: Option[String] = None

  /** 邮编 */
  var postcode: Option[String] = None

  /** 单位名称 */
  var orgName: Option[String] = None

  /** 单位电话 */
  var orgPhone: Option[String] = None

  /** 单位邮编 */
  var orgPostcode: Option[String] = None
}
