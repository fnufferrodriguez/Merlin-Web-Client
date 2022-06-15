package gov.usbr.wq.dataaccess.http;

import gov.usbr.wq.dataaccess.jwt.JwtContainer;
import gov.usbr.wq.dataaccess.jwt.TokenContainer;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public final class HttpAccess implements Access
{

	public HttpAccess()
	{

	}

	@Override
	public String getJsonMeasurementsByProfileId(TokenContainer token, Integer profileId) throws IOException, HttpAccessException
	{
		String api = "/MerlinWebService/GetMeasurementsByProfile";
		Map<String, String> queryParams = new HashMap<>();
		queryParams.put("profileID", String.valueOf(profileId));
		return HttpAccessUtils.getJson(token, api, queryParams);
	}

	@Override
	public String getJsonProfiles(TokenContainer token) throws IOException, HttpAccessException
	{
		String api = "/MerlinWebService/GetProfiles";
		return HttpAccessUtils.getJson(token, api);
	}

	@Override
	public String getJsonEventsBySeries(TokenContainer token, String seriesString, Instant start, Instant end) throws IOException, HttpAccessException
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
