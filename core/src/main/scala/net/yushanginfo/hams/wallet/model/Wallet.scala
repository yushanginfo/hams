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
import org.beangle.commons.collection.Collections
import org.beangle.data.model.LongId
import org.beangle.data.model.pojo.Updated

import java.time.{Instant, YearMonth}
import scala.collection.mutable

object Wallet {
  def apply(inpatient: Inpatient, walletType: WalletType): Wallet = {
    val w = new Wallet
    w.inpatient = inpatient
    w.walletType = walletType
    w.balance = new Yuan(0L)
    w
  }
}

/**
 * 钱包
 */
class Wallet extends LongId {

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

  /** 月度统计 */
  var stats: mutable.Buffer[WalletStat] = Collections.newBuffer[WalletStat]

  /** 起始年月 */
  var createdOn: YearMonth = _

  /** 初始余额 */
  var initBalance: Yuan = _

  def income(amount: Yuan, payAt: Instant, channel: IncomeChannel): Income = {
    val i = new Income
    i.wallet = this
    i.amount = amount
    i.inpatient = this.inpatient
    i.updatedAt = Instant.now
    i.payAt = payAt
    i.balance = this.balance + amount
    i.channel = channel
    this.balance = i.balance
    i
  }

  def addStat(yearMonth: YearMonth, incomes: Yuan, expenses: Yuan): Option[WalletStat] = {
    stats.find(x => x.yearMonth == yearMonth) match {
      case None =>
        if (createdOn == yearMonth) {
          val ws = new WalletStat
          ws.wallet = this
          ws.yearMonth = createdOn
          ws.startBalance = this.initBalance
          ws.update(incomes, expenses)
          Some(ws)
        } else {
          None
        }
      case Some(w) =>
        Some(w.update(incomes, expenses))
    }
  }
}

/** 钱包的现金流量表
 * Statement of Cash Flow
 */
class WalletStat extends LongId, Updated {
  var wallet: Wallet = _
  var yearMonth: YearMonth = _
  var startBalance: Yuan = _
  var endBalance: Yuan = _
  var incomes: Yuan = _
  var expenses: Yuan = _

  def update(incomes: Yuan, expenses: Yuan): WalletStat = {
    this.incomes = incomes
    this.expenses = if expenses.value > 0 then Yuan(0 - expenses.value) else expenses
    this.endBalance = this.startBalance + this.incomes + this.expenses
    this.updatedAt = Instant.now
    this
  }
}
