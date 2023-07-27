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

import net.yushanginfo.hams.base.model.{Inpatient, Yuan}
import org.beangle.commons.collection.Collections
import org.beangle.data.model.LongId
import org.beangle.data.model.pojo.Updated

import java.time.{Instant, LocalDate, YearMonth}
import scala.collection.mutable

object Subsidy {
  def apply(inpatient: Inpatient): Subsidy = {
    val w = new Subsidy
    w.inpatient = inpatient
    w.balance = new Yuan(0L)
    w.initBalance = Yuan(0)
    w.createdOn = LocalDate.now()
    w
  }
}

/**
 * 养护补贴
 */
class Subsidy extends LongId {

  def this(id: Long) = {
    this()
    this.id = id
  }

  /** 病人 */
  var inpatient: Inpatient = _

  /** 余额 */
  var balance: Yuan = _

  /** 月度统计 */
  var stats: mutable.Buffer[SubsidyStat] = Collections.newBuffer[SubsidyStat]

  /** 起始年月 */
  var createdOn: LocalDate = _

  /** 初始余额 */
  var initBalance: Yuan = _

  def addStat(yearMonth: YearMonth, incomes: Yuan, expenses: Yuan): Option[SubsidyStat] = {
    stats.find(x => x.yearMonth == yearMonth) match {
      case None =>
        val initYearMonth = YearMonth.from(createdOn)
        if (initYearMonth == yearMonth) {
          val ws = new SubsidyStat
          ws.account = this
          ws.yearMonth = initYearMonth
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

/** 现金流量表
 * Statement of Cash Flow
 */
class SubsidyStat extends LongId, Updated {
  var account: Subsidy = _
  var yearMonth: YearMonth = _
  var startBalance: Yuan = _
  var endBalance: Yuan = _
  var incomes: Yuan = _
  var expenses: Yuan = _

  def update(incomes: Yuan, expenses: Yuan): SubsidyStat = {
    this.incomes = incomes
    this.expenses = if expenses.value > 0 then Yuan(0 - expenses.value) else expenses
    this.endBalance = this.startBalance + this.incomes + this.expenses
    this.updatedAt = Instant.now
    this
  }
}
