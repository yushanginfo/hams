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

package net.yushanginfo.hams.account.model

import net.yushanginfo.hams.base.model.{Account, Transaction}
import net.yushanginfo.hams.wallet.model.WalletType
import org.beangle.data.model.LongId
import org.beangle.data.model.pojo.Updated

/** 银行卡消费账单
 */
class BankcardBill extends LongId, Updated, Transaction {

  var account: Bankcard = _

  /** 资金去向 */
  var expenses: String = _

  /** 转入钱包 */
  var toWallet: Option[WalletType] = None

  override def user: Account = account
}
