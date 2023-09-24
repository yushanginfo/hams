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

package net.yushanginfo.landray.ws;

import com.landray.kmss.sys.organization.webservice.out.ISysSynchroGetOrgWebService;
import com.landray.kmss.sys.organization.webservice.out.SysSynchroGetOrgBaseInfoContext;
import com.landray.kmss.sys.organization.webservice.out.SysSynchroGetOrgInfoContext;
import com.landray.kmss.sys.organization.webservice.out.SysSynchroOrgResult;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

public class Test {

  // 定义组织架构接出WebService对象
  private ISysSynchroGetOrgWebService getOrgWebService = null;

  /**
   * 获取所有组织架构基本信息
   *
   * @param orgContext 组织架构基本信息接出上下文
   * @return
   * @throws Exception
   */
  public SysSynchroOrgResult getElementsBaseInfo(SysSynchroGetOrgBaseInfoContext orgContext) throws Exception {
    return getService().getElementsBaseInfo(orgContext);
  }

  public SysSynchroOrgResult getUpdatedElements(SysSynchroGetOrgInfoContext orgContext) throws Exception {
    return getService().getUpdatedElements(orgContext);
  }

  /**
   * 获取组织架构接出WebService对象
   *
   * @return
   */
  private ISysSynchroGetOrgWebService getService() {
    if (getOrgWebService == null) {
      // WebService配置信息对象（读取自client.properties配置文件）
      WebServiceConfig serviceConfig = WebServiceConfig.getInstance();
      // 使用 Apache CXF 框架创建组织架构接出WebService对象
      JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
      factory.getInInterceptors().add(new LoggingInInterceptor());
      factory.getOutInterceptors().add(new LoggingOutInterceptor());
      factory.getOutInterceptors().add(new AddSoapHeader());
      factory.setServiceClass(ISysSynchroGetOrgWebService.class);
      // 设置服务请求的URL地址
      String servicePath = serviceConfig.getAddress();
      factory.setAddress(servicePath);
      getOrgWebService = (ISysSynchroGetOrgWebService) factory.create();
    }
    return getOrgWebService;
  }

  public static void main(String[] args) {
    Test test = new Test();

    // 定义组织架构基本信息接出上下文
    SysSynchroGetOrgInfoContext orgContext = new SysSynchroGetOrgInfoContext();

    // 参数：组织类型JSON数组字符串（ 可选项: org(机构)、dept(部门)、group(群组)、post(岗位)、person(人员)）
    // 按需增减参数，如仅需要人员数据时: String returnOrgType = "[{\"type\":\"person\"}]";
    //String returnOrgType = "[{\"type\":\"org\"},{\"type\":\"dept\"},{\"type\":\"group\"},{\"type\":\"post\"},{\"type\":\"person\"}]";
    //String returnOrgType = "[{\"type\":\"org\"},{\"type\":\"dept\"},{\"type\":\"person\"}]";
    String returnOrgType = "[{\"type\":\"dept\"}]";
    orgContext.setReturnOrgType(returnOrgType);
    // 定义需要添加返回的信息字段（  可选项: no(编号)、keyword(关键字) ）
    // String returnType = " [{\"type\":\"no\"},{\"type\":\"keyword\"},{\"type\":\"mobileNo\"}]";
    orgContext.setReturnOrgType(returnOrgType);
    orgContext.setCount(10000);
    try {
//       调用WebService接口，并接收请求返回的数据
      var result = test.getUpdatedElements(orgContext);
      System.out.println("请求状态:\n" + result.getReturnState());  // 0:未操作、1:失败、2:成功
      System.out.println("记录条数:\n" + result.getCount());
      System.out.println("组织架构数据:\n" + result.getMessage());

//      var context = new SysSynchroGetOrgInfoContext();
//      var returnOrgType = "[{\"type\":\"org\"},{\"type\":\"dept\"},{\"type\":\"person\"}]";
//      context.setReturnOrgType(returnOrgType);
//      SysSynchroOrgResult result2 = test.getUpdatedElements(context);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
