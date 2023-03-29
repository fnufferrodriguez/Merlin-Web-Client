/*
 * Copyright 2022 United States Bureau of Reclamation (USBR).
 * United States Department of the Interior
 * All Rights Reserved. USBR PROPRIETARY/CONFIDENTIAL.
 * Source may not be released without written approval
 * from USBR.
 */

package gov.usbr.wq.dataaccess.model;

import gov.usbr.wq.dataaccess.json.Measure;
import gov.usbr.wq.dataaccess.mapper.MerlinObjectMapper;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
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

	public Boolean isProcessed()
	{
		return _measure.isIsProcessed();
	}

	public ZonedDateTime getStart()
	{
		ZonedDateTime retVal = null;
		OffsetDateTime start = _measure.getStart();
		if(start != null)
		{
			retVal = start.toZonedDateTime();
		}
		return retVal;
	}

	public ZonedDateTime getEnd()
	{
		ZonedDateTime retVal = null;
		OffsetDateTime end = _measure.getEnd();
		if(end != null)
		{
			retVal = end.toZonedDateTime();
		}
		return retVal;
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

	public String getProjectAndSiteAndSensor()
	{
		return getSeriesString().split("/")[0];
	}
	public String getParameter()
	{
		return getSeriesString().split("/")[1];
	}

	public String getSite()
	{
		return getSeriesString().split("/")[1];
	}

	public String getType()
	{
		return getSeriesString().split("/")[2];
	}

	public String getTimeStep()
	{
		return getSeriesString().split("/")[3];
	}

	public String toJsonString() throws IOException
	{
		return MerlinObjectMapper.mapObjectToJson(_measure);
	}



}
