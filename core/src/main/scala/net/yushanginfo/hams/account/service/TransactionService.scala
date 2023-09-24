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

import net.yushanginfo.hams.base.model.{Account, Transaction, TransactionStat, Yuan}

import java.time.{Instant, YearMonth}

trait TransactionService {
  def adjustBalance(account: Account, incomeClazz: Class[_], billClazz: Class[_], userAttr: String, beginAt: Instant): Yuan

  def getLast(account: Account, incomeClazz: Class[_], billClazz: Class[_], userAttr: String, before: Instant): Option[Transaction]

  def find(account: Account, incomeClazz: Class[_], billClazz: Class[_], userAttr: String, beginAt: Instant): collection.Seq[Transaction]

  def stat(yearMonth: YearMonth, accountClazz: Class[_], incomeClazz: Class[_], billClazz: Class[_], userAttr: String,
           params: collection.Map[String, Any]): collection.Seq[TransactionStat]
}
