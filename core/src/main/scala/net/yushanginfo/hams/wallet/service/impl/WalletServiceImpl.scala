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

import net.yushanginfo.hams.account.service.TransactionService
import net.yushanginfo.hams.base.model.{Inpatient, TransactionStat, Yuan}
import net.yushanginfo.hams.wallet.model.*
import net.yushanginfo.hams.wallet.service.WalletService
import org.beangle.data.dao.{EntityDao, OqlBuilder}

import java.time.temporal.ChronoUnit
import java.time.{Instant, YearMonth, ZoneId}

class WalletServiceImpl extends WalletService {
  var entityDao: EntityDao = _
  var transactionService: TransactionService = _

  override def getWallet(code: String, walletType: WalletType): Option[Wallet] = {
    val q = OqlBuilder.from(classOf[Wallet], "wallet")
    q.where("wallet.inpatient.code=:inpatient and wallet.walletType=:type", code, walletType)
    entityDao.search(q).headOption
  }

  override def getWallet(inpatient: Inpatient, walletType: WalletType): Option[Wallet] = {
    val q = OqlBuilder.from(classOf[Wallet], "wallet")
    q.where("wallet.inpatient=:inpatient and wallet.walletType=:type", inpatient, walletType)
    entityDao.search(q).headOption
  }

  override def getOrCreateWallet(inpatient: Inpatient, walletType: WalletType, payAt: Instant): Wallet = {
    val q = OqlBuilder.from(classOf[Wallet], "wallet")
    q.where("wallet.inpatient=:inpatient and wallet.walletType=:type", inpatient, walletType)
    entityDao.search(q).headOption match
      case None =>
        val w = Wallet(inpatient, walletType)
        w.createdOn = payAt.atZone(ZoneId.systemDefault).toLocalDate
        entityDao.saveOrUpdate(w)
        w
      case Some(w) => w
  }

  override def getIncome(wallet: Wallet, amount: Yuan, payAt: Instant): Option[Income] = {
    val q = OqlBuilder.from(classOf[Income], "si")
    q.where("si.wallet=:wallet", wallet)
    q.where("si.amount=:amount", amount)
    q.where("si.payAt=:payAt", payAt)
    entityDao.search(q).headOption
  }

  override def getBill(wallet: Wallet, amount: Yuan, payAt: Instant): Option[Bill] = {
    val q = OqlBuilder.from(classOf[Bill], "si")
    q.where("si.wallet=:wallet", wallet)
    q.where("si.amount=:amount", amount)
    q.where("si.payAt=:payAt", payAt)
    entityDao.search(q).headOption
  }

  override def stat(yearMonth: YearMonth, walletType: WalletType): collection.Seq[TransactionStat] = {
    transactionService.stat(yearMonth, classOf[Wallet], classOf[Income], classOf[Bill],
      "wallet", Map("walletType" -> walletType))
  }

  override def adjustBalance(wallet: Wallet, beginAt: Instant): Yuan = {
    transactionService.adjustBalance(wallet, classOf[Income], classOf[Bill], "wallet", beginAt)
  }

  override def generateMealBills(yearMonth: YearMonth): Int = {
    val setting = entityDao.getAll(classOf[WalletSetting]).head

    val q = OqlBuilder.from(classOf[Inpatient], "i")
    q.where("i.endAt is null or :today <= to_char(i.endAt,'yyyy-MM-dd')", yearMonth.atDay(1).toString)
    q.where("i.status.name not like '%请假%'")
    val inpatients = entityDao.search(q)
    var billCount = 0
    inpatients foreach { i =>
      val b = generateMealBill(i, yearMonth, setting)
      if (null != b) billCount += 1
    }
    billCount
  }

  override def generateDischangeBill(inpatient: Inpatient): Bill = {
    require(inpatient.endOn.nonEmpty, "病人应该办理出院")
    val setting = entityDao.getAll(classOf[WalletSetting]).head
    generateMealBill(inpatient, YearMonth.from(inpatient.endOn.get), setting)
  }

  override def generateMealBill(inpatient: Inpatient, yearMonth: YearMonth, setting: WalletSetting): Bill = {
    val existWallet = entityDao.findBy(classOf[Wallet], "inpatient" -> inpatient,
      "walletType" -> WalletType.Meal).headOption.getOrElse(Wallet(inpatient, WalletType.Meal))

    var startDay = yearMonth.atDay(1)
    if (inpatient.beginOn.isAfter(startDay)) startDay = inpatient.beginOn
    var endDay = yearMonth.atEndOfMonth()
    inpatient.endOn foreach { e => if (e.isBefore(endDay)) endDay = e }

    if (endDay.isBefore(startDay)) return null

    if (YearMonth.from(startDay) != YearMonth.from(endDay)) return null

    val days = Math.abs(ChronoUnit.DAYS.between(startDay, endDay).toInt + 1) //首尾都算
    val mealDays = if (days <= 20) days else 30 - (yearMonth.lengthOfMonth() - days)
    val cost = new Yuan(0 - setting.mealPricePerDay.value * mealDays)

    val bq = OqlBuilder.from(classOf[Bill], "b")
    bq.where("b.wallet.walletType=:walletType", WalletType.Meal)
    bq.where("b.wallet.inpatient=:inpatient", inpatient)
    bq.where("to_char(b.payAt,'yyyy-MM-dd')=:date", endDay.toString)
    val bill = entityDao.search(bq).headOption match
      case Some(b) => b
      case None =>
        val b = new Bill()
        b.wallet = existWallet
        b

    bill.goods = if (mealDays != 30) yearMonth.toString + s"伙食费(${mealDays}天)" else yearMonth.toString + "伙食费"
    bill.amount = cost
    bill.payAt = endDay.atTime(0, 0).atZone(ZoneId.systemDefault()).toInstant
    bill.updatedAt = Instant.now
    bill.wallet.balance = bill.wallet.balance + bill.amount
    bill.balance = bill.wallet.balance
    entityDao.saveOrUpdate(bill.wallet, bill)
    adjustBalance(bill.wallet, bill.payAt)
    bill
  }

}
