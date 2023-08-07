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

package net.yushanginfo.hams.account.service

import net.yushanginfo.hams.account.model.{Subsidy, SubsidyBill, SubsidyIncome, SubsidyStat}
import net.yushanginfo.hams.base.model.{Inpatient, Yuan}

import java.time.{Instant, YearMonth}

trait SubsidyService {

  def adjustBalance(subsidy: Subsidy, beginAt: Instant): Yuan

  def get(inpatient: Inpatient): Option[Subsidy]

  def getOrCreate(inpatient: Inpatient): Subsidy

  def getBill(subsidy: Subsidy, amount: Yuan, payAt: Instant): Option[SubsidyBill]

  def getIncome(subsidy: Subsidy, amount: Yuan, payAt: Instant): Option[SubsidyIncome]

  def stat(yearMonth: YearMonth, force: Boolean): Seq[SubsidyStat]
}
