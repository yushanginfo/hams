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

package net.yushanginfo.hams.account.web.action

import net.yushanginfo.hams.account.model.{Subsidy, SubsidyStat}
import net.yushanginfo.hams.account.service.SubsidyService
import net.yushanginfo.hams.base.model.{Ward, Yuan}
import org.beangle.data.dao.{EntityDao, OqlBuilder}
import org.beangle.web.action.support.ActionSupport
import org.beangle.web.action.view.View
import org.beangle.webmvc.support.action.EntityAction

import java.time.{Year, YearMonth}
import scala.collection.mutable

class SubsidyStatAction extends ActionSupport, EntityAction[Subsidy] {
  var entityDao: EntityDao = _
  var subsidyService: SubsidyService = _

  /**
   * 统计首页
   *
   * @return
   */
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
    val forceStat = getBoolean("force", ym.getYear == Year.now().getValue)
    val q = OqlBuilder.from(classOf[SubsidyStat], "ws")
    q.where("ws.yearMonth=:yearMonth", ym)
    var stats = entityDao.search(q)
    if (stats.isEmpty || forceStat) {
      stats = subsidyService.stat(ym, forceStat)
    }
    put("stats", stats)
    val wardStats = stats.groupBy(w => w.account.inpatient.ward)
    put("stats", wardStats)
    put("wards", wardStats.keys)
    forward()
  }

  /**
   * 月度汇总表
   *
   * @return
   */
  def inpatient(): View = {
    val ym = YearMonth.parse(get("yearMonth", ""))
    val ward = entityDao.get(classOf[Ward], getIntId("ward"))
    val wards = entityDao.getAll(classOf[Ward])
    val q = OqlBuilder.from(classOf[SubsidyStat], "ws")
    q.where("ws.yearMonth=:yearMonth", ym)
    q.where("ws.account.inpatient.ward=:ward", ward)
    q.orderBy("ws.account.inpatient.bedNo")
    put("stats", entityDao.search(q))
    put("yearMonth", ym)
    put("ward", ward)
    forward()
  }

  def ward(): View = {
    val ym = YearMonth.parse(get("yearMonth", ""))
    val q = OqlBuilder.from(classOf[SubsidyStat], "ws")
    q.where("ws.yearMonth=:yearMonth", ym)
    val stats = entityDao.search(q)
    val wardStats = stats.groupBy(_.account.inpatient.ward)
    val startBalances = new mutable.HashMap[Ward, Yuan]
    val endBalances = new mutable.HashMap[Ward, Yuan]
    val incomes = new mutable.HashMap[Ward, Yuan]
    val expenses = new mutable.HashMap[Ward, Yuan]

    wardStats foreach { case (ward, ws) =>
      if(ward.id==1){
        println(ws.map(_.startBalance.value).mkString(","))
      }
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
