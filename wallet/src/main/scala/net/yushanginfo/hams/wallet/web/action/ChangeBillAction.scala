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
import net.yushanginfo.hams.wallet.service.WalletService
import org.beangle.commons.lang.Strings
import org.beangle.data.dao.OqlBuilder
import org.beangle.web.action.view.View
import org.beangle.webmvc.support.action.{ExportSupport, ImportSupport, RestfulAction}
import org.beangle.webmvc.support.helper.QueryHelper

/**
 * 伙食费支出
 */
class ChangeBillAction extends RestfulAction[Bill], ImportSupport[Bill], ExportSupport[Bill] {
  var walletService: WalletService = _

  override protected def indexSetting(): Unit = {
    put("wards", entityDao.getAll(classOf[Ward]))
    super.indexSetting()
  }

  override protected def saveAndRedirect(bill: Bill): View = {
    if (!bill.persisted && !Strings.isEmpty(bill.wallet.inpatient.code)) {
      val wallet = walletService.getWallet(bill.wallet.inpatient.code, WalletType.Change)
      wallet match {
        case None => return redirect("index", "没有对应住院号的零用金账户")
        case Some(w) =>
          val nbill = w.newBill(bill.amount, bill.payAt, bill.goods)
          entityDao.saveOrUpdate(wallet, nbill)
          super.saveAndRedirect(nbill)
      }
    } else {
      super.saveAndRedirect(bill)
    }
  }

  override protected def getQueryBuilder: OqlBuilder[Bill] = {
    val query = super.getQueryBuilder
    QueryHelper.dateBetween(query, null, "payAt", "beginAt", "endAt")
    query.where("bill.wallet.walletType=:walletType", WalletType.Change)
    query
  }
}
