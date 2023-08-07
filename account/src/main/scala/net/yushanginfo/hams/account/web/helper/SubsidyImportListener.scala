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

package net.yushanginfo.hams.account.web.helper

import net.yushanginfo.hams.account.model.Subsidy
import net.yushanginfo.hams.base.service.InpatientService
import org.beangle.data.dao.{EntityDao, OqlBuilder}
import org.beangle.data.transfer.importer.{ImportListener, ImportResult}

class SubsidyImportListener(inpatientService: InpatientService, entityDao: EntityDao) extends ImportListener {

  override def onItemFinish(tr: ImportResult): Unit = {
    val data = tr.transfer.curData
    val code = data.get("subsidy.inpatient.name").orNull.asInstanceOf[String]
    inpatientService.getInpatientByName(code) match
      case None => tr.addFailure("错误的姓名", code)
      case Some(inpatient) =>
        val po = tr.transfer.current.asInstanceOf[Subsidy]
        val createdOn = po.createdOn
        if (null == createdOn) {
          tr.addFailure("缺少支付时间", code)
        } else {
          val query = OqlBuilder.from(classOf[Subsidy], "d")
          query.where("d.inpatient=:inpatient", inpatient)
          query.where("to_char(d.createdOn,'yyyy-MM-dd') = :date", createdOn.toString)
          val subsidies = entityDao.search(query)
          val subsidy = subsidies.headOption.getOrElse(new Subsidy())
          subsidy.inpatient = inpatient
          subsidy.createdOn = createdOn
          subsidy.initBalance = po.initBalance
          subsidy.balance = po.balance
          entityDao.saveOrUpdate(subsidy)
        }
  }
}
