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

package net.yushanginfo.landray.ws

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.landray.kmss.sys.organization.webservice.out.{ISysSynchroGetOrgWebService, SysSynchroGetOrgBaseInfoContext, SysSynchroGetOrgInfoContext, SysSynchroOrgResult}
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean

import java.io.{File, FileWriter}
import java.time.OffsetDateTime
import java.util
import java.util.Map
import scala.collection.mutable

object Sync {
  def main(args: Array[String]): Unit = {
    if (args.length < 1) {
      println("Sync dir")
      return;
    }
    new File(args(0)).mkdirs()
    val sync = new Sync
    val departs = sync.getDeparts()
    var sb = new StringBuilder("[")
    sb.append(departs.map(_.toJson()).mkString(","))
    sb.append("]")
    writeToFile(sb, args(0) + "/org.json")

    sb = new StringBuilder("[")
    val staffs = sync.getStaffs(departs)
    sb.append(staffs.map(_.toJson()).mkString(","))
    sb.append("]")
    writeToFile(sb, args(0) + "/person.json")
  }

  private def writeToFile(sb: StringBuilder, filePath: String): Unit = {
    val writer = new FileWriter(new File(filePath))
    writer.write(sb.toString)
    writer.close()
  }
}

class Sync {

  // 定义组织架构接出WebService对象
  private var getOrgWebService: ISysSynchroGetOrgWebService = null

  /**
   * 获取所有组织架构基本信息
   *
   * @param orgContext 组织架构基本信息接出上下文
   * @return
   * @throws Exception
   */
  @throws[Exception]
  private def getElementsBaseInfo(orgContext: SysSynchroGetOrgBaseInfoContext): SysSynchroOrgResult = {
    getService().getElementsBaseInfo(orgContext)
  }

  @throws[Exception]
  private def getUpdatedElements(orgContext: SysSynchroGetOrgInfoContext): SysSynchroOrgResult = {
    getService().getUpdatedElements(orgContext)
  }

  /**
   * 获取组织架构接出WebService对象
   *
   * @return
   */
  private def getService(): ISysSynchroGetOrgWebService = {
    if (getOrgWebService == null) {
      // WebService配置信息对象（读取自client.properties配置文件）
      val serviceConfig = WebServiceConfig.getInstance
      // 使用 Apache CXF 框架创建组织架构接出WebService对象
      val factory = new JaxWsProxyFactoryBean
      factory.getOutInterceptors.add(new AddSoapHeader)
      factory.setServiceClass(classOf[ISysSynchroGetOrgWebService])
      // 设置服务请求的URL地址
      val servicePath = serviceConfig.getAddress
      factory.setAddress(servicePath)
      getOrgWebService = factory.create.asInstanceOf[ISysSynchroGetOrgWebService]
    }
    getOrgWebService
  }

  def getStaffs(departs: collection.Seq[SyncData.Depart]): collection.Seq[SyncData.Staff] = {
    val departMap = departs.map(x => (x.id, x)).toMap
    val staffs = new mutable.HashMap[String, SyncData.Staff]
    val orgContext = new SysSynchroGetOrgInfoContext
    val returnOrgType = "[{\"type\":\"person\"}]"
    orgContext.setReturnOrgType(returnOrgType)
    orgContext.setCount(10000)
    try {
      val result = getUpdatedElements(orgContext)
      if (result.getReturnState != 2) { // 0:未操作、1:失败、2:成功
        return List.empty
      }
      if (result.getCount > 0) {
        val gson: Gson = new Gson
        val datas = gson.fromJson(result.getMessage, TypeToken.getArray(classOf[util.Map[_, _]])).asInstanceOf[Array[util.Map[String, AnyRef]]]
        for (data <- datas) {
          val depart = if null == data.get("parent") then None else departMap.get(data.get("parent").toString)
          if (data.get("isAvailable").toString != "false" && depart.nonEmpty) {
            val staff = new SyncData.Staff
            staff.id = data.get("id").toString
            staff.code = data.get("no").toString
            staff.name = data.get("name").toString
            staff.genderCode = data.get("sex").toString
            staff.mobile = if null == data.get("mobileNo") then None else Some(data.get("mobileNo").toString)
            staff.departCode = depart.get.code
            val alterTime: AnyRef = data.get("alterTime")
            if (null != alterTime) {
              staff.updatedAt = OffsetDateTime.parse(alterTime.toString.replace(' ', 'T').substring(0, 19) + "+08:00").toInstant
            }
            staffs.put(staff.id, staff)
          }
        }
      }
    } catch {
      case e: Exception => e.printStackTrace()
    }
    staffs.values.toBuffer.sortBy(x => x.code)
  }

  def getDeparts(): collection.Seq[SyncData.Depart] = {
    val departs = new mutable.HashMap[String, SyncData.Depart]
    val orgContext = new SysSynchroGetOrgInfoContext
    val returnOrgType = "[{\"type\":\"dept\"}]"
    orgContext.setReturnOrgType(returnOrgType)
    orgContext.setCount(1000)
    try {
      val result = getUpdatedElements(orgContext)
      if (result.getReturnState != 2) { // 0:未操作、1:失败、2:成功
        return List.empty
      }
      if (result.getCount > 0) {
        val gson: Gson = new Gson
        val datas = gson.fromJson(result.getMessage, TypeToken.getArray(classOf[util.Map[_, _]])).asInstanceOf[Array[util.Map[String, AnyRef]]]
        for (data <- datas) {
          val depart = new SyncData.Depart
          depart.id = data.get("id").toString
          depart.code = data.get("no").toString
          depart.name = data.get("name").toString
          depart.indexno = data.get("order").toString
          if (depart.indexno.length == 1) depart.indexno = "0" + depart.indexno
          depart.parentId = if null == data.get("parent") then None else Some(data.get("parent").toString)
          val alterTime: AnyRef = data.get("alterTime")
          if (null != alterTime) {
            depart.updatedAt = OffsetDateTime.parse(alterTime.toString.replace(' ', 'T').substring(0, 19) + "+08:00").toInstant
          }
          departs.put(depart.id, depart)
        }
      }
    } catch {
      case e: Exception => e.printStackTrace()
    }
    departs foreach { case (_, depart) =>
      depart.parentId foreach { pid =>
        if !departs.contains(pid) then depart.parentId = None
      }
    }
    departs.values.toBuffer.sortBy(_.indexno)
  }
}
