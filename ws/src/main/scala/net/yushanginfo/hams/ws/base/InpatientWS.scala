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

package net.yushanginfo.hams.ws.base

import net.yushanginfo.hams.base.model.Inpatient
import org.beangle.commons.collection.page.PageLimit
import org.beangle.commons.collection.{Order, Properties}
import org.beangle.commons.lang.Strings
import org.beangle.data.dao.{EntityDao, OqlBuilder}
import org.beangle.web.action.annotation.response
import org.beangle.web.action.support.ActionSupport
import org.beangle.webmvc.support.action.EntityAction
import org.beangle.webmvc.support.helper.QueryHelper.{PageParam, PageSizeParam}

class InpatientWS extends ActionSupport with EntityAction[Inpatient] {
  var entityDao: EntityDao = _

  @response
  def index(): Seq[Properties] = {
    val query = OqlBuilder.from(classOf[Inpatient], "i")
    populateConditions(query)
    query.limit(PageLimit(getInt(PageParam, 1), getInt(PageSizeParam, 100)))
    get("q") foreach { q =>
      if (Strings.isNotBlank(q)) {
        val c = s"%${q}%"
        query.where("i.name like :c or i.person.phoneticName like :c or i.bedNo like :c or i.code like :c", c)
      }
    }
    val orderStr = get(Order.OrderStr).getOrElse("i.name")
    query.orderBy(orderStr)

    entityDao.search(query).map { t =>
      new Properties(t, "id", "code", "name", "description")
    }
  }

}
