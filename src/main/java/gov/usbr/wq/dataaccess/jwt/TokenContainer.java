/*
 * Copyright 2019  Hydrologic Engineering Center (HEC).
 * United States Army Corps of Engineers
 * All Rights Reserved.  HEC PROPRIETARY/CONFIDENTIAL.
 * Source may not be released without written approval
 * from HEC
 */

package gov.usbr.wq.dataaccess.jwt;

public interface TokenContainer
{
	boolean isValid();
	String getToken();
}
