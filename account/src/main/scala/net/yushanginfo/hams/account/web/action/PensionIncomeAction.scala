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

package net.yushanginfo.hams.account.web.action

import net.yushanginfo.hams.account.model.PensionIncome
import net.yushanginfo.hams.account.service.PensionService
import net.yushanginfo.hams.base.model.{Inpatient, Ward}
import org.beangle.commons.lang.Strings
import org.beangle.data.dao.OqlBuilder
import org.beangle.web.action.view.View
import org.beangle.webmvc.support.action.{ExportSupport, ImportSupport, RestfulAction}
import org.beangle.webmvc.support.helper.QueryHelper

/** 养老金入账
 */
class PensionIncomeAction extends RestfulAction[PensionIncome], ImportSupport[PensionIncome], ExportSupport[PensionIncome] {
  override protected def simpleEntityName: String = "income"

  var pensionService: PensionService = _

  override protected def indexSetting(): Unit = {
    put("wards", entityDao.getAll(classOf[Ward]))
    super.indexSetting()
  }

  override protected def getQueryBuilder: OqlBuilder[PensionIncome] = {
    val query = super.getQueryBuilder
    QueryHelper.dateBetween(query, null, "payAt", "beginAt", "endAt")
    query
  }

  override protected def saveAndRedirect(income: PensionIncome): View = {
    val payAt = getInstant("payAt").get
    val minPayAt = income.updatePayAt(payAt)

    if (!income.persisted && !Strings.isEmpty(income.account.inpatient.code)) {
      entityDao.findBy(classOf[Inpatient], "code", income.account.inpatient.code).headOption match {
        case None => redirect("index", "不正确的住院号")
        case Some(i) =>
          val account = pensionService.getOrCreate(i, payAt)
          val newI = account.newIncome(income.amount, income.payAt, income.channel)
          entityDao.saveOrUpdate(account, newI)
          pensionService.adjustBalance(account, minPayAt)
          redirect("search", "info.save.success")
      }
    } else {
      entityDao.saveOrUpdate(income)
      pensionService.adjustBalance(income.account, minPayAt)
      super.saveAndRedirect(income)
    }
  }

}
