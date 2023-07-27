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
import net.yushanginfo.hams.code.model.IncomeChannel
import org.beangle.data.model.LongId
import org.beangle.data.model.pojo.Updated

import java.time.Instant

/** 收入明细 */
class Income extends LongId, Updated {
  /** 病人 */
  var inpatient: Inpatient = _
  /** 钱包 */
  var wallet: Wallet = _
  /** 支付日期 */
  var payAt: Instant = _
  /** 金额 */
  var amount: Yuan = _
  /** 结余 */
  var balance: Yuan = _
  /** 渠道 */
  var channel: IncomeChannel = _

  def originBalance: Yuan = {
    balance - amount
  }
}
