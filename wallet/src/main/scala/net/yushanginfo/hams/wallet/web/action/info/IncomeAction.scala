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

package net.yushanginfo.hams.wallet.web.action.info

import net.yushanginfo.hams.base.model.Ward
import net.yushanginfo.hams.wallet.model.{Income, WalletType}
import org.beangle.data.dao.{EntityDao, OqlBuilder}
import org.beangle.web.action.support.ActionSupport
import org.beangle.web.action.view.View
import org.beangle.webmvc.support.action.{EntityAction, ExportSupport}
import org.beangle.webmvc.support.helper.QueryHelper

class IncomeAction extends ActionSupport, EntityAction[Income], ExportSupport[Income] {
  var entityDao: EntityDao = _

  def index(): View = {
    put("wards", entityDao.getAll(classOf[Ward]))
    put("walletTypes", WalletType.values)
    forward()
  }

  override protected def getQueryBuilder: OqlBuilder[Income] = {
    val query = super.getQueryBuilder
    QueryHelper.dateBetween(query, null, "payAt", "beginAt", "endAt")
    query
  }

  def search(): View = {
    put("incomes", entityDao.search(getQueryBuilder))
    forward()
  }
}
