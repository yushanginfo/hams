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

package net.yushanginfo.hams.wallet.model

import net.yushanginfo.hams.base.model.Inpatient
import net.yushanginfo.hams.code.model.LeaveCategory
import org.beangle.data.model.LongId
import org.beangle.data.model.pojo.{Coded, Updated}

import java.time.Instant

/**
 * 请假申请
 */
class LeaveApply extends LongId, Updated, Coded {

  var inpatient: Inpatient = _

  /** 请假起始日期 */
  var leaveAt: Instant = _

  /** 实际销假日期 */
  var backAt: Option[Instant] = None

  /** 预计销假日期 */
  var predictBackAt: Option[Instant] = None

  /** 事由 */
  var reasons: String = _

  /** 请假分类 */
  var category: LeaveCategory = _

  /** 是否通过 */
  var approved: Option[Boolean] = None

}
