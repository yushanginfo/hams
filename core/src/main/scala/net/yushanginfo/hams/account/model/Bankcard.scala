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
import net.yushanginfo.hams.code.model.BankcardIncomeCategory
import org.beangle.data.model.LongId
import org.beangle.data.model.pojo.Updated

import java.time.{Instant, LocalDate}

/** 银行卡信息
 *
 */
class Bankcard extends LongId, Updated, Account {
  /** 病人信息 */
  var inpatient: Inpatient = _
  /** 银行 */
  var bank: String = _
  /** 卡号 */
  var cardNo: String = _
  /** 是否是存折 */
  var bankbook: Boolean = _
  /** 余额 */
  var balance: Yuan = _
  /** 起始年月 */
  var createdOn: LocalDate = _
  /** 初始余额 */
  var initBalance: Yuan = _

  def newBill(amount: Yuan, payAt: Instant, expenses: String): BankcardBill = {
    val i = new BankcardBill
    i.account = this
    i.amount = if (amount.value > 0) Yuan.Zero - amount else amount
    i.updatedAt = Instant.now
    i.payAt = payAt
    i.balance = this.balance + amount
    i.expenses = expenses
    this.balance = i.balance
    i
  }

  def newIncome(amount: Yuan, payAt: Instant, category: BankcardIncomeCategory): BankcardIncome = {
    val i = new BankcardIncome
    i.account = this
    i.amount = amount
    i.updatedAt = Instant.now
    i.payAt = payAt
    i.balance = this.balance + amount
    i.category = category
    this.balance = i.balance
    i
  }

}
