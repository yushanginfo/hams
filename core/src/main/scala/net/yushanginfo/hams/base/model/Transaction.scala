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

import org.beangle.data.model.pojo.Remark

import java.time.Instant

trait Transaction extends Remark, Ordered[Transaction] {
  /** 支付日期 */
  var payAt: Instant = _
  /** 金额 */
  var amount: Yuan = _
  /** 结余 */
  var balance: Yuan = _

  def updatePayAt(newPayAt: Instant): Instant = {
    if (null == this.payAt) {
      this.payAt = newPayAt
      newPayAt
    } else {
      val minPayAt = if this.payAt.isBefore(newPayAt) then this.payAt else newPayAt
      this.payAt = newPayAt
      minPayAt
    }
  }

  override def compare(that: Transaction): Int = {
    this.payAt.compareTo(that.payAt)
  }
}
