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

import net.yushanginfo.hams.code.model.{Gender, InpatientStatus}
import org.beangle.data.model.LongId
import org.beangle.data.model.pojo.{Coded, Named, TemporalOn}

import java.time.LocalDate

/**
 * 住院病人
 * 住院号作为唯一键
 */
class Inpatient extends LongId, Coded, Named, TemporalOn {

  /** 性别 */
  var gender: Gender = _

  /** 住院号 */
  var number: String = _

  /** 所在病区 */
  var ward: Ward = _

  /** 状态 */
  var status: InpatientStatus = _

  /** 家属1姓名 */
  var familyMember1Name: String = _

  /** 家属1电话 */
  var familyMember1Mobile: String = _

  /** 家属2姓名 */
  var familyMember2Name: Option[String] = None

  /** 家属2电话 */
  var familyMember2Mobile: Option[String] = None

  /** 请假日期 */
  var leaveOn: Option[LocalDate] = None

  /** 预计销假日期 */
  var backOn: Option[LocalDate] = None

}
