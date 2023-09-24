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

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 解析配置文件
 */
public class WebServiceConfig {
  private static WebServiceConfig cfg = new WebServiceConfig();

  // Web服务的URL
  private String address;

  // Web服务接口
  private Class serviceClass;

  // Web服务标识
  private String serviceBean;

  // 用户
  private String user;

  // 密码
  private String password;

  private WebServiceConfig() {
    loadCfg();
  }

  public static WebServiceConfig getInstance() {
    return cfg;
  }

  /**
   * 解析配置文件
   */
  private void loadCfg() {
    Properties prop = new Properties();
    InputStream confStream = this.getClass().getResourceAsStream("/client.properties");

    try {
      prop.load(confStream);
      this.address = prop.getProperty("address");
      String serviceClassName = prop.getProperty("service_class");
      this.serviceClass = Class.forName(serviceClassName);
      this.serviceBean = prop.getProperty("service_bean");
      this.user = prop.getProperty("user");
      this.password = prop.getProperty("password");

    } catch (Exception e) {
      // TODO 自动生成的catch块
      e.printStackTrace();
    } finally {
      try {
        confStream.close();
      } catch (IOException e) {
        // TODO 自动生成的catch块
        e.printStackTrace();
      }
    }

  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Class getServiceClass() {
    return serviceClass;
  }

  public void setServiceClass(Class serviceClass) {
    this.serviceClass = serviceClass;
  }

  public String getServiceBean() {
    return serviceBean;
  }

  public void setServiceBean(String serviceBean) {
    this.serviceBean = serviceBean;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}
