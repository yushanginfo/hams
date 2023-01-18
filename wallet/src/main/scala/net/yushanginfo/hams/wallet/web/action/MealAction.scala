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
import net.yushanginfo.hams.wallet.model.*
import net.yushanginfo.hams.wallet.service.WalletService
import org.beangle.commons.collection.Collections
import org.beangle.commons.lang.Strings
import org.beangle.data.dao.OqlBuilder
import org.beangle.web.action.view.View
import org.beangle.webmvc.support.action.RestfulAction

import java.time.{LocalDate, Year, YearMonth}

class MealAction extends RestfulAction[Wallet] {

  var walletService: WalletService = _

  override protected def saveAndRedirect(wallet: Wallet): View = {
    if (!wallet.persisted && !Strings.isEmpty(wallet.inpatient.code)) {
      entityDao.findBy(classOf[Inpatient], "code", wallet.inpatient.code).headOption match {
        case None => return redirect("index", "不正确的住院号")
        case Some(i) => wallet.inpatient = i
      }
      if (null == wallet.createdOn) {
        wallet.createdOn = YearMonth.of(wallet.inpatient.beginOn.getYear, wallet.inpatient.beginOn.getMonth.getValue)
      }
      wallet.walletType = WalletType.Meal
      wallet.balance = wallet.initBalance
    }
    super.saveAndRedirect(wallet)
  }

  override protected def getQueryBuilder: OqlBuilder[Wallet] = {
    val query = super.getQueryBuilder
    query.where("wallet.walletType=:walletType", WalletType.Meal)
    query
  }

  /**
   * 统计首页
   *
   * @return
   */
  def stat(): View = {
    val thisYear = Year.now
    val years = List(thisYear.getValue, thisYear.minusYears(1).getValue, thisYear.minusYears(2).getValue)
    put("year", get("year", thisYear.getValue.toString))
    put("months", 1.to(12))
    put("years", years)
    forward()
  }

  /**
   * 月度统计
   *
   * @return
   */
  def statMonth(): View = {
    val year = getInt("year", 0)
    val month = getInt("month", 1)
    val ym = YearMonth.of(year, month)

    val wards = entityDao.getAll(classOf[Ward])
    put("wards", wards)

    var stats: Iterable[WalletStat] = entityDao.findBy(classOf[WalletStat], "yearMonth", ym)
    if (stats.isEmpty) {
      stats = walletService.stat(ym, WalletType.Meal)
    }
    val wardStats = stats.groupBy(w => w.wallet.inpatient.ward)
    put("wardStats", wardStats)
    forward()
  }

}
