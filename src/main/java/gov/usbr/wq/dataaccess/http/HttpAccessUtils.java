/*
 * Copyright {year} United States Bureau of Reclamation (USBR).
 * United States Department of the Interior
 * All Rights Reserved. USBR PROPRIETARY/CONFIDENTIAL.
 * Source may not be released without written approval
 * from USBR
 */

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
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.TimeUnit;
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

	private HttpAccessUtils()
	{
	}

	//can also be built using OkHttpClient.
	static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient.Builder()
			.callTimeout(Duration.ofSeconds(TimeUnit.MINUTES.toSeconds(1)))
			.connectTimeout(Duration.ofSeconds(TimeUnit.MINUTES.toSeconds(1)))
			.readTimeout(Duration.ofSeconds(TimeUnit.MINUTES.toSeconds(1)))
			.build();

	public static TokenContainer authenticate(String user, String pass) throws IOException
	{
		String apiUrl = WEB_SERVICE_ROOT + MERLIN_WEB_SERVICE_API_ACCOUNT_GENERATE_TOKEN;
		HttpUrl.Builder urlBuilder = HttpUrl.parse(apiUrl)
											.newBuilder();
		//params in url
		urlBuilder.addQueryParameter(USERNAME_QUERY_PARAM, user)
				  .addQueryParameter(PASSWORD_QUERY_PARAM, pass);
		String fullUrl = urlBuilder.build()
								   .toString();
		//empty post body
		FormBody body = new FormBody.Builder().build();
		Request request = new Request.Builder().url(fullUrl)
											   .post(body)
											   .build();
		String tokenString;
		try (Response response = OK_HTTP_CLIENT.newCall(request)
											   .execute())
		{
			tokenString = response.body()
								  .string();
		}
		//response token comes back quoted, strip quotes
		if (tokenString.indexOf('"') != -1)
		{
			tokenString = tokenString.replaceAll("\"", "");
		}
		LOGGER.info(tokenString);
		return new JwtContainer(tokenString);
	}

	static String getNoToken(String url) throws IOException
	{
		Request request = new Request.Builder().url(url)
											   .build();
		try (Response response = HttpAccessUtils.OK_HTTP_CLIENT.newCall(request)
															   .execute())
		{
			return response.body()
						   .string();
		}
	}

	static String postJsonNoToken(String url, String json) throws IOException
	{
		MediaType mediaType = MediaType.get("application/json; charset=utf-8");
		RequestBody body = RequestBody.create(mediaType, json);
		Request request = new Request.Builder().url(url)
											   .post(body)
											   .build();
		try (Response response = HttpAccessUtils.OK_HTTP_CLIENT.newCall(request)
															   .execute())
		{
			return response.body()
						   .string();
		}
	}

	static String getJson(TokenContainer token, String api) throws IOException, HttpAccessException
	{
		HttpUrl.Builder urlBuilder = HttpUrl.parse(WEB_SERVICE_ROOT + api)
											.newBuilder();
		urlBuilder.addQueryParameter("token", token.getToken());
		return getJson(urlBuilder);
	}

	private static String getJson(HttpUrl.Builder urlBuilder) throws IOException, HttpAccessException
	{
		String url = urlBuilder.build()
							   .toString();
		Request request = new Request.Builder().url(url)
											   .build();
		Call call = OK_HTTP_CLIENT.newCall(request);
		Response response = call.execute();
		if (response.code() == 200)
		{
			//success
			return response.body()
						   .string();
		}
		else
		{
			throw new HttpAccessException(response.code(), response.body()
																   .string());
		}
	}

	static String getJson(TokenContainer token, String api, Map<String, String> queryParams) throws IOException, HttpAccessException
	{
		HttpUrl.Builder urlBuilder = HttpUrl.parse(WEB_SERVICE_ROOT + api)
											.newBuilder();
		urlBuilder.addQueryParameter("token", token.getToken());
		queryParams.forEach(urlBuilder::addQueryParameter);
		return getJson(urlBuilder);
	}
}
