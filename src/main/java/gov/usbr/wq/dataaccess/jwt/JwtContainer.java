/*
 * Copyright 2022 United States Bureau of Reclamation (USBR).
 * United States Department of the Interior
 * All Rights Reserved. USBR PROPRIETARY/CONFIDENTIAL.
 * Source may not be released without written approval
 * from USBR.
 */

package gov.usbr.wq.dataaccess.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class JwtContainer implements TokenContainer
{
	private static final Logger LOGGER = Logger.getLogger(JwtContainer.class.getName());
	private final String _token;

	public JwtContainer(String token)
	{
		_token = token;
	}

	@Override
	public String getToken()
	{
		return _token;
	}

	@Override
	public boolean isValid()
	{
		boolean retval = false;
		try
		{
			retval = JWT.decode(_token) != null;
		}
		catch(JWTDecodeException e)
		{
			LOGGER.log(Level.SEVERE,e,() -> "Invalid token, "+_token);
		}
		return retval;
	}
}
