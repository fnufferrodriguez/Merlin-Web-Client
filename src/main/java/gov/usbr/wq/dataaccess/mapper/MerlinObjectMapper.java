/*
 * Copyright 2022 United States Bureau of Reclamation (USBR).
 * United States Department of the Interior
 * All Rights Reserved. USBR PROPRIETARY/CONFIDENTIAL.
 * Source may not be released without written approval
 * from USBR.
 */

package gov.usbr.wq.dataaccess.mapper;

import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.util.List;

public final class MerlinObjectMapper
{
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

	static
	{
		OBJECT_MAPPER.findAndRegisterModules();
		OBJECT_MAPPER.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
	}

	public static <T> List<T> mapJsonToListOfObjectsUsingClass(String json, Class<T> classObject) throws IOException
	{
		return OBJECT_MAPPER.readValue(json, OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, classObject));
	}

	public static <T> T mapJsonToObjectUsingClass(String json, Class<T> classObject) throws IOException
	{
		return OBJECT_MAPPER.readValue(json, classObject);
	}

}
