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

import org.beangle.commons.logging.Logging

import java.time.{LocalDate, Year, YearMonth}

class MealBillGeneratorDaily extends DaoJob, Logging {

  var walletService: WalletService = _

  def execute(): Unit = {
    if (LocalDate.now == YearMonth.now().atDay(1)) {
      val billCount = walletService.generateMealBills(YearMonth.now.minusMonths(1))
      logger.info(s"每日伙食费扣费成功,${billCount}人")
    }
  }
}
