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

import jakarta.xml.ws.WebFault;

/**
 * This class was generated by Apache CXF 4.0.2
 * 2023-07-31T14:35:37.306+08:00
 * Generated source version: 4.0.2
 */

@WebFault(name = "Exception", targetNamespace = "http://out.webservice.organization.sys.kmss.landray.com/")
public class Exception_Exception extends java.lang.Exception {

    private com.landray.kmss.sys.organization.webservice.out.Exception faultInfo;

    public Exception_Exception() {
        super();
    }

    public Exception_Exception(String message) {
        super(message);
    }

    public Exception_Exception(String message, java.lang.Throwable cause) {
        super(message, cause);
    }

    public Exception_Exception(String message, com.landray.kmss.sys.organization.webservice.out.Exception exception) {
        super(message);
        this.faultInfo = exception;
    }

    public Exception_Exception(String message, com.landray.kmss.sys.organization.webservice.out.Exception exception, java.lang.Throwable cause) {
        super(message, cause);
        this.faultInfo = exception;
    }

    public com.landray.kmss.sys.organization.webservice.out.Exception getFaultInfo() {
        return this.faultInfo;
    }
}