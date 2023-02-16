package gov.usbr.wq.dataaccess;

import gov.usbr.wq.dataaccess.http.ApiConnectionInfo;
import gov.usbr.wq.dataaccess.http.HttpAccessException;
import gov.usbr.wq.dataaccess.http.HttpAccessUtils;
import gov.usbr.wq.dataaccess.http.TokenContainer;

public class TokenUtil
{
	private TokenUtil()
	{
		throw new AssertionError("Utility class, don't instantiate");
	}

	public static TokenContainer getToken() throws HttpAccessException
	{
		String username = ResourceAccess.getUsername();
		char[] password = ResourceAccess.getPassword();
		ApiConnectionInfo connectionInfo = new ApiConnectionInfo("https://www.grabdata2.com");
		TokenContainer token = HttpAccessUtils.authenticate(connectionInfo, username, password);
		return token;
	}
}