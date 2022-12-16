/*
 * Copyright 2022 United States Bureau of Reclamation (USBR).
 * United States Department of the Interior
 * All Rights Reserved. USBR PROPRIETARY/CONFIDENTIAL.
 * Source may not be released without written approval
 * from USBR.
 */

package gov.usbr.wq.dataaccess.model;

import gov.usbr.wq.dataaccess.json.Data;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableSet;
import java.util.TimeZone;
import java.util.TreeSet;
import java.util.logging.Logger;

import static java.util.stream.Collectors.toList;

/**
 * Created by Ryan Miles
 */
public final class DataWrapper
{
	private static final Logger LOGGER = Logger.getLogger(DataWrapper.class.getName());

	private final Data _data;
	private final NavigableSet<EventWrapper> _events = new TreeSet<>();
	private final ZoneId _zoneId;

	public DataWrapper(Data data)
	{
		_data = data;
		ZoneId zoneId = ZoneId.of("UTC");
		if (data.getTimeZone() != null)
		{
			try
			{
				zoneId = ZoneId.of(data.getTimeZone());
			}
			catch (DateTimeException ex)
			{
				//Not sure what to do, for now stick with UTC
				TimeZone tz = TimeZone.getTimeZone(data.getTimeZone());
				LOGGER.fine(() -> "Converting " + data.getTimeZone() + " to " + tz);
				zoneId = tz.toZoneId();
			}
		}
		_zoneId = zoneId;

		List<EventWrapper> events = new ArrayList<>();
		if (data.getEvents() != null)
		{
			events = data.getEvents().stream()
				.map(EventWrapper::new)
				.collect(toList());
		}

		_events.addAll(events);
	}

	public NavigableSet<EventWrapper> getEvents()
	{
		return new TreeSet<>(_events);
	}

	public ZonedDateTime getStartTime()
	{
		ZonedDateTime output = LocalDateTime.MIN.atZone(getTimeZone());

		if (!_events.isEmpty())
		{
			output = _events.first().getDate();
		}

		return output;
	}

	public ZonedDateTime getEndTime()
	{
		ZonedDateTime output = LocalDateTime.MAX.atZone(getTimeZone());

		if (!_events.isEmpty())
		{
			output = _events.last().getDate();
		}

		return output;
	}

	public String getSeriesId()
	{
		return _data.getSeriesString();
	}

	public String getUnits()
	{
		return _data.getUnits();
	}

	public ZoneId getTimeZone()
	{
		return _zoneId;
	}

	public String getDuration()
	{
		return _data.getDuration();
	}

	public String getParameter()
	{
		return _data.getParameter();
	}

	public String getProject()
	{
		return _data.getProject();
	}

	public String getMeasurement()
	{
		return _data.getMeasurement();
	}

	public String getTimestep()
	{
		return _data.getTimestep();
	}

	public String getLatitude()
	{
		return _data.getLatitude();
	}

	public String getLongitude()
	{
		return _data.getLongitude();
	}

	public String getStation()
	{
		return _data.getStation();
	}

	public String getSensor()
	{
		return _data.getSensor();
	}

	@Override
	public String toString()
	{
		return _data.toString();
	}

	public String getDataType()
	{
		return _data.getDataType();
	}
}
