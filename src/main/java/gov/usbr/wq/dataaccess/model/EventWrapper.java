/*
 * Copyright 2022 United States Bureau of Reclamation (USBR).
 * United States Department of the Interior
 * All Rights Reserved. USBR PROPRIETARY/CONFIDENTIAL.
 * Source may not be released without written approval
 * from USBR.
 */

package gov.usbr.wq.dataaccess.model;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Objects;

import gov.usbr.wq.dataaccess.json.Event;
import gov.usbr.wq.dataaccess.mapper.MerlinObjectMapper;

/**
 * Created by Ryan Miles
 */
public final class EventWrapper implements Comparable<EventWrapper>
{
	private final Event _event;
	private final ZonedDateTime _date;

	public EventWrapper(Event event)
	{
		_event = event;

		//This ZonedDateTime conversion might be different later, but we want to return ZonedDateTimes
		_date = _event.getDate().toZonedDateTime();
	}

	public Double getValue()
	{
		return _event.getValue();
	}

	public ZonedDateTime getDate()
	{
		return _date;
	}

	public Integer getQuality()
	{
		return _event.getQuality();
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
		EventWrapper that = (EventWrapper) o;
		return _event.equals(that._event);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(_event);
	}

	@Override
	public int compareTo(EventWrapper o)
	{
		if (_date != null && o.getDate() != null)
		{
			return _date.compareTo(o.getDate());
		}

		//Move nulls to the end of the list
		return 1;
	}

	@Override
	public String toString()
	{
		return _event.toString();
	}

	public String toJsonString() throws IOException
	{
		return MerlinObjectMapper.mapObjectToJson(_event);
	}


}
