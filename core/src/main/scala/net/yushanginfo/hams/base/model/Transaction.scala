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

import org.beangle.data.model.Entity
import org.beangle.data.model.pojo.{Remark, Updated}

import java.time.{Instant, YearMonth}

trait Transaction extends Remark, Ordered[Transaction], Entity[Long] {
  /** 支付日期 */
  var payAt: Instant = _
  /** 金额 */
  var amount: Yuan = _
  /** 结余 */
  var balance: Yuan = _

  def update(amount: Yuan, payAt: Instant): Unit = {
    this.balance += (this.amount - amount)
    this.amount = amount
    this.payAt = payAt
  }

  def user: Account

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

  /** 按照时间和ID的发生顺序进行排序
   *
   * @param that
   * @return
   */
  override def compare(that: Transaction): Int = {
    val rs = this.payAt.compareTo(that.payAt)
    if (rs == 0) this.id.compare(that.id) else rs
  }

  def originBalance: Yuan = {
    balance - amount
  }

  def fixBillAmount(): Unit = {
    if (this.amount.value > 0) {
      this.amount = Yuan.Zero - this.amount
    }
  }
}

/** 现金流量表
 * Statement of Cash Flow
 */
class TransactionStat(val inpatient: Inpatient, val yearMonth: YearMonth) extends Updated {
  var startBalance: Yuan = _
  var endBalance: Yuan = _
  var incomes: Yuan = _
  var expenses: Yuan = _

  def update(incomes: Yuan, expenses: Yuan): TransactionStat = {
    this.incomes = incomes
    this.expenses = if expenses.value > 0 then Yuan(0 - expenses.value) else expenses
    this.endBalance = this.startBalance + this.incomes + this.expenses
    this.updatedAt = Instant.now
    this
  }

  /** 是否账户起始、结束金额都是0并且进账、出账都是0
   *
   * @return
   */
  def isZero: Boolean = {
    startBalance == Yuan.Zero && endBalance == Yuan.Zero && incomes == Yuan.Zero && expenses == Yuan.Zero
  }
}
