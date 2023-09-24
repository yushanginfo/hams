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
 * <p>sysSynchroGetOrgInfoContextV2 complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType name="sysSynchroGetOrgInfoContextV2"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://out.webservice.organization.sys.kmss.landray.com/}sysSynchroGetOrgInfoContext"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="extendPara" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="isBusiness" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sysSynchroGetOrgInfoContextV2", propOrder = {
    "extendPara",
    "isBusiness"
})
public class SysSynchroGetOrgInfoContextV2
    extends SysSynchroGetOrgInfoContext
{

    protected String extendPara;
    protected Boolean isBusiness;

    /**
     * 获取extendPara属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getExtendPara() {
        return extendPara;
    }

    /**
     * 设置extendPara属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setExtendPara(String value) {
        this.extendPara = value;
    }

    /**
     * 获取isBusiness属性的值。
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public Boolean isIsBusiness() {
        return isBusiness;
    }

    /**
     * 设置isBusiness属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setIsBusiness(Boolean value) {
        this.isBusiness = value;
    }

}
