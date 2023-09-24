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

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import java.util.HashMap;
import java.util.Map;

/**
 * WebService客户端
 *
 */
public class WebServiceClient {

  /**
   * 主方法
   *
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {
    WebServiceConfig cfg = WebServiceConfig.getInstance();

    Object service = callService(cfg.getAddress(), cfg.getServiceClass());
    // 请在此处添加业务代码

  }

  /**
   * 调用服务，生成客户端的服务代理
   *
   * @param address WebService的URL
   * @param serviceClass 服务接口全名
   * @return 服务代理对象
   * @throws Exception
   */
  public static Object callService(String address, Class serviceClass)
    throws Exception {

    JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();

    // 记录入站消息
    factory.getInInterceptors().add(new LoggingInInterceptor());

    // 记录出站消息
    factory.getOutInterceptors().add(new LoggingOutInterceptor());

    // 添加消息头验证信息。如果服务端要求验证用户密码，请加入此段代码
    // factory.getOutInterceptors().add(new AddSoapHeader());

    factory.setServiceClass(serviceClass);
    factory.setAddress(address);

    // 使用MTOM编码处理消息。如果需要在消息中传输文档附件等二进制内容，请加入此段代码
    // Map<String, Object> props = new HashMap<String, Object>();
    // props.put("mtom-enabled", Boolean.TRUE);
    // factory.setProperties(props);

    // 创建服务代理并返回
    return factory.create();
  }

}
