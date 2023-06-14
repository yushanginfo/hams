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

import net.yushanginfo.hams.base.model.{Contact, Inpatient, Person, Relation}
import org.beangle.commons.collection.Collections
import org.beangle.data.dao.{EntityDao, OqlBuilder}
import org.beangle.data.transfer.importer.{ImportListener, ImportResult, MultiEntityImporter}

import java.time.{LocalDate, LocalDateTime}

class InpatientImporterListener(entityDao: EntityDao) extends ImportListener {

  override def onItemStart(tr: ImportResult): Unit = {
    val data = tr.transfer.curData
    val code = data.get("inpatient.code").orNull.asInstanceOf[String]
    val beginAt = data.get("inpatient.beginAt").orNull.asInstanceOf[LocalDateTime]
    val endAt = data.get("inpatient.endAt").orNull.asInstanceOf[LocalDateTime]

    val query = OqlBuilder.from(classOf[Inpatient], "i")
    query.where("i.code=:code", code)
    if (endAt == null) {
      query.where("i.endAt is null")
    } else {
      query.where("i.beginAt=:beginAt", beginAt)
    }
    val inpatient = entityDao.search(query).headOption
    val newMap = Collections.newMap[String, AnyRef]
    inpatient foreach { i =>
      newMap.put("inpatient", i)
      newMap.put("person", if i.person == null then new Person else i.person)
      newMap.put("contact", if i.contact == null then new Contact else i.contact)
      newMap.put("relation", i.relations.headOption.getOrElse(new Relation))
      tr.transfer.current_=(newMap)
    }

  }

  override def onItemFinish(tr: ImportResult): Unit = {
    val mImporter = transfer.asInstanceOf[MultiEntityImporter]
    val inpatient = mImporter.getCurrent("inpatient").asInstanceOf[Inpatient]
    val person = mImporter.getCurrent("person").asInstanceOf[Person]
    val contact = mImporter.getCurrent("contact").asInstanceOf[Contact]
    val relation = mImporter.getCurrent("relation").asInstanceOf[Relation]

    inpatient.person = person
    if (null == person.name) person.name = inpatient.name
    if (null == inpatient.gender) inpatient.gender = person.gender
    inpatient.contact = contact
    entityDao.saveOrUpdate(person, contact, inpatient)
    if (null != relation.name) {
      relation.inpatient = inpatient
      inpatient.relations += relation
      entityDao.saveOrUpdate(relation)
    }

  }
}
