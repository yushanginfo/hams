package net.yushanginfo.hams.base.service.impl

import net.yushanginfo.hams.base.service.CodeService
import org.beangle.data.dao.{EntityDao, OqlBuilder}

class CodeServiceImpl extends CodeService {
  var entityDao: EntityDao = _

  def get[T](clazz: Class[T]): Seq[T] = {
    val query = OqlBuilder.from(clazz, "q")
    query.where("q.endOn is null")
    query.cacheable()
    entityDao.search(query)
  }
}
