/*
 * Copyright 2022 United States Bureau of Reclamation (USBR).
 * United States Department of the Interior
 * All Rights Reserved. USBR PROPRIETARY/CONFIDENTIAL.
 * Source may not be released without written approval
 * from USBR.
 */

package gov.usbr.wq.dataaccess.model;

import gov.usbr.wq.dataaccess.json.Profile;

import java.util.Objects;

/**
 * This class is intended to act as a buffer between the code-genned profile and the
 * Created by Ryan Miles
 */
public final class ProfileWrapper
{
	private final Profile _profile;

	public ProfileWrapper(Profile profile)
	{
		_profile = profile;
	}

	public Integer getDprId()
	{
		return _profile.getDprID();
	}

	public String getName()
	{
		return _profile.getDprName();
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null || getClass() != o.getClass())
		{
			return false;
		}
		ProfileWrapper that = (ProfileWrapper) o;
		return _profile.equals(that._profile);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(_profile);
	}

	@Override
	public String toString()
	{
		return _profile.toString();
	}
}
