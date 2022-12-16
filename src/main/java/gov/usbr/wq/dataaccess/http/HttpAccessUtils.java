/*
 * Copyright 2022 United States Bureau of Reclamation (USBR).
 * United States Department of the Interior
 * All Rights Reserved. USBR PROPRIETARY/CONFIDENTIAL.
 * Source may not be released without written approval
 * from USBR.
 */

package gov.usbr.wq.dataaccess.http;

import gov.usbr.wq.dataaccess.jwt.TokenContainer;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Ryan Miles
 */
public class HttpAccessUtils
{
	private static final Logger LOGGER = Logger.getLogger(HttpAccessUtils.class.getName());
	static final String WEB_SERVICE_ROOT = "https://www.grabdata2.com";
	static final String MERLIN_WEB_SERVICE_API_ACCOUNT_GENERATE_TOKEN = "/MerlinWebService/api/Account/GenerateToken";
	static final String USERNAME_QUERY_PARAM = "username";
	static final String PASSWORD_QUERY_PARAM = "password";

	private static final String CALL_TIMEOUT_PROPERTY_KEY = "cwms.http.client.calltimeout.seconds";
	private static final Duration CALL_TIMEOUT_PROPERTY_DEFAULT = Duration.ofSeconds(0);
	private static final String CONNECT_TIMEOUT_PROPERTY_KEY = "cwms.http.client.connecttimeout.seconds";
	private static final Duration CONNECT_TIMEOUT_PROPERTY_DEFAULT = Duration.ofSeconds(5);
	private static final String READ_TIMEOUT_PROPERTY_KEY = "cwms.http.client.readtimeout.seconds";
	private static final Duration READ_TIMEOUT_PROPERTY_DEFAULT = Duration.ofSeconds(TimeUnit.MINUTES.toSeconds(5));

	private HttpAccessUtils()
	{
	}

	//can also be built using OkHttpClient.
	static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient.Builder()
			.callTimeout(getCallTimeout())
			.connectTimeout(getConnectTimeout())
			.readTimeout(getReadTimeout())
			.build();

	public static TokenContainer authenticate(String user, String pass) throws HttpAccessException
	{
		String apiUrl = WEB_SERVICE_ROOT + MERLIN_WEB_SERVICE_API_ACCOUNT_GENERATE_TOKEN;
		HttpUrl.Builder urlBuilder = HttpUrl.parse(apiUrl).newBuilder();
		//params in url
		urlBuilder.addQueryParameter(USERNAME_QUERY_PARAM, user)
				  .addQueryParameter(PASSWORD_QUERY_PARAM, pass);
		String fullUrl = urlBuilder.build().toString();
		//empty post body
		FormBody body = new FormBody.Builder().build();
		Request request = new Request.Builder().url(fullUrl).post(body).build();

		String tokenString = getRequestBodyString(request);

		//response token comes back quoted, strip quotes
		if (tokenString.indexOf('"') != -1)
		{
			tokenString = tokenString.replaceAll("\"", "");
		}
		return new JwtContainer(tokenString);
	}

	static String getNoToken(String url) throws HttpAccessException
	{
		Request request = new Request.Builder().url(url).build();
		return getRequestBodyString(request);
	}

	static String postJsonNoToken(String url, String json) throws HttpAccessException
	{
		MediaType mediaType = MediaType.get("application/json; charset=utf-8");
		RequestBody body = RequestBody.create(mediaType, json);
		Request request = new Request.Builder().url(url).post(body).build();
		return getRequestBodyString(request);
	}

	static String getJson(TokenContainer token, String api) throws HttpAccessException
	{
		HttpUrl.Builder urlBuilder = HttpUrl.parse(WEB_SERVICE_ROOT + api).newBuilder();
		urlBuilder.addQueryParameter("token", token.getToken());
		return getJson(urlBuilder);
	}

	private static String getJson(HttpUrl.Builder urlBuilder) throws HttpAccessException
	{
		String url = urlBuilder.build().toString();
		Request request = new Request.Builder().url(url).build();
		return getRequestBodyString(request);
	}

	private static String getRequestBodyString(Request request) throws HttpAccessException
	{
		try(Response response = OK_HTTP_CLIENT.newCall(request).execute())
		{
			if (response.code() == 200)
			{
				//success
				return response.body().string();
			}
			else
			{
				throw new HttpAccessException(response.code(), response.body().string());
			}
		}
		catch (IOException ex)
		{
			throw new HttpAccessException(ex);
		}
	}

	static String getJson(TokenContainer token, String api, Map<String, String> queryParams) throws HttpAccessException
	{
		HttpUrl.Builder urlBuilder = HttpUrl.parse(WEB_SERVICE_ROOT + api).newBuilder();
		urlBuilder.addQueryParameter("token", token.getToken());
		queryParams.forEach(urlBuilder::addQueryParameter);
		return getJson(urlBuilder);
	}

	public static Access buildHttpAccess()
	{
		return new HttpAccess();
	}

	//This code below is copied from https://github.com/HydrologicEngineeringCenter/cwms-radar-client/blob/main/cwms-http-client/src/main/java/mil/army/usace/hec/cwms/http/client/OkHttpClientInstance.java
	//Once we move to the cwms-http-client this will not be necessary.
	private static Duration getReadTimeout()
	{
		return getDurationFromSystemProperty(READ_TIMEOUT_PROPERTY_KEY, READ_TIMEOUT_PROPERTY_DEFAULT);
	}

	private static Duration getConnectTimeout()
	{
		return getDurationFromSystemProperty(CONNECT_TIMEOUT_PROPERTY_KEY, CONNECT_TIMEOUT_PROPERTY_DEFAULT);
	}

	private static Duration getCallTimeout()
	{
		return getDurationFromSystemProperty(CALL_TIMEOUT_PROPERTY_KEY, CALL_TIMEOUT_PROPERTY_DEFAULT);
	}

	private static Duration getDurationFromSystemProperty(String readTimeoutPropertyKey, Duration readTimeoutPropertyDefault)
	{
		String readTimeoutPropertyValue = System.getProperty(readTimeoutPropertyKey);
		Duration readTimeout = readTimeoutPropertyDefault;
		if (readTimeoutPropertyValue == null)
		{
			LOGGER.log(Level.FINE, () -> "Setting " + readTimeoutPropertyKey + " is not set in system properties. Defaulting to " + readTimeoutPropertyDefault);
		}
		else
		{
			LOGGER.log(Level.FINE, () -> "Setting " + readTimeoutPropertyKey + " read from system properties as " + readTimeoutPropertyValue);
			readTimeout = Duration.parse(readTimeoutPropertyValue);
		}
		return readTimeout;
	}
}
