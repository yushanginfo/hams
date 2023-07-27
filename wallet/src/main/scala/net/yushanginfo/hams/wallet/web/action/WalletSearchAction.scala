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

import net.yushanginfo.hams.wallet.model.{Wallet, WalletSetting, WalletType}
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

  def warning(): View = {
    val setting = entityDao.getAll(classOf[WalletSetting]).head
    val walletType = getInt("walletType", 1)
    val q = OqlBuilder.from(classOf[Wallet], "w")
    q.where("w.inpatient.endAt is null")
    if (walletType == 1) {
      q.where("w.walletType=:walletType", WalletType.Meal)
      q.where("w.balance < :minBalance", setting.warningMealBalance)
    } else {
      q.where("w.walletType=:walletType", WalletType.Change)
      q.where("w.balance < :minBalance", setting.warningChangeBalance)
    }
    q.orderBy("w.inpatient.bedNo")
    q.limit(1, 20)
    val wallets = entityDao.search(q)
    put("wallets", wallets)
    put("walletType", walletType)
    forward()
  }

  def search(): View = {
    put("wallets", entityDao.search(getQueryBuilder))
    forward()
  }
}
