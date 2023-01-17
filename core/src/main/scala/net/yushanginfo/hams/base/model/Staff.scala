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

import net.yushanginfo.hams.code.model.{Gender, StaffType, WorkStatus}
import org.beangle.data.model.LongId
import org.beangle.data.model.pojo.*

import java.time.LocalDate

/** 员工
 * */
class Staff extends LongId, Coded, Named, Updated, TemporalOn, Remark {

  /** 员工类型 */
  var staffType: StaffType = _

  /** 身份证号 */
  var idNumber: Option[String] = None

  /** 出生日期 */
  var birthday: Option[LocalDate] = None

  /** 性别 */
  var gender: Gender = _

  /** 部门 */
  var department: Department = _

  /** 是否在编 */
  var formalHr: Boolean = _

  /** 在职状态 */
  var status: WorkStatus = _

  /** 家庭住址 */
  var homeAddress: Option[String] = None

  /** 家庭电话 */
  var homePhone: Option[String] = None

  /** 移动电话 */
  var mobile: Option[String] = None

  /** 户籍地址 */
  var residentAddress: Option[String] = None
}
