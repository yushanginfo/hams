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

package net.yushanginfo.hams.account.model

import net.yushanginfo.hams.account.model.{Bankcard, BankcardBill, BankcardIncome}
import net.yushanginfo.hams.base.model.YuanConverter
import org.beangle.commons.conversion.impl.DefaultConversion
import org.beangle.data.orm.{IdGenerator, MappingModule}

class DefaultMapping extends MappingModule {

  def binding(): Unit = {
    bind[Bankcard] declare { e =>
      e.stats is depends("account")
    }
    bind[BankcardBill]
    bind[BankcardIncome]
    bind[BankcardStat]

    bind[Pension] declare { e =>
      e.stats is depends("account")
    }
    bind[PensionBill]
    bind[PensionIncome]
    bind[PensionStat]

    bind[Subsidy] declare { e =>
      e.stats is depends("account")
    }
    bind[SubsidyBill]
    bind[SubsidyStat]
    bind[SubsidyIncome]
  }
}
