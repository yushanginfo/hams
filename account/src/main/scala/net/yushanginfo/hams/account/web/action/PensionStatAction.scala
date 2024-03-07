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

import net.yushanginfo.hams.account.model.Pension
import net.yushanginfo.hams.account.service.PensionService
import net.yushanginfo.hams.base.model.Ward
import net.yushanginfo.hams.web.helper.StatHelper
import org.beangle.data.dao.EntityDao
import org.beangle.web.action.support.ActionSupport
import org.beangle.web.action.view.View
import org.beangle.webmvc.support.action.EntityAction

import java.time.{Year, YearMonth}
import scala.collection.mutable

class PensionStatAction extends ActionSupport, EntityAction[Pension] {
  var entityDao: EntityDao = _
  var pensionService: PensionService = _

  /**
   * 统计首页
   *
   * @return
   */
  def index(): View = {
    val thisYear = Year.now
    var yearMonth = YearMonth.now()
    val yearMonths = new mutable.ArrayBuffer[YearMonth]
    (1 to 12) foreach { i =>
      yearMonths.addOne(yearMonth)
      yearMonth = yearMonth.minusMonths(1)
    }
    put("yearMonths", yearMonths)
    forward()
  }

  def stat(): View = {
    val ym = YearMonth.parse(get("yearMonth", ""))
    val stats = pensionService.stat(ym)
    StatHelper.putStats(ym, stats)
    forward()
  }

  /**
   * 月度汇总表
   *
   * @return
   */
  def inpatient(): View = {
    val ym = YearMonth.parse(get("yearMonth", ""))
    val ward = entityDao.get(classOf[Ward], getIntId("ward"))
    val wards = entityDao.getAll(classOf[Ward])
    val stats = pensionService.stat(ym)
    put("stats", stats)
    put("yearMonth", ym)
    put("ward", ward)
    forward()
  }
}
