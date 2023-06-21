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

package net.yushanginfo.hams.account.service.impl

import net.yushanginfo.hams.account.model.{Bankcard, BankcardBill, BankcardIncome, BankcardStat}
import net.yushanginfo.hams.account.service.BankcardService
import net.yushanginfo.hams.base.model.Yuan
import org.beangle.commons.collection.Collections
import org.beangle.data.dao.{EntityDao, OqlBuilder}

import java.time.{LocalDate, YearMonth, ZoneId, ZoneOffset}
import scala.collection.mutable

class BankcardServiceImpl extends BankcardService {
  var entityDao: EntityDao = _

  override def stat(yearMonth: YearMonth, forceStat: Boolean): Seq[BankcardStat] = {
    val q = OqlBuilder.from(classOf[BankcardStat], "ws")
    q.where("ws.yearMonth=:year", yearMonth)
    val existedStats = entityDao.search(q)
    if (existedStats.isEmpty || forceStat) {
      val lastMonth = yearMonth.minusMonths(1)
      val beginAt = yearMonth.atDay(1).atTime(0, 0, 0)
      val endAt = yearMonth.atEndOfMonth().atTime(23, 59, 59)

      val beginAtZ = beginAt.atZone(ZoneId.systemDefault).toInstant
      val endAtZ = endAt.atZone(ZoneId.systemDefault).toInstant

      val lq = OqlBuilder.from(classOf[BankcardStat], "ws")
      lq.where("ws.yearMonth=:last", lastMonth)
      val lastStats = entityDao.search(lq).map(x => (x.account, x)).toMap

      //find all pension for active inpatient
      val q = OqlBuilder.from(classOf[Bankcard], "w")
      q.where("w.createdOn<=:yearMonth", yearMonth.atEndOfMonth())
      q.where("w.inpatient.beginAt <= :endAt", endAt)
      q.where("w.inpatient.endAt is null or :beginAt >= w.inpatient.endAt", beginAt)
      val pensions = entityDao.search(q)

      val stats = new mutable.ArrayBuffer[BankcardStat]

      val bq = OqlBuilder.from(classOf[BankcardBill], "b")
      bq.where("b.payAt between :beginAt and :endAt", beginAtZ, endAtZ)
      val bills = entityDao.search(bq)

      val iq = OqlBuilder.from(classOf[BankcardIncome], "i")
      iq.where("i.updatedAt between :beginAt and :endAt", beginAtZ, endAtZ)
      val incomes = entityDao.search(iq)

      val billStats = bills.groupBy(_.account.inpatient)
      val incomeStats = incomes.groupBy(_.account.inpatient)

      pensions foreach { w =>
        val s = existedStats.find(x => x.account == w && x.yearMonth == yearMonth).getOrElse(new BankcardStat)
        stats.addOne(s)
        s.account = w
        s.yearMonth = yearMonth

        val incomes = incomeStats.get(w.inpatient) match
          case None => Yuan(0)
          case Some(bs) => Yuan(bs.map(_.amount.value).sum)

        val expenses = billStats.get(w.inpatient) match
          case None => Yuan(0)
          case Some(bs) => Yuan(bs.map(_.amount.value).sum)

        s.startBalance = lastStats.get(w) match
          case None => w.initBalance
          case Some(ws) => ws.endBalance

        s.update(incomes, expenses)
      }
      entityDao.saveOrUpdate(stats)

      stats.toSeq
    } else {
      existedStats
    }
  }
}
