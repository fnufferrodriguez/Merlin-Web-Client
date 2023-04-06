/*
 * Copyright 2022 United States Bureau of Reclamation (USBR).
 * United States Department of the Interior
 * All Rights Reserved. USBR PROPRIETARY/CONFIDENTIAL.
 * Source may not be released without written approval
 * from USBR.
 */

package gov.usbr.wq.dataaccess.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import gov.usbr.wq.dataaccess.json.Template;
import gov.usbr.wq.dataaccess.mapper.MerlinObjectMapper;

import java.io.IOException;
import java.util.Objects;

/**
 * This class is intended to act as a buffer between the code-genned profile and the
 * Created by Ryan Miles
 */
public final class TemplateWrapper
{
	@JsonUnwrapped
	private final Template _template;

	public TemplateWrapper(Template profile)
	{
		_template = profile;
	}

	@JsonIgnore
	public Integer getDprId()
	{
		return _template.getDprID();
	}

	@JsonIgnore
	public String getName()
	{
		return _template.getDprName();
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
		TemplateWrapper that = (TemplateWrapper) o;
		return _template.equals(that._template);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(_template);
	}

	@Override
	public String toString()
	{
		return _template.toString();
	}

	public String toJsonString() throws IOException
	{
		return MerlinObjectMapper.mapObjectToJson(_template);
	}

}
