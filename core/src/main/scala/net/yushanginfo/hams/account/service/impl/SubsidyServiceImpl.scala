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

import net.yushanginfo.hams.account.model.{Subsidy, SubsidyBill, SubsidyIncome}
import net.yushanginfo.hams.account.service.{SubsidyService, TransactionService}
import net.yushanginfo.hams.base.model.{Inpatient, TransactionStat, Yuan}
import org.beangle.data.dao.{EntityDao, OqlBuilder}

import java.time.*

class SubsidyServiceImpl extends SubsidyService {
  var entityDao: EntityDao = _
  var transactionService: TransactionService = _

  override def adjustBalance(subsidy: Subsidy, beginAt: Instant): Yuan = {
    transactionService.adjustBalance(subsidy, classOf[SubsidyIncome], classOf[SubsidyBill], "account", beginAt)
  }

  override def get(inpatient: Inpatient): Option[Subsidy] = {
    val q = OqlBuilder.from(classOf[Subsidy], "s")
    q.where("s.inpatient=:inpatient", inpatient)
    entityDao.search(q).headOption
  }

  override def getOrCreate(inpatient: Inpatient, payAt: Instant): Subsidy = {
    val q = OqlBuilder.from(classOf[Subsidy], "s")
    q.where("s.inpatient=:inpatient", inpatient)
    entityDao.search(q).headOption match
      case None =>
        val w = Subsidy(inpatient)
        w.createdOn = payAt.atZone(ZoneId.systemDefault()).toLocalDate
        entityDao.saveOrUpdate(w)
        w
      case Some(s) => s
  }

  override def getIncome(subsidy: Subsidy, amount: Yuan, payAt: Instant): Option[SubsidyIncome] = {
    val q = OqlBuilder.from(classOf[SubsidyIncome], "si")
    q.where("si.account=:account", subsidy)
    q.where("si.amount=:amount", amount)
    q.where("si.payAt=:payAt", payAt)
    entityDao.search(q).headOption
  }

  override def getBill(subsidy: Subsidy, amount: Yuan, payAt: Instant): Option[SubsidyBill] = {
    val q = OqlBuilder.from(classOf[SubsidyBill], "si")
    q.where("si.account=:account", subsidy)
    q.where("si.amount=:amount", amount)
    q.where("si.payAt=:payAt", payAt)
    entityDao.search(q).headOption
  }

  override def stat(yearMonth: YearMonth): collection.Seq[TransactionStat] = {
    transactionService.stat(yearMonth, classOf[Subsidy], classOf[SubsidyIncome], classOf[SubsidyBill], "account", Map.empty)
  }
}
