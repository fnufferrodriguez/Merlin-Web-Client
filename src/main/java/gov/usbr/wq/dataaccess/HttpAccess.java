package gov.usbr.wq.dataaccess;

import okhttp3.*;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public final class HttpAccess
{
	private static final Logger LOGGER = Logger.getLogger(HttpAccess.class.getName());

	//can also be built using OkHttpClient.
	private static OkHttpClient _okHttpClient = new OkHttpClient.Builder()
		.callTimeout(Duration.ofSeconds(TimeUnit.MINUTES.toSeconds(1)))
		.connectTimeout(Duration.ofSeconds(TimeUnit.MINUTES.toSeconds(1)))
		.readTimeout(Duration.ofSeconds(TimeUnit.MINUTES.toSeconds(1)))
		.build();
	private final String _webServiceRoot;
	private static final String _merlintokengenerationapi =  "/MerlinWebService/api/Account/GenerateToken";

	private static OkHttpClient getOkHttpClient()
	{
		return _okHttpClient;
	}

	static String getDefaultWebServiceRoot()
	{
		return "https://www.grabdata2.com";
	}

	public HttpAccess(String webServiceRoot)
	{
		_webServiceRoot = webServiceRoot;
	}

	public JwtContainer authenticate(String user, String pass) throws IOException
	{
		String apiUrl = _webServiceRoot + _merlintokengenerationapi;
		HttpUrl.Builder urlBuilder = HttpUrl.parse(apiUrl).newBuilder();
		//params in url
		urlBuilder.addQueryParameter("username",user).addQueryParameter("password",pass);
		String fullUrl = urlBuilder.build().toString();
		//empty post body
		FormBody body = new FormBody.Builder().build();
		Request request = new Request.Builder().url(fullUrl).post(body).build();
		OkHttpClient client = HttpAccess.getOkHttpClient();
		String tokenString;
		try (Response response = client.newCall(request).execute())
		{
			tokenString = response.body().string();
		}
		//response token comes back quoted, strip quotes
		if (tokenString.indexOf('"') != -1)
		{
			tokenString = tokenString.replaceAll("\"","");
		}
		LOGGER.info(tokenString);
		return new JwtContainer(tokenString);
	}

//	public DecodedJWT authenticate(String user, String pass) throws IOException
//	{
//		String webServiceRoot = "https://www.grabdata2.com";
//		String merlinTokenGenerationApi = "/MerlinWebService/api/Account/GenerateToken";
//
//		StringBuilder sb = new StringBuilder(webServiceRoot).append(merlinTokenGenerationApi).append("?username=").append(user).append("&password=").append(pass);
//		String url = sb.toString();
//		String tokenString = post(url, "");
//		if (tokenString.indexOf('"') != -1)
//		{
//			tokenString = tokenString.replaceAll("\"","");
//		}
//		LOGGER.info(tokenString);
//		DecodedJWT jwt = JWT.decode(tokenString);
//		return jwt;
//	}

//	private String post(String url, String json) throws IOException
//	{
//		MediaType JSON = MediaType.get("application/json; charset=utf-8");
//		OkHttpClient client = OkHttpAccess.getOkHttpClient();
//		RequestBody body = RequestBody.create(JSON, json);
//		Request request = new Request.Builder().url(url).post(body).build();
//		try (Response response = client.newCall(request).execute())
//		{
//			return response.body().string();
//		}
//	}

//	private String post(Request request) throws IOException
//	{
//		OkHttpClient client = HttpAccess.getOkHttpClient();
//		try (Response response = client.newCall(request).execute())
//		{
//			return response.body().string();
//		}
//	}
//
//	private String post(String url, String json) throws IOException
//	{
//		MediaType JSON = MediaType.get("application/json; charset=utf-8");
//		OkHttpClient client = HttpAccess.getOkHttpClient();
//		RequestBody body =  RequestBody.create(JSON, json);
//		Request request = new Request.Builder().url(url).post(body).build();
//		try (Response response = client.newCall(request).execute())
//		{
//			return response.body().string();
//		}
//	}

	private String get(String url) throws IOException
	{
		Request request = new Request.Builder().url(url).build();
		OkHttpClient client = getOkHttpClient();
		try (Response response = client.newCall(request).execute())
		{
			return response.body().string();
		}
	}

//	public void getAsynch(String url)
//	{
//		Request request = new Request.Builder()
//			.url(BASE_URL + "/date")
//			.build();
//
//		Call call = client.newCall(request);
//		call.enqueue(new Callback() {
//			public void onResponse(Call call, Response response)
//				throws IOException {
//				// ...
//			}
//
//			public void onFailure(Call call, IOException e) {
//				fail();
//			}
//		});
//	}



	private String post(String url, String json) throws IOException
	{
		MediaType JSON = MediaType.get("application/json; charset=utf-8");
		OkHttpClient client = HttpAccess.getOkHttpClient();
		RequestBody body = RequestBody.create(JSON, json);
		Request request = new Request.Builder().url(url).post(body).build();
		try (Response response = client.newCall(request).execute())
		{
			return response.body().string();
		}
	}

	public String get(String api, JwtContainer token) throws IOException
	{
		OkHttpClient client = HttpAccess.getOkHttpClient();
		HttpUrl.Builder urlBuilder = HttpUrl.parse(getDefaultWebServiceRoot() + api).newBuilder();
		urlBuilder.addQueryParameter("token", token.getToken());
		String url = urlBuilder.build().toString();
		Request request = new Request.Builder().url(url).build();
		Call call = client.newCall(request);
		Response response = call.execute();
		return response.body().string();
	}

	public String get(String api, JwtContainer token, Map<String,String> queryParams) throws IOException
	{
		OkHttpClient client = HttpAccess.getOkHttpClient();
		final HttpUrl.Builder urlBuilder = HttpUrl.parse(getDefaultWebServiceRoot() + api).newBuilder();
		urlBuilder.addQueryParameter("token", token.getToken());
		queryParams.forEach(urlBuilder::addQueryParameter);
		String url = urlBuilder.build().toString();
		Request request = new Request.Builder().url(url).build();
		Call call = client.newCall(request);
		Response response = call.execute();
		return response.body().string();
	}
}
