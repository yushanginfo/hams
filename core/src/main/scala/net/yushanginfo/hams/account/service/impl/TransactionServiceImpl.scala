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

package net.yushanginfo.hams.account.service.impl

import net.yushanginfo.hams.account.service.TransactionService
import net.yushanginfo.hams.base.model.Transaction
import org.beangle.commons.collection.Collections
import org.beangle.data.dao.{EntityDao, OqlBuilder}

import java.time.Instant

class TransactionServiceImpl extends TransactionService {

  var entityDao: EntityDao = _

  override def getLast(before: Instant, clazzes: Seq[Class[_]], params: Map[String, Any]): Option[Transaction] = {
    assert(params.nonEmpty, "需要指定参数查询流水")
    val ts = Collections.newBuffer[Transaction]
    clazzes foreach { clazz =>
      val query = OqlBuilder.from[Transaction](clazz.getName, "t")
      query.where("t.payAt<:before", before)
      query.orderBy("t.payAt desc")
      var index = 0
      params.foreach { case (k, v) =>
        query.where(s"t.${k}=:v${index}", v)
        index += 1
      }
      query.limit(1, 1)
      ts ++= entityDao.search(query)
    }
    ts.sortBy(_.payAt).reverse
    ts.headOption
  }

  override def find(beginAt: Instant, clazzes: Seq[Class[_]], params: Map[String, Any]): collection.Seq[Transaction] = {
    assert(params.nonEmpty, "需要指定参数查询流水")

    val ts = Collections.newBuffer[Transaction]
    clazzes foreach { clazz =>
      val query = OqlBuilder.from[Transaction](clazz.getName, "t")
      query.where("t.payAt>=:before", beginAt)
      var index = 0
      params.foreach { case (k, v) =>
        query.where(s"t.${k}=:v${index}", v)
        index += 1
      }
      ts ++= entityDao.search(query)
    }
    ts.sortBy(_.payAt)
  }
}
