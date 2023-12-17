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

import net.yushanginfo.hams.wallet.model.{Bill, Wallet}
import net.yushanginfo.hams.wallet.service.BillService
import org.beangle.data.dao.{EntityDao, OqlBuilder}

import java.time.YearMonth
import java.time.format.DateTimeFormatter

class BillServiceImpl extends BillService {

  var entityDao: EntityDao = _

  def getBills(wallet: Wallet, yearMonth: YearMonth, goods: String): Seq[Bill] = {
    val query = OqlBuilder.from(classOf[Bill], "bill")
    query.where("bill.wallet=:w", wallet)
    query.where("to_char(bill.payAt,'yyyyMM')=:payAt", DateTimeFormatter.ofPattern("yyyyMM").format(yearMonth))
    query.where("bill.goods=:goods", goods)
    entityDao.search(query)
  }
}
