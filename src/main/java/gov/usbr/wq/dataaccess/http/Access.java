/*
 * Copyright 2019  Hydrologic Engineering Center (HEC).
 * United States Army Corps of Engineers
 * All Rights Reserved.  HEC PROPRIETARY/CONFIDENTIAL.
 * Source may not be released without written approval
 * from HEC
 */

package gov.usbr.wq.dataaccess.http;

import gov.usbr.wq.dataaccess.jwt.TokenContainer;

import java.io.IOException;
import java.util.Map;

public interface Access
{
	String get(String api, TokenContainer token) throws IOException;

	String get(String api, TokenContainer token, Map<String, String> queryParams) throws IOException, HttpAccessException;

	TokenContainer authenticate(String user, String pass) throws IOException;
}
