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

import net.yushanginfo.hams.base.model.{Inpatient, Ward}
import net.yushanginfo.hams.wallet.model.{Bill, Wallet, WalletType}
import org.beangle.commons.lang.Strings
import org.beangle.data.dao.OqlBuilder
import org.beangle.web.action.view.View
import org.beangle.webmvc.support.action.{ExportSupport, ImportSupport, RestfulAction}

/**
 * 伙食费支出
 */
class MealBillAction extends RestfulAction[Bill], ImportSupport[Bill], ExportSupport[Bill] {
  override protected def indexSetting(): Unit = {
    put("wards", entityDao.getAll(classOf[Ward]))
    super.indexSetting()
  }

  override protected def saveAndRedirect(income: Bill): View = {
    if (!income.persisted && !Strings.isEmpty(income.wallet.inpatient.code)) {
      entityDao.findBy(classOf[Inpatient], "code", income.wallet.inpatient.code).headOption match {
        case None => return redirect("index", "不正确的住院号")
        case Some(i) =>
          val q = OqlBuilder.from(classOf[Wallet], "wallet")
          q.where("wallet.inpatient=:inpatient and wallet.walletType=:type", i, WalletType.Meal)
          val wallet = entityDao.search(q).headOption match {
            case Some(w) => w
            case None =>
              val w = Wallet(i, WalletType.Meal)
              entityDao.saveOrUpdate(w)
              w
          }
          income.inpatient = i
          income.wallet = wallet
      }
    }
    super.saveAndRedirect(income)
  }

  override protected def getQueryBuilder: OqlBuilder[Bill] = {
    val query = super.getQueryBuilder
    query.where("bill.wallet.walletType=:walletType", WalletType.Meal)
    query
  }
}
