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

import net.yushanginfo.hams.base.model.{Account, Inpatient, Yuan}
import net.yushanginfo.hams.code.model.IncomeChannel
import org.beangle.data.model.LongId

import java.time.{Instant, LocalDate, ZoneId}

object Wallet {
  def apply(inpatient: Inpatient, walletType: WalletType): Wallet = {
    val w = new Wallet
    w.inpatient = inpatient
    w.walletType = walletType
    w.createdOn = inpatient.beginAt.atZone(ZoneId.systemDefault).toLocalDate
    w.balance = new Yuan(0L)
    w.initBalance = Yuan.Zero
    w
  }
}

/**
 * 钱包
 */
class Wallet extends LongId, Account {

  def this(id: Long) = {
    this()
    this.id = id
  }

  /** 病人 */
  var inpatient: Inpatient = _

  /** 余额 */
  var balance: Yuan = _

  /** 钱包类型 */
  var walletType: WalletType = _

  /** 起始年月 */
  var createdOn: LocalDate = _

  /** 初始余额 */
  var initBalance: Yuan = _

  def newBill(amount: Yuan, payAt: Instant, goods: String): Bill = {
    val i = new Bill
    i.wallet = this
    i.amount = if (amount.value > 0) Yuan.Zero - amount else amount
    i.updatedAt = Instant.now
    i.payAt = payAt
    i.balance = this.balance + i.amount
    i.goods = goods
    this.balance = i.balance
    i
  }

  def newIncome(amount: Yuan, payAt: Instant, channel: IncomeChannel): Income = {
    val i = new Income
    i.wallet = this
    i.amount = if (amount.value < 0) Yuan.Zero - amount else amount
    i.updatedAt = Instant.now
    i.payAt = payAt
    i.balance = this.balance + amount
    i.channel = channel
    this.balance = i.balance
    i
  }
}
