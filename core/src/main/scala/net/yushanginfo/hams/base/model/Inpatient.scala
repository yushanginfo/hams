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

import net.yushanginfo.hams.code.model.*
import org.beangle.commons.collection.Collections
import org.beangle.data.model.LongId
import org.beangle.data.model.pojo.{Coded, Named}

import java.time.{Instant, LocalDate, ZoneId}
import scala.collection.mutable

/**
 * 住院病人
 * 住院号作为唯一键
 */
class Inpatient extends LongId, Coded, Named {

  def this(id: Long) = {
    this()
    this.id = id
  }

  /** 病人ID */
  var patientId: Long = _

  /** 卡号 */
  var card: Option[String] = None

  /** 卡类型 */
  var cardType: Option[CardType] = None

  /** 性别 */
  var gender: Gender = _

  /** 人员信息 */
  var person: Person = _

  /** 联系信息 */
  var contact: Contact = _

  /** 所在病区 */
  var ward: Ward = _

  /** 床位号 */
  var bedNo: String = _

  /** 床位医生 */
  var bedDoctor: Option[String] = None

  /** 门诊诊断 */
  var diseaseType: Option[DiseaseType] = None

  /** 状态 */
  var status: InpatientStatus = _

  /** 联系人列表 */
  var relations: mutable.Buffer[Relation] = Collections.newBuffer[Relation]

  /** 请假日期 */
  var leaveAt: Option[Instant] = None

  /** 预计销假日期 */
  var backAt: Option[Instant] = None

  /** 费用来源 */
  var feeOrigin: FeeOrigin = _

  /** 危重级别 */
  var criticalLevel: Option[CriticalLevel] = None

  /** 登记人 */
  var createdBy: Option[String] = None

  /** 入院时间 */
  var beginAt: Instant = _

  /** 出院时间 */
  var endAt: Option[Instant] = None

  def beginOn: LocalDate = {
    this.beginAt.atZone(ZoneId.systemDefault()).toLocalDate
  }

  def endOn: Option[LocalDate] = {
    this.endAt.map(x => x.atZone(ZoneId.systemDefault()).toLocalDate)
  }

  def description: String = {
    person.phoneticName match
      case None => code + " " + name + " " + ward.name + "(" + bedNo + ")"
      case Some(n) => code + " " + name + "(" + n + ")" + " " + ward.name + "(" + bedNo + ")"
  }
}
