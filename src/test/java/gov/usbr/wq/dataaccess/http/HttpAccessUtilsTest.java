/*
 * Copyright 2022 United States Bureau of Reclamation (USBR).
 * United States Department of the Interior
 * All Rights Reserved. USBR PROPRIETARY/CONFIDENTIAL.
 * Source may not be released without written approval
 * from USBR.
 */

package gov.usbr.wq.dataaccess.http;

import gov.usbr.wq.dataaccess.ResourceAccess;
import gov.usbr.wq.dataaccess.jwt.TokenContainer;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class HttpAccessUtilsTest
{
	@Test
	void testAuthenticate() throws HttpAccessException
	{
		String password = ResourceAccess.getPassword();
		String username = ResourceAccess.getUsername();
		TokenContainer token = HttpAccessUtils.authenticate(username, password);
		assertTrue(token instanceof JwtContainer);
		assertTrue(token.isValid());
		assertNotNull(token.getToken());
	}
}