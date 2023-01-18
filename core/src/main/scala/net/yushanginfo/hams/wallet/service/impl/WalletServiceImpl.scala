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

import net.yushanginfo.hams.wallet.model.*
import net.yushanginfo.hams.wallet.service.WalletService
import org.beangle.commons.collection.Collections
import org.beangle.data.dao.{EntityDao, OqlBuilder}

import java.time.YearMonth

class WalletServiceImpl extends WalletService {
  var entityDao: EntityDao = _

  override def stat(yearMonth: YearMonth, walletType: WalletType): Iterable[WalletStat] = {
    val first = yearMonth.atDay(1)
    val last = yearMonth.atEndOfMonth()

    val iQuery = OqlBuilder.from[Array[Object]](classOf[Income].getName, "income")
    iQuery.where("income.inpatient.beginOn <=:last and (income.inpatient.endOn is null or income.inpatient.endOn >= :first)", last, first)
    iQuery.groupBy("income.wallet.id").select("income.wallet.id,sum(income.amount)")
    iQuery.where("income.wallet.walletType=:walletType", walletType)
    val incomes = entityDao.search(iQuery).map(x => x(0).asInstanceOf[Number].longValue -> x(1).asInstanceOf[Number].longValue).toMap
    val bQuery = OqlBuilder.from[Array[Object]](classOf[Bill].getName, "bill")
    bQuery.where("bill.inpatient.beginOn <=:last and (bill.inpatient.endOn is null or bill.inpatient.endOn >= :first)", last, first)
    bQuery.groupBy("bill.wallet.id").select("bill.wallet.id,sum(bill.amount)")
    bQuery.where("bill.wallet.walletType=:walletType", walletType)
    val bills = entityDao.search(bQuery).map(x => x(0).asInstanceOf[Number].longValue -> x(1).asInstanceOf[Number].longValue).toMap

    val walletIds = incomes.keySet ++ bills.keySet
    val wallets = walletIds.map(x => entityDao.get(classOf[Wallet], x))

    val stats = Collections.newBuffer[WalletStat]
    wallets foreach { wallet =>
      wallet.addStat(yearMonth, Yuan(incomes.getOrElse(wallet.id, 0L)), Yuan(bills.getOrElse(wallet.id, 0L))) foreach { w =>
        stats.addOne(w)
      }
    }
    entityDao.saveOrUpdate(stats)
    stats
  }
}
