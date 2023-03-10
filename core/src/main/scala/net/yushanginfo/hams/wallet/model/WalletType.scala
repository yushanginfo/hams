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

package net.yushanginfo.hams.wallet.model

enum WalletType(val id: Int, val name: String) {
  case Meal extends WalletType(1, "伙食费")
  case Change extends WalletType(2, "零用金")
  case Deposit extends WalletType(3, "住院押金")

  case Subsidy extends WalletType(4, "养护补贴")
  case Pension extends WalletType(5, "养老金")
}
