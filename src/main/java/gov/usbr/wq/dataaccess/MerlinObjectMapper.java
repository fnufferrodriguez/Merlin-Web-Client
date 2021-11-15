package gov.usbr.wq.dataaccess;

import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import gov.usbr.wq.dataaccess.model.Profiles;

import java.io.IOException;
import java.util.List;

public class MerlinObjectMapper
{
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	static {
		OBJECT_MAPPER.findAndRegisterModules();
		OBJECT_MAPPER.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
	}


	public static <T> T mapJsonToObjectUsingTypeReference(String json, TypeReference<T> classObject) throws IOException
	{
		return OBJECT_MAPPER.readValue(json, classObject);
	}

	public static <T> T mapJsonToObjectUsingClass(String json, Class<T> classObject) throws IOException
	{
		return OBJECT_MAPPER.readValue(json, classObject);
	}

}
