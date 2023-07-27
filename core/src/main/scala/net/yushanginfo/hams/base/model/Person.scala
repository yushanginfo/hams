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

import net.yushanginfo.hams.code.model.*
import org.beangle.data.model.LongId

import java.time.LocalDate

/** 人员基本信息
 */
class Person extends LongId {
  /** 性别 */
  var gender: Gender = _

  /** 姓名 */
  var name: String = _

  /** 姓名拼音 */
  var phoneticName: Option[String] = None

  /** 五笔姓名 */
  var wubiName: Option[String] = None

  /** 职业 */
  var profession: Option[String] = None

  /** 身份证件类型 */
  var idType: IdType = _

  /** 证件号码 */
  var idcard: Option[String] = None

  /** 民族 */
  var nation: Option[Nation] = None

  /** 国籍/地区 */
  var country: Option[Country] = None

  /** 出生日期 */
  var birthday: Option[LocalDate] = None

  /** 婚姻状况 */
  var maritalStatus: Option[MaritalStatus] = None

  /** 出生地(省) */
  var birthProvince: Option[String] = None

  /** 出生地(区、县) */
  var birthDistrict: Option[String] = None

  /** 籍贯 */
  var homeTown: Option[String] = None
}
