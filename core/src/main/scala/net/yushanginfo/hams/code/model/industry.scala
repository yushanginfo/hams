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

class DiseaseType extends CodeBean

class FeeOrigin extends CodeBean

class CardType extends CodeBean

class CriticalLevel extends CodeBean

/**
 * 病人状态
 * 在院、请假、出院、死亡等
 */
class InpatientStatus extends CodeBean

/** 收入渠道 */
class IncomeChannel extends CodeBean {
  def this(id: Int) = {
    this()
    this.id = id
  }
}

object IncomeChannel {
  val FromBank = 2
  val FromPension = 4
  val FromSubsidy = 5
}

object BankcardIncomeCategory {
  val Mics = 99
}

class BankcardIncomeCategory extends CodeBean {
  def this(id: Int) = {
    this()
    this.id = id
  }
}

class LeaveCategory extends CodeBean {

}
