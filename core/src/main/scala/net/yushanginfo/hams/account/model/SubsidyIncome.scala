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

import net.yushanginfo.hams.base.model.{Account, Inpatient, Transaction, Yuan}
import org.beangle.data.model.LongId
import org.beangle.data.model.pojo.Updated

/** 养护补贴收入明细 */
class SubsidyIncome extends LongId, Updated, Transaction {
  /** 养护补贴 */
  var account: Subsidy = _
  /** 渠道 */
  var channel: String = _

  override def user: Account = account
}
