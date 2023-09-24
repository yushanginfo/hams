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

import net.yushanginfo.hams.account.service.TransactionService
import net.yushanginfo.hams.base.model.{Account, Transaction, TransactionStat, Yuan}
import org.beangle.commons.bean.Properties
import org.beangle.commons.collection.Collections
import org.beangle.data.dao.{EntityDao, OqlBuilder}

import java.time.{Instant, YearMonth, ZoneId}
import scala.collection.mutable

class TransactionServiceImpl extends TransactionService {

  var entityDao: EntityDao = _

  override def adjustBalance(account: Account, incomeClazz: Class[_], billClazz: Class[_], userAttr: String, beginAt: Instant): Yuan = {
    val last = getLast(account, incomeClazz, billClazz, userAttr, beginAt)
    var balance = last.map(_.balance).getOrElse(account.initBalance)
    val ts = find(account, incomeClazz, billClazz, userAttr, beginAt)
    ts foreach { t =>
      balance += t.amount
      t.balance = balance
    }
    Properties.set(account, "balance", balance)
    entityDao.saveOrUpdate(account)
    entityDao.saveOrUpdate(ts)
    if (ts.isEmpty) then balance else ts.head.balance
  }

  override def getLast(account: Account, incomeClazz: Class[_], billClazz: Class[_], userAttr: String, before: Instant): Option[Transaction] = {
    val ts = Collections.newBuffer[Transaction]
    List(incomeClazz, billClazz) foreach { clazz =>
      val query = OqlBuilder.from[Transaction](clazz.getName, "t")
      query.where("t.payAt<:before", before)
      query.orderBy("t.payAt desc")
      query.where(s"t.${userAttr} =:account", account)
      query.limit(1, 1)
      ts ++= entityDao.search(query)
    }
    ts.sorted.lastOption
  }

  override def find(account: Account, incomeClazz: Class[_], billClazz: Class[_], userAttr: String, beginAt: Instant): collection.Seq[Transaction] = {
    val ts = Collections.newBuffer[Transaction]
    List(incomeClazz, billClazz) foreach { clazz =>
      val query = OqlBuilder.from[Transaction](clazz.getName, "t")
      query.where("t.payAt>=:before", beginAt)
      query.where(s"t.${userAttr} =:account", account)
      ts ++= entityDao.search(query)
    }
    ts.sorted
  }

  override def stat(yearMonth: YearMonth, accountClazz: Class[_], incomeClazz: Class[_], billClazz: Class[_], userAttr: String,
                    params: collection.Map[String, Any]): collection.Seq[TransactionStat] = {
    val beginAt = yearMonth.atDay(1).atTime(0, 0, 0).atZone(ZoneId.systemDefault).toInstant
    val endAt = yearMonth.atEndOfMonth().atTime(23, 59, 59).atZone(ZoneId.systemDefault).toInstant
    var index = 0
    val accounts = Collections.newSet[Account]
    val aq = OqlBuilder.from[Account](accountClazz.getName, "w")
    aq.where("w.createdOn<=:endofMonth", yearMonth.atEndOfMonth())
    index = 0
    params.foreach { case (k, v) =>
      aq.where(s"w.${k}=:v${index}", v)
      index += 1
    }
    accounts ++= entityDao.search(aq)

    val iq = OqlBuilder.from[Transaction](incomeClazz.getName, "i")
    iq.where("i.payAt between :beginAt and :endAt", beginAt, endAt)
    iq.orderBy("i.payAt desc")
    index = 0
    params.foreach { case (k, v) =>
      iq.where(s"i.${userAttr}.${k}=:v${index}", v)
      index += 1
    }
    val incomes = entityDao.search(iq)

    val bq = OqlBuilder.from[Transaction](billClazz.getName, "b")
    bq.where("b.payAt between :beginAt and :endAt", beginAt, endAt)
    bq.orderBy("b.payAt desc")
    index = 0
    params.foreach { case (k, v) =>
      bq.where(s"b.${userAttr}.${k}=:v${index}", v)
      index += 1
    }
    val bills = entityDao.search(bq)

    val billStats = bills.groupBy(_.user)
    val incomeStats = incomes.groupBy(_.user)
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

      s.startBalance = findInitBalance(acc, incomeStats.get(acc), billStats.get(acc), incomeClazz, billClazz, userAttr, beginAt)
      s.update(incomes, expenses)

      if (!s.isZero) stats.addOne(s)
    }
    stats
  }

  def findInitBalance(account: Account, incomes: Option[Seq[Transaction]], bills: Option[Seq[Transaction]],
                      incomeClazz: Class[_], billClazz: Class[_], userAttr: String, beginAt: Instant): Yuan = {
    val rs = new mutable.ArrayBuffer[Transaction]
    incomes foreach { i => rs ++= i.headOption }
    bills foreach { b => rs ++= b.headOption }
    if (rs.nonEmpty) {
      val ts = rs.sorted
      ts.head.originBalance
    } else {
      val last = getLast(account, incomeClazz, billClazz, userAttr, beginAt)
      last.map(_.balance).getOrElse(account.initBalance)
    }
  }
}
