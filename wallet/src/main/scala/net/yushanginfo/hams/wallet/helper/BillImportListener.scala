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

import net.yushanginfo.hams.base.model.Yuan
import net.yushanginfo.hams.base.service.InpatientService
import net.yushanginfo.hams.wallet.model.{Bill, WalletType}
import net.yushanginfo.hams.wallet.service.WalletService
import org.beangle.data.dao.EntityDao
import org.beangle.data.transfer.importer.{ImportListener, ImportResult}

class BillImportListener(walletType: WalletType, inpatientService: InpatientService,
                         walletService: WalletService, entityDao: EntityDao) extends ImportListener {

  override def onItemFinish(tr: ImportResult): Unit = {
    val data = tr.transfer.curData
    val name = data.get("bill.wallet.inpatient.name").orNull.asInstanceOf[String]
    inpatientService.getInpatientByName(name) match
      case None => tr.addFailure("错误的姓名", name)
      case Some(inpatient) =>
        val po = tr.transfer.current.asInstanceOf[Bill]
        val payAt = po.payAt
        if (null == payAt) {
          tr.addFailure("缺少支出时间", name)
        } else {
          if (po.amount.value > 0) {
            po.amount = Yuan.Zero - po.amount
          }
          walletService.getWallet(inpatient, walletType) match
            case None => tr.addFailure("缺少账户余额信息", name)
            case Some(wallet) =>
              val bill = walletService.getBill(wallet, po.amount, payAt) match
                case None => wallet.newBill(po.amount, po.payAt, po.goods)
                case Some(i) => i
              bill.goods = po.goods
              entityDao.saveOrUpdate(wallet, bill)
        }
  }
}
