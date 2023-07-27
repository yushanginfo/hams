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

package net.yushanginfo.hams.base.service.impl

import net.yushanginfo.hams.base.model.Inpatient
import net.yushanginfo.hams.base.service.InpatientService
import org.beangle.data.dao.{EntityDao, OqlBuilder}

class InpatientServiceImpl extends InpatientService {

  var entityDao: EntityDao = _

  override def getInpatient(code: String): Option[Inpatient] = {
    val query = OqlBuilder.from(classOf[Inpatient], "i")
    query.where("i.code=:code", code)
    val inpatients = entityDao.search(query)
    if (inpatients.size > 1) {
      inpatients.sortBy(_.beginAt).lastOption
    } else {
      inpatients.headOption
    }
  }
}
