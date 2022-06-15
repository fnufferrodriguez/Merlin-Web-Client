/*
 * Copyright 2021  Hydrologic Engineering Center (HEC).
 * United States Army Corps of Engineers
 * All Rights Reserved.  HEC PROPRIETARY/CONFIDENTIAL.
 * Source may not be released without written approval
 * from HEC
 */

package gov.usbr.wq.dataaccess.model;

import gov.usbr.wq.dataaccess.json.Measure;

import java.util.Objects;

/**
 * Created by Ryan Miles
 */
public final class MeasureWrapper
{
	private final Measure _measure;

	public MeasureWrapper(Measure measure)
	{
		_measure = measure;
	}

	public String getSeriesString()
	{
		return _measure.getSeriesString();
	}

	public String getTypeId()
	{
		return _measure.getTypID();
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
		MeasureWrapper that = (MeasureWrapper) o;
		return _measure.equals(that._measure);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(_measure);
	}

	@Override
	public String toString()
	{
		return _measure.toString();
	}
}
