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

import net.yushanginfo.hams.base.model.{Inpatient, Yuan}
import org.beangle.data.model.LongId
import org.beangle.data.model.pojo.Updated

import java.time.{Instant, LocalDateTime}

/** 住院押金
 */
class Deposit extends LongId, Updated {
  def this(inpatient: Inpatient, payAt: Instant) = {
    this()
    this.inpatient = inpatient
    this.payAt = payAt
    this.updatedAt = Instant.now
  }

  /** 病人 */
  var inpatient: Inpatient = _
  /** 金额 */
  var amount: Yuan = _
  /** 支付时间 */
  var payAt: Instant = _
  /** 退回时间 */
  var refundAt: Option[Instant] = None
}