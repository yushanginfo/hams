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

package net.yushanginfo.hams.base.web.helper

import net.yushanginfo.hams.base.model.Department
import net.yushanginfo.hams.code.model.Gender

import java.time.{Instant, LocalDate}

object SyncData {

  class Depart {
    var id: String = _
    var code: String = _
    var name: String = _
    var indexno: String = _
    var parentId: Option[String] = None
    var updatedAt: Instant = _

    def mergeTo(depart: Department): Department = {
      val d = depart
      d.code = this.code
      d.name = this.name
      d.indexno = this.indexno
      d.updatedAt = this.updatedAt
      if (null == depart.beginOn) {
        depart.beginOn = LocalDate.now
      }
      d
    }
  }

  class Staff {
    var id: String = _
    var code: String = _
    var name: String = _
    var gender: Gender = _ //M 表示男 F表示女
    var mobile: Option[String] = None
    var depart: Department = _
    var updatedAt: Instant = _

    def mergeTo(staff: net.yushanginfo.hams.base.model.Staff): net.yushanginfo.hams.base.model.Staff = {
      val s = staff
      s.code = this.code
      s.name = this.name
      s.gender = this.gender
      s.mobile = this.mobile
      s.department = this.depart
      s.updatedAt = this.updatedAt
      if (null == staff.beginOn) {
        staff.beginOn = LocalDate.now
      }
      s
    }
  }

}
