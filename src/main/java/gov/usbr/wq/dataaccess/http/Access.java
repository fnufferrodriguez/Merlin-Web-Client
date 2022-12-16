/*
 * Copyright 2022 United States Bureau of Reclamation (USBR).
 * United States Department of the Interior
 * All Rights Reserved. USBR PROPRIETARY/CONFIDENTIAL.
 * Source may not be released without written approval
 * from USBR.
 */

package gov.usbr.wq.dataaccess.http;

import gov.usbr.wq.dataaccess.jwt.TokenContainer;

import java.time.Instant;

public interface Access
{
	String getJsonMeasurementsByProfileId(TokenContainer container, Integer profileId) throws HttpAccessException;

	String getJsonProfiles(TokenContainer container) throws HttpAccessException;

	String getJsonEventsBySeries(TokenContainer container, String seriesString, Instant start, Instant end) throws HttpAccessException;
}
