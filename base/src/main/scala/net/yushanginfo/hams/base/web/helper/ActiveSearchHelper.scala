package net.yushanginfo.hams.base.web.helper

import org.beangle.data.dao.OqlBuilder
import org.beangle.data.model.Entity

import java.time.LocalDate

object ActiveSearchHelper {

  def addTemporalOn[T <: Entity[_]](builder: OqlBuilder[T], active: Option[Boolean]): OqlBuilder[T] = {
    active.foreach { active =>
      if (active) {
        builder.where(
          builder.alias + ".beginOn <= :now and (" + builder.alias + ".endOn is null or " + builder.alias + ".endOn >= :now)",
          LocalDate.now())
      } else {
        builder.where(
          "not (" + builder.alias + ".beginOn <= :now and (" + builder.alias + ".endOn is null or " + builder.alias + ".endOn >= :now))",
          LocalDate.now())
      }
    }

    builder
  }
}
