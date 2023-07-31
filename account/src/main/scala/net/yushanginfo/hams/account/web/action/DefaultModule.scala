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

import org.beangle.cdi.bind.BindModule

class DefaultModule extends BindModule {

  override def binding(): Unit = {

    bind(classOf[PensionAction])
    bind(classOf[PensionBillAction])
    bind(classOf[PensionIncomeAction])
    bind(classOf[PensionStatAction])

    bind(classOf[SubsidyAction])
    bind(classOf[SubsidyBillAction])
    bind(classOf[SubsidyIncomeAction])
    bind(classOf[SubsidyStatAction])

    bind(classOf[BankcardAction])
    bind(classOf[BankcardBillAction])
    bind(classOf[BankcardIncomeAction])
    bind(classOf[BankcardStatAction])

    bind(classOf[AttendFeeAction])
    bind(classOf[AttendFeeBillAction])
    bind(classOf[AttendFeeIncomeAction])
    bind(classOf[AttendFeeStatAction])
  }
}
