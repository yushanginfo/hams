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

package net.yushanginfo.hams.code.model

import net.yushanginfo.hams.code.model.{Gender, InpatientStatus}
import org.beangle.data.model.pojo.Updated
import org.beangle.data.orm.{IdGenerator, MappingModule}

class DefaultMapping extends MappingModule {

  def binding(): Unit = {
    defaultCache("code", "read-write")
    bind[Updated] declare { e =>
      e.updatedAt is default("current")
    }
    bind[CodeBean] declare { e =>
      e.beginOn is default("current")
    }
    bind[Gender]
    bind[Nation]
    bind[Country]
    bind[Relationship]
    bind[MaritalStatus]
    bind[IdType]

    bind[FeeOrigin]
    bind[CriticalLevel]
    bind[CardType]
    bind[InpatientStatus]
    bind[DiseaseType]
    bind[IncomeChannel]

    bind[BankcardIncomeCategory]
    bind[LeaveCategory]
    all.cacheAll()
  }
}
