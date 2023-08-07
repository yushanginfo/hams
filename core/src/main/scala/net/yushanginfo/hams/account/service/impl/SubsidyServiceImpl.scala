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

import net.yushanginfo.hams.account.model.{Subsidy, SubsidyBill, SubsidyIncome, SubsidyStat}
import net.yushanginfo.hams.account.service.{SubsidyService, TransactionService}
import net.yushanginfo.hams.base.model.{Inpatient, Yuan}
import org.beangle.data.dao.{EntityDao, OqlBuilder}

import java.time.*
import scala.collection.mutable

class SubsidyServiceImpl extends SubsidyService {
  var entityDao: EntityDao = _
  var transactionService: TransactionService = _

  override def adjustBalance(subsidy: Subsidy, beginAt: Instant): Yuan = {
    val last = transactionService.getLast(beginAt, List(classOf[SubsidyBill], classOf[SubsidyIncome]), Map("account" -> subsidy))
    var balance = last.map(_.balance).getOrElse(subsidy.initBalance)
    val ts = transactionService.find(beginAt, List(classOf[SubsidyBill], classOf[SubsidyIncome]), Map("account" -> subsidy))
    ts foreach { t =>
      balance += t.amount
      t.balance = balance
    }
    subsidy.balance = balance
    entityDao.saveOrUpdate(subsidy)
    entityDao.saveOrUpdate(ts)
    if (ts.isEmpty) then balance else ts.head.balance
  }

  override def get(inpatient: Inpatient): Option[Subsidy] = {
    val q = OqlBuilder.from(classOf[Subsidy], "s")
    q.where("s.inpatient=:inpatient", inpatient)
    entityDao.search(q).headOption
  }

  override def getOrCreate(inpatient: Inpatient): Subsidy = {
    val q = OqlBuilder.from(classOf[Subsidy], "s")
    q.where("s.inpatient=:inpatient", inpatient)
    entityDao.search(q).headOption match
      case None =>
        val w = Subsidy(inpatient)
        entityDao.saveOrUpdate(w)
        w
      case Some(s) => s
  }

  override def getIncome(subsidy: Subsidy, amount: Yuan, payAt: Instant): Option[SubsidyIncome] = {
    val q = OqlBuilder.from(classOf[SubsidyIncome], "si")
    q.where("si.account=:account", subsidy)
    q.where("si.amount=:amount", amount)
    q.where("si.payAt=:payAt", payAt)
    entityDao.search(q).headOption
  }

  override def getBill(subsidy: Subsidy, amount: Yuan, payAt: Instant): Option[SubsidyBill] = {
    val q = OqlBuilder.from(classOf[SubsidyBill], "si")
    q.where("si.account=:account", subsidy)
    q.where("si.amount=:amount", amount)
    q.where("si.payAt=:payAt", payAt)
    entityDao.search(q).headOption
  }

  override def stat(yearMonth: YearMonth, forceStat: Boolean): Seq[SubsidyStat] = {
    val q = OqlBuilder.from(classOf[SubsidyStat], "ws")
    q.where("ws.yearMonth=:year", yearMonth)
    val existedStats = entityDao.search(q)
    if (existedStats.isEmpty || forceStat) {
      val lastMonth = yearMonth.minusMonths(1)
      val beginAt = yearMonth.atDay(1).atTime(0, 0, 0).atZone(ZoneId.systemDefault).toInstant
      val endAt = yearMonth.atEndOfMonth().atTime(23, 59, 59).atZone(ZoneId.systemDefault).toInstant

      val lq = OqlBuilder.from(classOf[SubsidyStat], "ws")
      lq.where("ws.yearMonth=:last", lastMonth)
      val lastStats = entityDao.search(lq).map(x => (x.account, x)).toMap

      //find all subsidy wallet for active inpatient
      val q = OqlBuilder.from(classOf[Subsidy], "w")
      q.where("w.createdOn<=:yearMonth", yearMonth.atEndOfMonth())
      //q.where("w.inpatient.beginAt <= :endAt", endAt)
      //q.where("w.inpatient.endAt is null or :beginAt <= w.inpatient.endAt", beginAt)
      val subsidys = entityDao.search(q)

      val stats = new mutable.ArrayBuffer[SubsidyStat]

      val bq = OqlBuilder.from(classOf[SubsidyBill], "b")
      bq.where("b.payAt between :beginAt and :endAt", beginAt, endAt)
      val bills = entityDao.search(bq)

      val iq = OqlBuilder.from(classOf[SubsidyIncome], "i")
      iq.where("i.payAt between :beginAt and :endAt", beginAt, endAt)
      val incomes = entityDao.search(iq)

      val billStats = bills.groupBy(_.account.inpatient)
      val incomeStats = incomes.groupBy(_.account.inpatient)

      subsidys foreach { w =>
        val s = existedStats.find(x => x.account == w && x.yearMonth == yearMonth).getOrElse(new SubsidyStat)
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
