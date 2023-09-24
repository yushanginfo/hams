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

package com.landray.kmss.sys.organization.webservice.out;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

/**
 * <p>sysSynchroGetOrgInfoTokenContext complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType name="sysSynchroGetOrgInfoTokenContext"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://out.webservice.organization.sys.kmss.landray.com/}sysSynchroGetOrgInfoContext"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="pageNo" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sysSynchroGetOrgInfoTokenContext", propOrder = {
    "pageNo",
    "token"
})
public class SysSynchroGetOrgInfoTokenContext
    extends SysSynchroGetOrgInfoContext
{

    protected int pageNo;
    protected String token;

    /**
     * 获取pageNo属性的值。
     *
     */
    public int getPageNo() {
        return pageNo;
    }

    /**
     * 设置pageNo属性的值。
     *
     */
    public void setPageNo(int value) {
        this.pageNo = value;
    }

    /**
     * 获取token属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getToken() {
        return token;
    }

    /**
     * 设置token属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setToken(String value) {
        this.token = value;
    }

}
