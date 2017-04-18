package com.ecn.exchange.serialize;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
	private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

	private JsonUtils() {

	}

	public static <T> T deserialize(final String json, final Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {
		final ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}

	public static String serialize(final Object o) {
		final ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(o);
		} catch (final JsonProcessingException e) {
			throw new JsonFormatException(e);
		}
	}
	
	
	public static <V> V jsonStringToObject(final String source, final Class<V> valueClass) throws JsonFormatException {
		V result = null;
		if (source == null) {
			result = null;
		} else {
			try {
				result = JSON_MAPPER.readValue(source, valueClass);
			} catch(final Exception e) {
				new JsonFormatException(e);
			}
		}
		return result;
	}
	
	public static <V> List<V> jsonStringToList(final String source, final Class<V> valueClass) throws JsonFormatException {
		List<V> result = null;
		if (source == null) {
			result = null;
		} else {
			try {
				result = JSON_MAPPER.readValue(source, JSON_MAPPER.getDeserializationConfig().getTypeFactory().constructCollectionType(ArrayList.class, valueClass));
			} catch(final Exception e) {
				throw new JsonFormatException(e);
			}
		}
		return result;
	}
}
