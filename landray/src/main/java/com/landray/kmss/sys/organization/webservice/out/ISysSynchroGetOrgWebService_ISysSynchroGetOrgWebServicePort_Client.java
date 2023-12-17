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

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.ws.RequestWrapper;
import jakarta.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 4.0.2
 * 2023-07-31T14:35:37.249+08:00
 * Generated source version: 4.0.2
 *
 */
public final class ISysSynchroGetOrgWebService_ISysSynchroGetOrgWebServicePort_Client {

    private static final QName SERVICE_NAME = new QName("http://out.webservice.organization.sys.kmss.landray.com/", "ISysSynchroGetOrgWebServiceService");

    private ISysSynchroGetOrgWebService_ISysSynchroGetOrgWebServicePort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = ISysSynchroGetOrgWebServiceService.WSDL_LOCATION;
        if (args.length > 0 && args[0] != null && !"".equals(args[0])) {
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        ISysSynchroGetOrgWebServiceService ss = new ISysSynchroGetOrgWebServiceService(wsdlURL, SERVICE_NAME);
        ISysSynchroGetOrgWebService port = ss.getISysSynchroGetOrgWebServicePort();

        {
        System.out.println("Invoking getRoleLineInfo...");
        com.landray.kmss.sys.organization.webservice.out.SysSynchroGetOrgInfoContext _getRoleLineInfo_arg0 = null;
        try {
            com.landray.kmss.sys.organization.webservice.out.SysSynchroOrgResult _getRoleLineInfo__return = port.getRoleLineInfo(_getRoleLineInfo_arg0);
            System.out.println("getRoleLineInfo.result=" + _getRoleLineInfo__return);

        } catch (Exception_Exception e) {
            System.out.println("Expected exception: Exception has occurred.");
            System.out.println(e.toString());
        }

            }
        {
        System.out.println("Invoking getOrgStaffingLevelInfo...");
        com.landray.kmss.sys.organization.webservice.out.SysSynchroGetOrgInfoContext _getOrgStaffingLevelInfo_arg0 = null;
        try {
            com.landray.kmss.sys.organization.webservice.out.SysSynchroOrgResult _getOrgStaffingLevelInfo__return = port.getOrgStaffingLevelInfo(_getOrgStaffingLevelInfo_arg0);
            System.out.println("getOrgStaffingLevelInfo.result=" + _getOrgStaffingLevelInfo__return);

        } catch (Exception_Exception e) {
            System.out.println("Expected exception: Exception has occurred.");
            System.out.println(e.toString());
        }

            }
        {
        System.out.println("Invoking getRoleLineDefaultRoleInfo...");
        com.landray.kmss.sys.organization.webservice.out.SysSynchroGetOrgInfoContext _getRoleLineDefaultRoleInfo_arg0 = null;
        try {
            com.landray.kmss.sys.organization.webservice.out.SysSynchroOrgResult _getRoleLineDefaultRoleInfo__return = port.getRoleLineDefaultRoleInfo(_getRoleLineDefaultRoleInfo_arg0);
            System.out.println("getRoleLineDefaultRoleInfo.result=" + _getRoleLineDefaultRoleInfo__return);

        } catch (Exception_Exception e) {
            System.out.println("Expected exception: Exception has occurred.");
            System.out.println(e.toString());
        }

            }
        {
        System.out.println("Invoking getRoleInfo...");
        com.landray.kmss.sys.organization.webservice.out.SysSynchroGetOrgInfoContext _getRoleInfo_arg0 = null;
        try {
            com.landray.kmss.sys.organization.webservice.out.SysSynchroOrgResult _getRoleInfo__return = port.getRoleInfo(_getRoleInfo_arg0);
            System.out.println("getRoleInfo.result=" + _getRoleInfo__return);

        } catch (Exception_Exception e) {
            System.out.println("Expected exception: Exception has occurred.");
            System.out.println(e.toString());
        }

            }
        {
        System.out.println("Invoking getUpdatedElementsV2...");
        com.landray.kmss.sys.organization.webservice.out.SysSynchroGetOrgInfoContextV2 _getUpdatedElementsV2_arg0 = null;
        try {
            com.landray.kmss.sys.organization.webservice.out.SysSynchroOrgResult _getUpdatedElementsV2__return = port.getUpdatedElementsV2(_getUpdatedElementsV2_arg0);
            System.out.println("getUpdatedElementsV2.result=" + _getUpdatedElementsV2__return);

        } catch (Exception_Exception e) {
            System.out.println("Expected exception: Exception has occurred.");
            System.out.println(e.toString());
        }

            }
        {
        System.out.println("Invoking getUpdatedElements...");
        com.landray.kmss.sys.organization.webservice.out.SysSynchroGetOrgInfoContext _getUpdatedElements_arg0 = null;
        try {
            com.landray.kmss.sys.organization.webservice.out.SysSynchroOrgResult _getUpdatedElements__return = port.getUpdatedElements(_getUpdatedElements_arg0);
            System.out.println("getUpdatedElements.result=" + _getUpdatedElements__return);

        } catch (Exception_Exception e) {
            System.out.println("Expected exception: Exception has occurred.");
            System.out.println(e.toString());
        }

            }
        {
        System.out.println("Invoking getUpdatedElementsByToken...");
        com.landray.kmss.sys.organization.webservice.out.SysSynchroGetOrgInfoTokenContext _getUpdatedElementsByToken_arg0 = null;
        try {
            com.landray.kmss.sys.organization.webservice.out.SysSynchroOrgTokenResult _getUpdatedElementsByToken__return = port.getUpdatedElementsByToken(_getUpdatedElementsByToken_arg0);
            System.out.println("getUpdatedElementsByToken.result=" + _getUpdatedElementsByToken__return);

        } catch (Exception_Exception e) {
            System.out.println("Expected exception: Exception has occurred.");
            System.out.println(e.toString());
        }

            }
        {
        System.out.println("Invoking getRoleConfMemberInfo...");
        com.landray.kmss.sys.organization.webservice.out.SysSynchroGetOrgInfoContext _getRoleConfMemberInfo_arg0 = null;
        try {
            com.landray.kmss.sys.organization.webservice.out.SysSynchroOrgResult _getRoleConfMemberInfo__return = port.getRoleConfMemberInfo(_getRoleConfMemberInfo_arg0);
            System.out.println("getRoleConfMemberInfo.result=" + _getRoleConfMemberInfo__return);

        } catch (Exception_Exception e) {
            System.out.println("Expected exception: Exception has occurred.");
            System.out.println(e.toString());
        }

            }
        {
        System.out.println("Invoking getOrgGroupCateInfo...");
        com.landray.kmss.sys.organization.webservice.out.SysSynchroGetOrgInfoContext _getOrgGroupCateInfo_arg0 = null;
        try {
            com.landray.kmss.sys.organization.webservice.out.SysSynchroOrgResult _getOrgGroupCateInfo__return = port.getOrgGroupCateInfo(_getOrgGroupCateInfo_arg0);
            System.out.println("getOrgGroupCateInfo.result=" + _getOrgGroupCateInfo__return);

        } catch (Exception_Exception e) {
            System.out.println("Expected exception: Exception has occurred.");
            System.out.println(e.toString());
        }

            }
        {
        System.out.println("Invoking getRoleConfInfo...");
        com.landray.kmss.sys.organization.webservice.out.SysSynchroGetOrgInfoContext _getRoleConfInfo_arg0 = null;
        try {
            com.landray.kmss.sys.organization.webservice.out.SysSynchroOrgResult _getRoleConfInfo__return = port.getRoleConfInfo(_getRoleConfInfo_arg0);
            System.out.println("getRoleConfInfo.result=" + _getRoleConfInfo__return);

        } catch (Exception_Exception e) {
            System.out.println("Expected exception: Exception has occurred.");
            System.out.println(e.toString());
        }

            }
        {
        System.out.println("Invoking getElementsBaseInfo...");
        com.landray.kmss.sys.organization.webservice.out.SysSynchroGetOrgBaseInfoContext _getElementsBaseInfo_arg0 = null;
        try {
            com.landray.kmss.sys.organization.webservice.out.SysSynchroOrgResult _getElementsBaseInfo__return = port.getElementsBaseInfo(_getElementsBaseInfo_arg0);
            System.out.println("getElementsBaseInfo.result=" + _getElementsBaseInfo__return);

        } catch (Exception_Exception e) {
            System.out.println("Expected exception: Exception has occurred.");
            System.out.println(e.toString());
        }

            }
        {
        System.out.println("Invoking getElementsBaseInfoV2...");
        com.landray.kmss.sys.organization.webservice.out.SysSynchroGetOrgBaseInfoContextV2 _getElementsBaseInfoV2_arg0 = null;
        try {
            com.landray.kmss.sys.organization.webservice.out.SysSynchroOrgResult _getElementsBaseInfoV2__return = port.getElementsBaseInfoV2(_getElementsBaseInfoV2_arg0);
            System.out.println("getElementsBaseInfoV2.result=" + _getElementsBaseInfoV2__return);

        } catch (Exception_Exception e) {
            System.out.println("Expected exception: Exception has occurred.");
            System.out.println(e.toString());
        }

            }
        {
        System.out.println("Invoking getRoleConfCateInfo...");
        com.landray.kmss.sys.organization.webservice.out.SysSynchroGetOrgInfoContext _getRoleConfCateInfo_arg0 = null;
        try {
            com.landray.kmss.sys.organization.webservice.out.SysSynchroOrgResult _getRoleConfCateInfo__return = port.getRoleConfCateInfo(_getRoleConfCateInfo_arg0);
            System.out.println("getRoleConfCateInfo.result=" + _getRoleConfCateInfo__return);

        } catch (Exception_Exception e) {
            System.out.println("Expected exception: Exception has occurred.");
            System.out.println(e.toString());
        }

            }

        System.exit(0);
    }

}