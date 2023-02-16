/*
 * Copyright 2022 United States Bureau of Reclamation (USBR).
 * United States Department of the Interior
 * All Rights Reserved. USBR PROPRIETARY/CONFIDENTIAL.
 * Source may not be released without written approval
 * from USBR.
 */

package gov.usbr.wq.dataaccess.http;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

final class HttpAccess implements Access
{

	HttpAccess()
	{

	}

	@Override
	public String getJsonMeasurementsByTemplateId(ApiConnectionInfo connectionInfo, TokenContainer token, Integer templateId) throws HttpAccessException
	{
		String api = "/MerlinWebService/GetMeasurementsByTemplate";
		Map<String, String> queryParams = new HashMap<>();
		queryParams.put("templateID", String.valueOf(templateId));
		return HttpAccessUtils.getJsonWithToken(connectionInfo, token, api, queryParams);
	}

	@Override
	public String getJsonTemplates(ApiConnectionInfo connectionInfo, TokenContainer token) throws HttpAccessException
	{
		String api = "/MerlinWebService/GetTemplates";
		return HttpAccessUtils.getJsonWithToken(connectionInfo, token, api);
	}

	@Override
	public String getJsonEventsBySeries(ApiConnectionInfo connectionInfo, TokenContainer token, String seriesString, Integer qualityVersionID, Instant start, Instant end) throws HttpAccessException
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
		if(qualityVersionID != null)
		{
			queryParams.put("qvID", qualityVersionID.toString());
		}
		return HttpAccessUtils.getJsonWithToken(connectionInfo, token, api, queryParams);
	}

	@Override
	public String getJsonQualityVersions(ApiConnectionInfo connectionInfo, TokenContainer token) throws HttpAccessException
	{
		String api = "/MerlinWebService/GetQualityVersions";
		return HttpAccessUtils.getJsonWithToken(connectionInfo, token, api);
	}
}
