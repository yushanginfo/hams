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

package net.yushanginfo.hams.account.web.helper

import net.yushanginfo.hams.account.model.{Subsidy, SubsidyBill}
import net.yushanginfo.hams.account.service.SubsidyService
import net.yushanginfo.hams.base.model.Yuan
import net.yushanginfo.hams.base.service.InpatientService
import org.beangle.data.dao.EntityDao
import org.beangle.data.transfer.importer.{ImportListener, ImportResult}

class SubsidyBillImportListener(inpatientService: InpatientService, subsidyService: SubsidyService, entityDao: EntityDao) extends ImportListener {

  override def onItemFinish(tr: ImportResult): Unit = {
    val data = tr.transfer.curData
    val name = data.get("subsidyBill.account.inpatient.name").orNull.asInstanceOf[String]
    inpatientService.getInpatientByName(name) match
      case None => tr.addFailure("错误的姓名", name)
      case Some(inpatient) =>
        val po = tr.transfer.current.asInstanceOf[SubsidyBill]
        val payAt = po.payAt
        if (null == payAt) {
          tr.addFailure("缺少支出时间", name)
        } else {
          if (po.amount.value > 0) {
            po.amount = Yuan.Zero - po.amount
          }
          subsidyService.get(inpatient) match
            case None => tr.addFailure("缺少账户余额信息", name)
            case Some(subsidy) =>
              val bill = subsidyService.getBill(subsidy, po.amount, payAt) match
                case None => subsidy.newBill(po.amount, po.payAt, po.expenses)
                case Some(i) => i
              bill.expenses = po.expenses
              bill.remark = po.remark
              entityDao.saveOrUpdate(subsidy, bill)
        }
  }
}
