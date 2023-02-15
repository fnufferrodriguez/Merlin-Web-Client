/*
 * Copyright 2022 United States Bureau of Reclamation (USBR).
 * United States Department of the Interior
 * All Rights Reserved. USBR PROPRIETARY/CONFIDENTIAL.
 * Source may not be released without written approval
 * from USBR.
 */

package gov.usbr.wq.dataaccess.http;

import java.time.Instant;

/**
 * Created by Ryan Miles
 */
public class TestAccess implements Access
{

	public TestAccess()
	{

	}

	@Override
	public String getJsonMeasurementsByTemplateId(String rootUrl, TokenContainer token, Integer templateId)
	{
		return null;
	}

	@Override
	public String getJsonTemplates(String rootUrl, TokenContainer token)
	{
		return null;
	}

	@Override
	public String getJsonEventsBySeries(String rootUrl, TokenContainer token, String seriesString, Integer qvID, Instant start, Instant end)
	{
		return null;
	}

	@Override
	public String getJsonQualityVersions(String rootUrl, TokenContainer container) throws HttpAccessException {
		return null;
	}
}
