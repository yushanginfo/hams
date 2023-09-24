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

import net.yushanginfo.hams.base.model.{Inpatient, TransactionStat, Yuan}
import net.yushanginfo.hams.wallet.model.*

import java.time.{Instant, YearMonth}

trait WalletService {

  def getWallet(inpatient: Inpatient, walletType: WalletType): Option[Wallet]

  def getWallet(code: String, walletType: WalletType): Option[Wallet]

  def getOrCreateWallet(inpatient: Inpatient, walletType: WalletType, payAt: Instant): Wallet

  def getIncome(wallet: Wallet, amount: Yuan, payAt: Instant): Option[Income]

  def getBill(wallet: Wallet, amount: Yuan, payAt: Instant): Option[Bill]

  def stat(yearMonth: YearMonth, walletType: WalletType): collection.Seq[TransactionStat]

  def adjustBalance(wallet: Wallet, beginAt: Instant): Yuan

  def generateMealBills(yearMonth: YearMonth): Int

  def generateMealBill(inpatient: Inpatient, yearMonth: YearMonth, setting: WalletSetting): Bill

  def generateDischangeBill(inpatient: Inpatient): Bill
}
