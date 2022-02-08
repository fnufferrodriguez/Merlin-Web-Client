package gov.usbr.wq.dataaccess.http;

import gov.usbr.wq.dataaccess.ResourceAccess;
import gov.usbr.wq.dataaccess.jwt.JwtContainer;
import gov.usbr.wq.dataaccess.jwt.TokenContainer;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class HttpAccessTest
{

	@Test
	void authenticate() throws IOException
	{
		String webServiceRoot = HttpAccess.getDefaultWebServiceRoot();
		String username= ResourceAccess.getUsername();
		String password= ResourceAccess.getPassword();
		TokenContainer token = new HttpAccess(webServiceRoot).authenticate(username,password);
		assertNotNull(token);
		assertTrue(token.isValid());
		assertTrue(token instanceof JwtContainer);
	}
}