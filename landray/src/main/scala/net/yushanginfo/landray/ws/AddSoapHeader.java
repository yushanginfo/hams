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

import java.util.List;
import javax.xml.namespace.QName;

import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * 消息拦截器，在报文中添加消息头，以支持服务端的验证
 *
 */
public class AddSoapHeader extends AbstractSoapInterceptor {

  public AddSoapHeader() {
    super(Phase.WRITE);
  }

  /**
   * 处理消息，添加消息头
   *
   * @param message SOAP消息
   * @throws Exception
   */
  public void handleMessage(SoapMessage message) throws Fault {
    WebServiceConfig cfg = WebServiceConfig.getInstance();
    Document doc = DOMUtils.createDocument();

    // 添加用户名信息
    Element userElement = doc.createElement("tns:user");
    userElement.setTextContent(cfg.getUser());

    // 添加密码信息
    Element pwdElement = doc.createElement("tns:password");
    pwdElement.setTextContent(cfg.getPassword());

    // 创建消息头节点
    Element root = doc.createElementNS("http://sys.webservice.client"
      , "tns:RequestSOAPHeader");
    root.appendChild(userElement);
    root.appendChild(pwdElement);

    QName qname = new QName("RequestSOAPHeader");
    SoapHeader head = new SoapHeader(qname, root);
    List<Header> headers = message.getHeaders();
    headers.add(head);
  }

}
