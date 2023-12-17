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

package net.yushanginfo.landray.ws

import java.time.Instant

object SyncData {

  class Depart {
    var id: String = _
    var code: String = _
    var name: String = _
    var indexno: String = _
    var parentId: Option[String] = None
    var updatedAt: Instant = _

    override def toString: String = {
      toJson()
    }

    def toJson(): String = {
      parentId match
        case Some(pid) =>
          s"""{"id":"${id}","code":"${code}","name":"${name}","indexno":"${indexno}","parentId":"${pid}","updatedAt":"${updatedAt}"}"""
        case None =>
          s"""{"id":"${id}","code":"${code}","name":"${name}","indexno":"${indexno}","updatedAt":"${updatedAt}"}"""
    }
  }

  class Staff {
    var id: String = _
    var code: String = _
    var name: String = _
    var genderCode: String = _ //M 表示男 F表示女
    var mobile: Option[String] = None
    var departCode: String = _
    var updatedAt: Instant = _

    def toJson(): String = {
      s"""{"id":"${id}","code":"${code}","name":"${name}","genderCode":"${genderCode}","departCode":"${departCode}","mobile":"${mobile.getOrElse("")}","updatedAt":"${updatedAt}"}"""
    }

    override def toString: String = {
      toJson()
    }
  }

}
