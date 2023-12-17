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

import net.yushanginfo.hams.account.model.{Pension, PensionBill, PensionIncome}
import net.yushanginfo.hams.account.service.{PensionService, TransactionService}
import net.yushanginfo.hams.base.model.{Inpatient, TransactionStat, Yuan}
import org.beangle.data.dao.{EntityDao, OqlBuilder}

import java.time.{Instant, YearMonth, ZoneId}

class PensionServiceImpl extends PensionService {
  var entityDao: EntityDao = _

  var transactionService: TransactionService = _

  override def adjustBalance(pension: Pension, beginAt: Instant): Yuan = {
    transactionService.adjustBalance(pension, classOf[PensionIncome], classOf[PensionBill], "account", beginAt)
  }

  override def getOrCreate(inpatient: Inpatient, payAt: Instant): Pension = {
    val q = OqlBuilder.from(classOf[Pension], "s")
    q.where("s.inpatient=:inpatient", inpatient)
    entityDao.search(q).headOption match
      case None =>
        val w = Pension(inpatient)
        w.createdOn = payAt.atZone(ZoneId.systemDefault()).toLocalDate
        entityDao.saveOrUpdate(w)
        w
      case Some(s) => s
  }

  override def stat(yearMonth: YearMonth): collection.Seq[TransactionStat] = {
    transactionService.stat(yearMonth, classOf[Pension], classOf[PensionIncome], classOf[PensionBill], "account", Map.empty)
  }
}
