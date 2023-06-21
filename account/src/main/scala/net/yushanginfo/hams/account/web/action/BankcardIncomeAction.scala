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

import net.yushanginfo.hams.account.model.{Bankcard, BankcardIncome}
import net.yushanginfo.hams.base.model.{Inpatient, Ward}
import net.yushanginfo.hams.code.model.{BankcardIncomeCategory, IncomeChannel}
import net.yushanginfo.hams.wallet.model.{Income, Wallet, WalletType}
import org.beangle.commons.lang.Strings
import org.beangle.data.dao.OqlBuilder
import org.beangle.web.action.view.View
import org.beangle.webmvc.support.action.{ExportSupport, ImportSupport, RestfulAction}

/** 养老金入账
 */
class BankcardIncomeAction extends RestfulAction[BankcardIncome], ImportSupport[BankcardIncome], ExportSupport[BankcardIncome] {
  override protected def simpleEntityName: String = "income"

  override protected def indexSetting(): Unit = {
    put("wards", entityDao.getAll(classOf[Ward]))
    super.indexSetting()
  }

  override protected def editSetting(entity: BankcardIncome): Unit = {
    put("categories", entityDao.getAll(classOf[BankcardIncomeCategory]))
    super.editSetting(entity)
  }

  override protected def saveAndRedirect(income: BankcardIncome): View = {
    if (!income.persisted && !Strings.isEmpty(income.account.cardNo)) {
      entityDao.findBy(classOf[Bankcard], "cardNo", income.account.cardNo).headOption match {
        case None => return redirect("index", "不正确的银行卡号")
        case Some(i) => income.account = i
      }
    }
    super.saveAndRedirect(income)
  }

}
