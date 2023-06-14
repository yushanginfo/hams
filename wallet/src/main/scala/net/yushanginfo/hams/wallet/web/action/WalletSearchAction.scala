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

package net.yushanginfo.hams.wallet.web.action

import net.yushanginfo.hams.wallet.model.{Wallet, WalletType}
import org.beangle.data.dao.{EntityDao, OqlBuilder}
import org.beangle.web.action.support.ActionSupport
import org.beangle.web.action.view.View
import org.beangle.webmvc.support.action.{EntityAction, ExportSupport, ImportSupport, RestfulAction}
import org.beangle.webmvc.support.helper.QueryHelper

class WalletSearchAction extends ActionSupport, EntityAction[Wallet], ExportSupport[Wallet] {
  var entityDao: EntityDao = _

  def index(): View = {
    put("walletTypes", WalletType.values)
    put("wards", entityDao.getAll(classOf[Wallet]))
    forward()
  }

  def search(): View = {
    put("wallets", entityDao.search(getQueryBuilder))
    forward()
  }
}
