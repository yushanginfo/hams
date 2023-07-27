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

import java.time.Year

class ChangeAction extends RestfulAction[Wallet], ImportSupport[Wallet], ExportSupport[Wallet] {

  var walletService: WalletService = _

  override protected def indexSetting(): Unit = {
    put("wards", entityDao.getAll(classOf[Ward]))
    super.indexSetting()
  }

  def yearReport(): View = {
    val year = getInt("year", Year.now().getValue)
    val inpatientIds = getLongIds("inpatient")
    val inpatients =
      if null != inpatientIds && inpatientIds.nonEmpty then
        entityDao.find(classOf[Inpatient], inpatientIds)
      else
        entityDao.find(classOf[Wallet], getLongIds("wallet")).map(_.inpatient)

    val inpatientLogs = Collections.newMap[Inpatient, Object]
    inpatients foreach { inpatient =>
      val q = OqlBuilder.from(classOf[Wallet], "w")
      q.where("w.walletType=:walletType", WalletType.Change)
      q.where("w.inpatient=:inpatient", inpatient)
      val wallet = entityDao.search(q).head

      val b = OqlBuilder.from(classOf[Bill], "bill")
      b.where("bill.wallet.inpatient=:inpatient", inpatient)
      b.where("bill.wallet.walletType=:walletType", WalletType.Change)
      b.where("year(bill.payAt)=:year", year)
      val bills = entityDao.search(b)

      val i = OqlBuilder.from(classOf[Income], "income")
      i.where("income.wallet.inpatient=:inpatient", inpatient)
      i.where("income.wallet.walletType=:walletType", WalletType.Change)
      i.where("year(income.payAt)=:year", year)
      val incomes = entityDao.search(i)

      val logs = Collections.newBuffer[Object]
      logs.addAll(bills)
      logs.addAll(incomes)
      inpatientLogs.put(inpatient, logs)
    }
    put("inpatientLogs", inpatientLogs)
    put("year", year)
    put("inpatients", inpatients)
    forward()
  }

  override protected def saveAndRedirect(wallet: Wallet): View = {
    if (!wallet.persisted && !Strings.isEmpty(wallet.inpatient.code)) {
      entityDao.findBy(classOf[Inpatient], "code", wallet.inpatient.code).headOption match {
        case None => return redirect("index", "不正确的住院号")
        case Some(i) => wallet.inpatient = i
      }
      wallet.walletType = WalletType.Change
      wallet.balance = wallet.initBalance
    }
    super.saveAndRedirect(wallet)
  }

  override protected def getQueryBuilder: OqlBuilder[Wallet] = {
    val query = super.getQueryBuilder
    query.where("wallet.walletType=:walletType", WalletType.Change)
    query
  }

}
