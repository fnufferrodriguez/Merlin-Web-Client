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
import java.util.HashMap;
import java.util.Map;

final class HttpAccess implements Access
{

	HttpAccess()
	{

	}

	@Override
	public String getJsonMeasurementsByProfileId(TokenContainer token, Integer profileId) throws HttpAccessException
	{
		String api = "/MerlinWebService/GetMeasurementsByProfile";
		Map<String, String> queryParams = new HashMap<>();
		queryParams.put("profileID", String.valueOf(profileId));
		return HttpAccessUtils.getJson(token, api, queryParams);
	}

	@Override
	public String getJsonProfiles(TokenContainer token) throws HttpAccessException
	{
		String api = "/MerlinWebService/GetProfiles";
		return HttpAccessUtils.getJson(token, api);
	}

	@Override
	public String getJsonEventsBySeries(TokenContainer token, String seriesString, Instant start, Instant end) throws HttpAccessException
	{
		String api = "/MerlinWebService/GetEventsBySeriesString";
		Map<String, String> queryParams = new HashMap<>();
		queryParams.put("seriesString", seriesString);
		if (start != null)
		{
			queryParams.put("startDate", start.toString());
		}
		if (end != null)
		{
			queryParams.put("endDate", end.toString());
		}
		return HttpAccessUtils.getJson(token, api, queryParams);
	}
}
