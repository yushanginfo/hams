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

package net.yushanginfo.hams.wallet.helper

import net.yushanginfo.hams.base.service.InpatientService
import net.yushanginfo.hams.wallet.model.Deposit
import org.beangle.data.dao.{EntityDao, OqlBuilder}
import org.beangle.data.transfer.importer.{ImportListener, ImportResult}

import java.time.{Instant, LocalDate, ZoneId}

class DepositImportListener(inpatientService: InpatientService, entityDao: EntityDao) extends ImportListener {

  override def onItemFinish(tr: ImportResult): Unit = {
    val data = tr.transfer.curData
    val code = data.get("deposit.inpatient.code").orNull.asInstanceOf[String]
    inpatientService.getInpatient(code) match
      case None => tr.addFailure("错误的住院号", code)
      case Some(inpatient) =>
        val po = tr.transfer.current.asInstanceOf[Deposit]
        val payAt = po.payAt
        if (null == payAt) {
          tr.addFailure("缺少支付时间", code)
        } else {
          val query = OqlBuilder.from(classOf[Deposit], "d")
          query.where("d.inpatient=:inpatient", inpatient)
          query.where("to_char(d.payAt,'yyyy-MM-dd') = :date", payAt.atZone(ZoneId.systemDefault()).toLocalDate.toString)
          val deposits = entityDao.search(query)
          val deposit = deposits.headOption.getOrElse(new Deposit())
          deposit.inpatient = inpatient
          deposit.payAt = payAt
          deposit.amount = po.amount
          deposit.updatedAt = Instant.now
          entityDao.saveOrUpdate(deposit)
        }
  }
}
