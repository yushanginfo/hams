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

import org.beangle.cdi.bind.BindModule

class DefaultModule extends BindModule {

  override def binding(): Unit = {
    bind(classOf[DepositAction])
    bind(classOf[DepositSearchAction])

    bind(classOf[ChangeAction])
    bind(classOf[ChangeBillAction])
    bind(classOf[ChangeIncomeAction])
    bind(classOf[ChangeStatAction])

    bind(classOf[MealAction])
    bind(classOf[MealBillAction])
    bind(classOf[MealIncomeAction])
    bind(classOf[MealSettingAction])
    bind(classOf[MealStatAction])

    bind(classOf[WalletSearchAction])
    bind(classOf[BillSearchAction])
    bind(classOf[IncomeSearchAction])
  }
}
