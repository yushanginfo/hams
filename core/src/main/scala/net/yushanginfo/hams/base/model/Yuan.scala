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

package net.yushanginfo.hams.base.model

import org.beangle.commons.lang.annotation.value

@value
class Yuan(val value: Long) extends Serializable with Ordered[Yuan] {
  override def toString(): String = {
    String.valueOf(value / 100.0f)
  }

  override def compare(o: Yuan): Int =
    if (this.value < o.value) then -1
    else if (this.value > o.value) then 1
    else 0

}
