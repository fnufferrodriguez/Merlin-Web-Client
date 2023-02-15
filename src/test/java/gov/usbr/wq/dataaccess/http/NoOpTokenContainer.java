/*
 * Copyright 2022 United States Bureau of Reclamation (USBR).
 * United States Department of the Interior
 * All Rights Reserved. USBR PROPRIETARY/CONFIDENTIAL.
 * Source may not be released without written approval
 * from USBR.
 */

package gov.usbr.wq.dataaccess.http;

import gov.usbr.wq.dataaccess.http.TokenContainer;

/**
 * Created by Ryan Miles
 */
public final class NoOpTokenContainer implements TokenContainer
{
	private final boolean _valid;
	private final String _token;

	public NoOpTokenContainer()
	{
		this(true, "");
	}

	public NoOpTokenContainer(boolean valid, String token)
	{
		_valid = valid;
		_token = token;
	}

	@Override
	public boolean isValid()
	{
		return _valid;
	}

	@Override
	public String getToken()
	{
		return _token;
	}

	@Override
	public boolean isExpired()
	{
		return false;
	}
}
