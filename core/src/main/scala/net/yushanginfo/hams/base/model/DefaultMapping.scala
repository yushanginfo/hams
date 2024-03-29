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

import net.yushanginfo.hams.code.model.{Gender, InpatientStatus}
import org.beangle.data.orm.{IdGenerator, MappingModule}

class DefaultMapping extends MappingModule {

  def binding(): Unit = {
    defaultCache("base", "read-write")

    bind[Department] declare { e =>
      e.indexno is length(20)
      e.name is length(100)
      e.children is depends("parent")
      index("", true, e.indexno)
    }

    bind[Inpatient] declare { e =>
      e.relations is depends("inpatient")
    }

    bind[Relation]
    bind[Staff]
    bind[Ward]
    bind[Person]
    bind[Contact]

    all.except(classOf[Inpatient], classOf[Relation],classOf[Staff]).cacheAll()
  }
}
