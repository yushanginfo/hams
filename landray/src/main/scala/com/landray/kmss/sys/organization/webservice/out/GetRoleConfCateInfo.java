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
 * <p>getRoleConfCateInfo complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType name="getRoleConfCateInfo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="arg0" type="{http://out.webservice.organization.sys.kmss.landray.com/}sysSynchroGetOrgInfoContext" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getRoleConfCateInfo", propOrder = {
    "arg0"
})
public class GetRoleConfCateInfo {

    protected SysSynchroGetOrgInfoContext arg0;

    /**
     * 获取arg0属性的值。
     *
     * @return
     *     possible object is
     *     {@link SysSynchroGetOrgInfoContext }
     *
     */
    public SysSynchroGetOrgInfoContext getArg0() {
        return arg0;
    }

    /**
     * 设置arg0属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link SysSynchroGetOrgInfoContext }
     *
     */
    public void setArg0(SysSynchroGetOrgInfoContext value) {
        this.arg0 = value;
    }

}
