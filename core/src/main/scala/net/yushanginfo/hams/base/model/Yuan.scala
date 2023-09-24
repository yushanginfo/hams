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

import org.beangle.commons.conversion.Converter
import org.beangle.commons.lang.Strings
import org.beangle.commons.lang.annotation.value

import java.time.LocalDateTime

@value
class Yuan(val value: Long) extends Serializable with Ordered[Yuan] {
  override def toString(): String = {
    var res = value.toString
    var sign = ""
    if (res.charAt(0) == '-') {
      sign = "-"
      res = res.substring(1)
    }
    if (res.length < 3) {
      sign + "0." + Strings.leftPad(res, 2, '0')
    } else {
      val len = res.length - 2
      var first = len % 3
      if (first == 0) first = 3
      val buf = new StringBuilder(sign)
      buf.append(res.substring(0, first))
      var s = first
      while (s < len) {
        buf.append(",")
        buf.append(res.substring(s, s + 3))
        s += 3
      }
      buf.append(".").append(res.substring(res.length - 2))
      buf.mkString
    }
  }

  override def compare(o: Yuan): Int =
    if this.value < o.value then -1
    else if this.value > o.value then 1
    else 0

  def +(o: Yuan): Yuan = {
    new Yuan(this.value + o.value)
  }

  def -(o: Yuan): Yuan = {
    new Yuan(this.value - o.value)
  }

}

object Yuan {
  val Zero = new Yuan(0)

  def apply(value: Long): Yuan = {
    new Yuan(value)
  }

  def apply(value: String): Yuan = {
    var v = value

    if Strings.isEmpty(v) then Zero
    else if (v.contains(".")) {
      v = Strings.replace(v, ",", "")
      val decimal = (Strings.substringAfter(v, ".") + "00").substring(0, 2)
      new Yuan(java.lang.Long.parseLong(Strings.substringBefore(v, ".") + decimal))
    } else {
      v = Strings.replace(v, ",", "")
      new Yuan(java.lang.Long.parseLong(v + "00"))
    }
  }

  def main(args: Array[String]): Unit = {
    println(LocalDateTime.parse("2021-06-28T11:04:00"))
    val y = Yuan("4.123")
    println(y.value)
    println(new Yuan(30))
    println(new Yuan(-30))
    println(new Yuan(-130))
    println(new Yuan(0))
    println(new Yuan(-243128))
  }
}

object YuanConverter extends Converter[String, Yuan] {

  override def apply(input: String): Yuan = Yuan(input)
}
