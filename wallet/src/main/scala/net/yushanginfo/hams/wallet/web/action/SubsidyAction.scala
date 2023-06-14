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
import org.beangle.webmvc.support.action.{ExportSupport, ImportSupport, RestfulAction}

import java.time.{LocalDate, Year, YearMonth}
import scala.collection.mutable

class SubsidyAction extends RestfulAction[Wallet], ImportSupport[Wallet], ExportSupport[Wallet] {

  var walletService: WalletService = _

  override protected def indexSetting(): Unit = {
    put("wards", entityDao.getAll(classOf[Ward]))
    super.indexSetting()
  }

  override protected def saveAndRedirect(wallet: Wallet): View = {
    if (!wallet.persisted && !Strings.isEmpty(wallet.inpatient.code)) {
      entityDao.findBy(classOf[Inpatient], "code", wallet.inpatient.code).headOption match {
        case None => return redirect("index", "不正确的住院号")
        case Some(i) => wallet.inpatient = i
      }
      if (null == wallet.createdOn) {
        wallet.createdOn = YearMonth.of(wallet.inpatient.beginAt.getYear, wallet.inpatient.beginAt.getMonth.getValue)
      }
      wallet.walletType = WalletType.Subsidy
      wallet.balance = wallet.initBalance
    }
    super.saveAndRedirect(wallet)
  }

  override protected def getQueryBuilder: OqlBuilder[Wallet] = {
    val query = super.getQueryBuilder
    query.where("wallet.walletType=:walletType", WalletType.Subsidy)
    query
  }

}
