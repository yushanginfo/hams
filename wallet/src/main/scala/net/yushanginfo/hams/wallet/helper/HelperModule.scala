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

package net.yushanginfo.hams.wallet.helper

import net.yushanginfo.hams.wallet.service.{MealBillGeneratorDaily, MealBillStatMonthly}
import org.beangle.cdi.bind.BindModule
import org.springframework.scheduling.config.CronTask

class HelperModule extends BindModule {

  protected def bindTask[T <: Runnable](clazz: Class[T], expression: String): Unit = {
    val taskName = clazz.getName
    bind(taskName + "Task", classOf[CronTask]).constructor(ref(taskName), expression)
  }

  override def binding(): Unit = {
    bind(classOf[MealBillGeneratorDaily])
    bind(classOf[MealBillStatMonthly])
    bindTask(classOf[MealBillGeneratorDaily], "0 0 23 * * *")
    bindTask(classOf[MealBillStatMonthly], "0 0 0 1 * *")
  }

}
