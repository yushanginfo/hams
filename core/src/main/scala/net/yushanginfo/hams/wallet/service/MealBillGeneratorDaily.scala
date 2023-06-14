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

package net.yushanginfo.hams.wallet.service

import net.yushanginfo.hams.base.model.Inpatient
import net.yushanginfo.hams.wallet.model.{Bill, Wallet, WalletSetting}
import org.beangle.data.dao.OqlBuilder

import java.time.{Instant, LocalDate}
import scala.collection.mutable

class MealBillGeneratorDaily extends DaoJob {

  def execute(): Unit = {
    val setting = entityDao.getAll(classOf[WalletSetting]).head
    val q = OqlBuilder.from(classOf[Wallet], "w")
    q.where("w.inpatient.endAt is null or :today < w.inpatient.endAt", LocalDate.now)
    q.where("w.balance >= :minBalance", setting.mealPricePerDay)
    q.where("w.impatient.status.name not like '%请假%'")
    val wallets = entityDao.search(q)
    val bills = new mutable.ArrayBuffer[Bill]
    wallets.foreach { w =>
      val bill = new Bill
      bill.wallet = w
      bill.inpatient = w.inpatient
      bill.amount = setting.mealPricePerDay
      bill.payAt = Instant.now
      bill.goods = "伙食费"
      bill.updatedAt = Instant.now
      w.balance = w.balance - bill.amount
      bills.addOne(bill)
    }
    entityDao.saveOrUpdate(bills, wallets)
  }
}
