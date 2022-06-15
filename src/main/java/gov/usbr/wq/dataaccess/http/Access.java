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
import java.time.Instant;

public interface Access
{
	String getJsonMeasurementsByProfileId(TokenContainer container, Integer profileId) throws IOException, HttpAccessException;

	String getJsonProfiles(TokenContainer container) throws IOException, HttpAccessException;

	String getJsonEventsBySeries(TokenContainer container, String seriesString, Instant start, Instant end) throws IOException, HttpAccessException;
}
