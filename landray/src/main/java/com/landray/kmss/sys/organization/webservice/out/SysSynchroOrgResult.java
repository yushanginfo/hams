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
 * <p>sysSynchroOrgResult complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType name="sysSynchroOrgResult"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="count" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="returnState" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="timeStamp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sysSynchroOrgResult", propOrder = {
    "count",
    "message",
    "returnState",
    "timeStamp"
})
public class SysSynchroOrgResult {

    protected int count;
    protected String message;
    protected int returnState;
    protected String timeStamp;

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

    /**
     * 获取message属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getMessage() {
        return message;
    }

    /**
     * 设置message属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setMessage(String value) {
        this.message = value;
    }

    /**
     * 获取returnState属性的值。
     *
     */
    public int getReturnState() {
        return returnState;
    }

    /**
     * 设置returnState属性的值。
     *
     */
    public void setReturnState(int value) {
        this.returnState = value;
    }

    /**
     * 获取timeStamp属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getTimeStamp() {
        return timeStamp;
    }

    /**
     * 设置timeStamp属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setTimeStamp(String value) {
        this.timeStamp = value;
    }

}
