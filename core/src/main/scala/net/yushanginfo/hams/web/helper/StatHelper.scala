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

package net.yushanginfo.hams.web.helper

import net.yushanginfo.hams.base.model.{TransactionStat, Ward, Yuan}
import org.beangle.web.action.context.ActionContext

import java.time.YearMonth
import scala.collection.mutable

object StatHelper {

  def putStats(ym: YearMonth, stats: collection.Seq[TransactionStat]): Unit = {
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
    put("wardStats", wardStats)
    put("yearMonth", ym)
  }

  private final def put(key: String, value: Any): Unit = {
    ActionContext.current.attribute(key, value)
  }
}
