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

import net.yushanginfo.hams.base.model.YuanConverter
import org.beangle.commons.conversion.impl.DefaultConversion
import org.beangle.data.orm.MappingModule

import java.time.{LocalDate, YearMonth}

object DefaultMapping extends MappingModule {

  def binding(): Unit = {
    DefaultConversion.Instance.addConverter(YuanConverter)
    bind[Wallet] declare { e =>
      index("", true, e.inpatient, e.walletType)
    }
    bind[Bill] declare { e =>
      index("", false, e.wallet)
    }
    bind[Income] declare { e =>
      index("", false, e.wallet)
    }
    bind[Deposit] declare { e =>
      index("", false, e.inpatient)
    }

    bind[LeaveApply]
    bind[WalletSetting]
  }
}
