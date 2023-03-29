/*
 * Copyright 2022 United States Bureau of Reclamation (USBR).
 * United States Department of the Interior
 * All Rights Reserved. USBR PROPRIETARY/CONFIDENTIAL.
 * Source may not be released without written approval
 * from USBR.
 */

package gov.usbr.wq.dataaccess.mapper;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public final class MerlinObjectMapper
{
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

	static
	{
		OBJECT_MAPPER.findAndRegisterModules();
		SimpleModule simpleModule = new SimpleModule();
		simpleModule.addSerializer(OffsetDateTime.class, new JsonSerializer<OffsetDateTime>()
		{
			@Override
			public void serialize(OffsetDateTime offsetDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
					throws IOException,
						   JsonProcessingException
			{
				jsonGenerator.writeString(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(offsetDateTime));
			}
		});
		OBJECT_MAPPER.registerModule(simpleModule);
		OBJECT_MAPPER.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
		OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public static <T> List<T> mapJsonToListOfObjectsUsingClass(String json, Class<T> classObject) throws IOException
	{
		return OBJECT_MAPPER.readValue(json, OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, classObject));
	}

	public static <T> T mapJsonToObjectUsingClass(String json, Class<T> classObject) throws IOException
	{
		return OBJECT_MAPPER.readValue(json, classObject);
	}

	public static <T> String mapObjectToJson(T object) throws IOException
	{
		return OBJECT_MAPPER.writeValueAsString(object);
	}
}
