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

import net.yushanginfo.hams.wallet.model.Deposit
import org.beangle.data.dao.{EntityDao, OqlBuilder}
import org.beangle.web.action.support.ActionSupport
import org.beangle.web.action.view.View
import org.beangle.webmvc.support.action.EntityAction

class DepositAction extends ActionSupport, EntityAction[Deposit] {
  var entityDao: EntityDao = _

  def warning(): View = {
    val q = OqlBuilder.from(classOf[Deposit], "w")
    q.where("w.inpatient.endAt is null")
    q.where("w.amount=0")
    q.orderBy("w.inpatient.bedNo")
    q.limit(1, 10)
    val wallets = entityDao.search(q)
    put("deposits", wallets)
    forward()
  }
}
