package gov.usbr.wq.dataaccess.http;

public final class HttpAccessException extends Exception
{
	private final int _code;
	private final String _body;

	public HttpAccessException(int code, String body)
	{
		_code = code;
		_body = body;
	}

	public int code()
	{
		return _code;
	}
	public String body()
	{
		return _body;
	}

	@Override
	public String getMessage()
	{
		return "Code: "+code()+". Body: "+body();
	}
}
