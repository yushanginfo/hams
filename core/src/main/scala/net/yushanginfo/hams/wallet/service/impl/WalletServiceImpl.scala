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

import net.yushanginfo.hams.base.model.Yuan
import net.yushanginfo.hams.wallet.model.*
import net.yushanginfo.hams.wallet.service.WalletService
import org.beangle.commons.collection.Collections
import org.beangle.data.dao.{EntityDao, OqlBuilder}

import java.time.{LocalDate, YearMonth, ZoneId, ZoneOffset}
import scala.collection.mutable

class WalletServiceImpl extends WalletService {
  var entityDao: EntityDao = _

  override def stat(yearMonth: YearMonth, walletType: WalletType, forceStat: Boolean): Seq[WalletStat] = {
    val q = OqlBuilder.from(classOf[WalletStat], "ws")
    q.where("ws.yearMonth=:year", yearMonth)
    q.where("ws.wallet.walletType=:walletType", walletType)
    val existedStats = entityDao.search(q)
    if (existedStats.isEmpty || forceStat) {
      val lastMonth = yearMonth.minusMonths(1)
      val beginAt = yearMonth.atDay(1).atTime(0, 0, 0)
      val endAt = yearMonth.atEndOfMonth().atTime(23, 59, 59)

      val beginAtZ = beginAt.atZone(ZoneId.systemDefault).toInstant
      val endAtZ = endAt.atZone(ZoneId.systemDefault).toInstant

      val lq = OqlBuilder.from(classOf[WalletStat], "ws")
      lq.where("ws.wallet.walletType=:walletType", walletType)
      lq.where("ws.yearMonth=:last", lastMonth)
      val lastStats = entityDao.search(lq).map(x => (x.wallet, x)).toMap

      //find all meal wallet for active inpatient
      val q = OqlBuilder.from(classOf[Wallet], "w")
      q.where("w.inpatient.beginAt <= :endAt", endAt)
      q.where("w.inpatient.endAt is null or :beginAt >= w.inpatient.endAt", beginAt)
      q.where("w.walletType=:walletType", walletType)
      val wallets = entityDao.search(q)

      val walletStats = new mutable.ArrayBuffer[WalletStat]

      val bq = OqlBuilder.from(classOf[Bill], "b")
      bq.where("b.wallet.walletType=:walletType", walletType)
      bq.where("b.payAt between :beginAt and :endAt", beginAtZ, endAtZ)
      val bills = entityDao.search(bq)

      val iq = OqlBuilder.from(classOf[Income], "i")
      iq.where("i.wallet.walletType=:walletType", walletType)
      iq.where("i.updatedAt between :beginAt and :endAt", beginAtZ, endAtZ)
      val incomes = entityDao.search(iq)

      val billStats = bills.groupBy(_.inpatient)
      val incomeStats = incomes.groupBy(_.inpatient)

      wallets foreach { w =>
        val walletStat = existedStats.find(x => x.wallet == w && x.yearMonth == yearMonth).getOrElse(new WalletStat)
        walletStats.addOne(walletStat)
        walletStat.wallet = w
        walletStat.yearMonth = yearMonth

        val incomes = incomeStats.get(w.inpatient) match
          case None => Yuan(0)
          case Some(bs) => Yuan(bs.map(_.amount.value).sum)

        val expenses = billStats.get(w.inpatient) match
          case None => Yuan(0)
          case Some(bs) => Yuan(bs.map(_.amount.value).sum)

        walletStat.startBalance = lastStats.get(w) match
          case None => w.initBalance
          case Some(ws) => ws.endBalance

        walletStat.update(incomes, expenses)
      }
      entityDao.saveOrUpdate(walletStats)
      walletStats.toSeq
    } else {
      existedStats
    }
  }
}
