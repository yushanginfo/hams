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

package net.yushanginfo.hams.account.model

import net.yushanginfo.hams.base.model.{Account, Inpatient, Yuan}
import org.beangle.data.model.LongId

import java.time.LocalDate

object AttendFee {
  def apply(inpatient: Inpatient): AttendFee = {
    val w = new AttendFee
    w.inpatient = inpatient
    w.balance = new Yuan(0L)
    w.initBalance = Yuan(0)
    w.createdOn = LocalDate.now()
    w
  }
}

/**
 * 陪护费
 */
class AttendFee extends LongId, Account {

  def this(id: Long) = {
    this()
    this.id = id
  }

  /** 病人 */
  var inpatient: Inpatient = _

  /** 余额 */
  var balance: Yuan = _

  /** 起始年月 */
  var createdOn: LocalDate = _

  /** 初始余额 */
  var initBalance: Yuan = _
}
