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

import net.yushanginfo.hams.account.model.*
import net.yushanginfo.hams.account.service.{AttendFeeService, TransactionService}
import net.yushanginfo.hams.base.model.{TransactionStat, Yuan}
import org.beangle.commons.collection.Collections
import org.beangle.data.dao.{EntityDao, OqlBuilder}

import java.time.{LocalDate, YearMonth, ZoneId, ZoneOffset}
import scala.collection.mutable

class AttendFeeServiceImpl extends AttendFeeService {
  var entityDao: EntityDao = _
  var transactionService: TransactionService = _

  override def stat(yearMonth: YearMonth): collection.Seq[TransactionStat] = {
    transactionService.stat(yearMonth, classOf[AttendFee], classOf[AttendFeeIncome], classOf[AttendFeeBill], "account", Map.empty)
  }

}
