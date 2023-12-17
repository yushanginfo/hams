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

package net.yushanginfo.hams.wallet.service.impl

import net.yushanginfo.hams.base.model.*
import net.yushanginfo.hams.wallet.model.Deposit
import net.yushanginfo.hams.wallet.service.DepositService
import org.beangle.commons.collection.Collections
import org.beangle.data.dao.{EntityDao, OqlBuilder}

import java.time.{Instant, YearMonth, ZoneId}
import scala.collection.mutable

class DepositServiceImpl extends DepositService {
  var entityDao: EntityDao = _

  override def stat(yearMonth: YearMonth): collection.Seq[TransactionStat] = {
    val beginAt = yearMonth.atDay(1).atTime(0, 0, 0).atZone(ZoneId.systemDefault).toInstant
    val endAt = yearMonth.atEndOfMonth().atTime(23, 59, 59).atZone(ZoneId.systemDefault).toInstant
    val accounts = Collections.newSet[Inpatient]

    val aq = OqlBuilder.from(classOf[Deposit], "w")
    aq.where("w.payAt<=:beginAt", beginAt)
    aq.where("w.refundAt is null")
    accounts ++= entityDao.search(aq).map(_.inpatient)

    val iq = OqlBuilder.from(classOf[Deposit], "i")
    iq.where("i.payAt between :beginAt and :endAt", beginAt, endAt)
    iq.orderBy("i.payAt desc")
    val incomes = entityDao.search(iq)

    val bq = OqlBuilder.from(classOf[Deposit], "b")
    bq.where("b.refundAt between :beginAt and :endAt", beginAt, endAt)
    bq.orderBy("b.refundAt desc")
    val bills = entityDao.search(bq)

    val billStats = bills.groupBy(_.inpatient)
    val incomeStats = incomes.groupBy(_.inpatient)
    accounts ++= billStats.keySet
    accounts ++= incomeStats.keySet

    val stats = new mutable.ArrayBuffer[TransactionStat]
    //有余额或者产生过流水的
    accounts foreach { acc =>
      val s = new TransactionStat(acc, yearMonth)
      val incomes = incomeStats.get(acc) match
        case None => Yuan(0)
        case Some(bs) => Yuan(bs.map(_.amount.value).sum)

      val expenses = billStats.get(acc) match
        case None => Yuan(0)
        case Some(bs) => Yuan(bs.map(_.amount.value).sum)

      s.startBalance = findInitBalance(acc, incomeStats.get(acc), billStats.get(acc), beginAt)
      s.update(incomes, expenses)

      if (!s.isZero) stats.addOne(s)
    }
    stats
  }

  def findInitBalance(inpatient: Inpatient, incomes: Option[Seq[Deposit]], bills: Option[Seq[Deposit]],
                      beginAt: Instant): Yuan = {
    val rs = new mutable.ArrayBuffer[Transaction]
    incomes foreach { i => rs ++= i.map(d => d.toIncome) }
    bills foreach { b => rs ++= b.map(d => d.toBill) }
    if (rs.nonEmpty) {
      val ts = rs.sorted
      ts.head.originBalance
    } else {
      val last = getLast(inpatient, beginAt)
      last.map(_.balance).getOrElse(Yuan.Zero)
    }
  }

  private def getLast(inpatient: Inpatient, before: Instant): Option[Transaction] = {
    val ts = Collections.newBuffer[Transaction]

    var query = OqlBuilder.from(classOf[Deposit], "t")
    query.where("t.payAt<:before", before)
    query.orderBy("t.payAt desc")
    query.where(s"t.inpatient =:inpatient", inpatient)
    query.limit(1, 1)
    ts ++= entityDao.search(query).map(_.toIncome)

    query = OqlBuilder.from(classOf[Deposit], "t")
    query.where("t.refundAt<:before", before)
    query.orderBy("t.refundAt desc")
    query.where(s"t.inpatient =:inpatient", inpatient)
    query.limit(1, 1)
    ts ++= entityDao.search(query).map(_.toBill)

    ts.sorted.lastOption
  }

}
