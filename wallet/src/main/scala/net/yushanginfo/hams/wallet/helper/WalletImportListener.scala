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

package net.yushanginfo.hams.wallet.helper

import net.yushanginfo.hams.base.service.InpatientService
import net.yushanginfo.hams.wallet.model.{Wallet, WalletType}
import org.beangle.data.dao.{EntityDao, OqlBuilder}
import org.beangle.data.transfer.importer.{ImportListener, ImportResult}

class WalletImportListener(walletType: WalletType,
                           inpatientService: InpatientService, entityDao: EntityDao) extends ImportListener {

  override def onItemFinish(tr: ImportResult): Unit = {
    val data = tr.transfer.curData
    val name = data.get("wallet.inpatient.name").orNull.asInstanceOf[String]
    inpatientService.getInpatientByName(name) match
      case None => tr.addFailure("错误的姓名", name)
      case Some(inpatient) =>
        val po = tr.transfer.current.asInstanceOf[Wallet]
        val createdOn = po.createdOn
        if (null == createdOn) {
          tr.addFailure("缺少创建时间", name)
        } else {
          val query = OqlBuilder.from(classOf[Wallet], "d")
          query.where("d.inpatient=:inpatient", inpatient)
          query.where("d.walletType=:walletType", walletType)
          query.where("to_char(d.createdOn,'yyyy-MM-dd') = :date", createdOn.toString)
          val wallets = entityDao.search(query)
          val wallet = wallets.headOption.getOrElse(new Wallet())
          wallet.walletType = walletType
          wallet.inpatient = inpatient
          wallet.createdOn = createdOn
          wallet.initBalance = po.initBalance
          wallet.balance = po.balance
          entityDao.saveOrUpdate(wallet)
        }
  }
}
