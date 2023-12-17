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

package net.yushanginfo.hams.wallet.web.action

import net.yushanginfo.hams.base.model.{Ward, Yuan}
import net.yushanginfo.hams.wallet.model.Wallet
import net.yushanginfo.hams.wallet.service.DepositService
import org.beangle.data.dao.EntityDao
import org.beangle.web.action.support.ActionSupport
import org.beangle.web.action.view.View
import org.beangle.webmvc.support.action.EntityAction

import java.time.{Year, YearMonth}
import scala.collection.mutable

/**
 * 病区住院押金统计
 */
class DepositStatAction extends ActionSupport, EntityAction[Wallet] {
  var entityDao: EntityDao = _

  var depositService: DepositService = _

  def index(): View = {
    val thisYear = Year.now
    var yearMonth = YearMonth.now()
    val yearMonths = new mutable.ArrayBuffer[YearMonth]
    (1 to 12) foreach { i =>
      yearMonths.addOne(yearMonth)
      yearMonth = yearMonth.minusMonths(1)
    }
    put("yearMonths", yearMonths)
    forward()
  }

  def stat(): View = {
    val ym = YearMonth.parse(get("yearMonth", ""))

    val stats = depositService.stat(ym)
    put("stats", stats)
    val wardStats = stats.groupBy(w => w.inpatient.ward)
    put("wardStats", wardStats)
    put("wards", wardStats.keys)
    forward()
  }

  def ward(): View = {
    val ym = YearMonth.parse(get("yearMonth", ""))
    val stats = depositService.stat(ym)
    val wardStats = stats.groupBy(_.inpatient.ward)
    val startBalances = new mutable.HashMap[Ward, Yuan]
    val endBalances = new mutable.HashMap[Ward, Yuan]
    val incomes = new mutable.HashMap[Ward, Yuan]
    val expenses = new mutable.HashMap[Ward, Yuan]

    wardStats foreach { case (ward, ws) =>
      startBalances.put(ward, Yuan(ws.map(_.startBalance.value).sum))
      endBalances.put(ward, Yuan(ws.map(_.endBalance.value).sum))
      incomes.put(ward, Yuan(ws.map(_.incomes.value).sum))
      expenses.put(ward, Yuan(ws.map(_.expenses.value).sum))
    }
    put("startBalances", startBalances)
    put("endBalances", endBalances)
    put("incomes", incomes)
    put("expenses", expenses)

    put("startBalances_sum", Yuan(startBalances.values.map(_.value).sum))
    put("endBalances_sum", Yuan(endBalances.values.map(_.value).sum))
    put("incomes_sum", Yuan(incomes.values.map(_.value).sum))
    put("expenses_sum", Yuan(expenses.values.map(_.value).sum))

    put("wards", wardStats.keys)
    put("yearMonth", ym)
    forward()
  }
}
