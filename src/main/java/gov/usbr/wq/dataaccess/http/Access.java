/*
 * Copyright 2022 United States Bureau of Reclamation (USBR).
 * United States Department of the Interior
 * All Rights Reserved. USBR PROPRIETARY/CONFIDENTIAL.
 * Source may not be released without written approval
 * from USBR.
 */

package gov.usbr.wq.dataaccess.http;

import java.time.Instant;

public interface Access
{
	String getJsonMeasurementsByTemplateId(TokenContainer container, Integer templateId) throws HttpAccessException;

	String getJsonTemplates(TokenContainer container) throws HttpAccessException;

	String getJsonEventsBySeries(TokenContainer container, String seriesString, Integer qualityVersionID, Instant start, Instant end) throws HttpAccessException;

	String getJsonQualityVersions(TokenContainer container) throws HttpAccessException;
}
