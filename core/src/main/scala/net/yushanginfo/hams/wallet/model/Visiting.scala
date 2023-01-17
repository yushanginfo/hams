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

import net.yushanginfo.hams.base.model.Inpatient
import org.beangle.data.model.LongId

import java.util.Currency

/** 会客
 * */
class Visiting extends LongId {

  var inpatient: Inpatient = _

}
object Visiting{
  def main(args: Array[String]): Unit = {
    val m = Currency.getAvailableCurrencies
    val i = m.iterator()
    while(i.hasNext){
      println(i.next())
    }
  }
}
