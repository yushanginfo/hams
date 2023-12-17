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
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.XmlType;

/**
 * <p>sysSynchroGetOrgInfoContext complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType name="sysSynchroGetOrgInfoContext"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://out.webservice.organization.sys.kmss.landray.com/}sysSynchroGetOrgContext"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="beginTimeStamp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="count" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sysSynchroGetOrgInfoContext", propOrder = {
    "beginTimeStamp",
    "count"
})
@XmlSeeAlso({
    SysSynchroGetOrgInfoTokenContext.class,
    SysSynchroGetOrgInfoContextV2 .class
})
public class SysSynchroGetOrgInfoContext
    extends SysSynchroGetOrgContext
{

    protected String beginTimeStamp;
    protected int count;

    /**
     * 获取beginTimeStamp属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getBeginTimeStamp() {
        return beginTimeStamp;
    }

    /**
     * 设置beginTimeStamp属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setBeginTimeStamp(String value) {
        this.beginTimeStamp = value;
    }

    /**
     * 获取count属性的值。
     *
     */
    public int getCount() {
        return count;
    }

    /**
     * 设置count属性的值。
     *
     */
    public void setCount(int value) {
        this.count = value;
    }

}
