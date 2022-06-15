/*
 * Copyright {year} United States Bureau of Reclamation (USBR).
 * United States Department of the Interior
 * All Rights Reserved. USBR PROPRIETARY/CONFIDENTIAL.
 * Source may not be released without written approval
 * from USBR
 */

package gov.usbr.wq.dataaccess.http;

import gov.usbr.wq.dataaccess.jwt.NoOpTokenContainer;
import gov.usbr.wq.dataaccess.jwt.TokenContainer;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;

/**
 * Created by Ryan Miles
 */
public class TestAccess implements Access
{

	public TestAccess()
	{

	}

	@Override
	public String getJsonMeasurementsByProfileId(TokenContainer token, Integer profileId)
	{
		return null;
	}

	@Override
	public String getJsonProfiles(TokenContainer token)
	{
		return null;
	}

	@Override
	public String getJsonEventsBySeries(TokenContainer token, String seriesString, Instant start, Instant end)
	{
		return null;
	}
}
