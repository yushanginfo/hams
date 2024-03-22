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

import java.time.{YearMonth, ZoneId}
import scala.collection.mutable

class DepositServiceImpl extends DepositService {
  var entityDao: EntityDao = _

  override def stat(yearMonth: YearMonth): collection.Seq[TransactionStat] = {
    val beginAt = yearMonth.atDay(1).atTime(0, 0, 0).atZone(ZoneId.systemDefault).toInstant
    val endAt = yearMonth.atEndOfMonth().atTime(23, 59, 59).atZone(ZoneId.systemDefault).toInstant
    val accounts = Collections.newSet[Inpatient]

    //所有该区间之前缴费，但在该区间没有退回的押金=>余额
    val aq = OqlBuilder.from(classOf[Deposit], "w")
    aq.where("w.payAt<=:beginAt", beginAt)
    aq.where("w.refundAt is null or w.refundAt > :endAt", endAt)
    val balances = entityDao.search(aq).groupBy(_.inpatient) // [inpatient, List(deposit)]
    accounts ++= balances.keySet

    //该区间的收入
    val iq = OqlBuilder.from(classOf[Deposit], "i")
    iq.where("i.payAt between :beginAt and :endAt", beginAt, endAt)
    iq.orderBy("i.payAt desc")
    val incomes = entityDao.search(iq)

    //该区间的支出
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

      s.startBalance = findInitBalance(acc, balances.getOrElse(acc, List.empty))
      s.update(incomes, expenses)

      if (!s.isZero) stats.addOne(s)
    }
    stats
  }

  def findInitBalance(inpatient: Inpatient, balances: Seq[Deposit]): Yuan = {
    Yuan(balances.map(_.amount.value).sum)
  }

}
