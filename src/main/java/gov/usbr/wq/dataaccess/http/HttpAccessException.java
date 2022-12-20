/*
 * Copyright 2022 United States Bureau of Reclamation (USBR).
 * United States Department of the Interior
 * All Rights Reserved. USBR PROPRIETARY/CONFIDENTIAL.
 * Source may not be released without written approval
 * from USBR.
 */

package gov.usbr.wq.dataaccess.http;

public class HttpAccessException extends Exception
{
	private final int _code;
	private final String _url;
	private final String _body;

	public HttpAccessException(int code, String url, String body)
	{
		super(constructMessage(code, url, body));
		_code = code;
		_url = url;
		_body = body;
	}

	public HttpAccessException(Exception ex)
	{
		super(ex);
		_code = 0;
		_url = null;
		_body = null;
	}

	private static String constructMessage(int code, String url, String body)
	{
		return "URL:" + url + System.lineSeparator() + "Code: " + code + System.lineSeparator() + "Body: " + body;
	}

	public int getCode()
	{
		return _code;
	}

	public String getUrl()
	{
		return _url;
	}

	public String getBody()
	{
		return _body;
	}
}
