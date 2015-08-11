package com.github.aparnachaudhary.jaxrs.depot.core.registry.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;

/**
 * @author Aparna Chaudhary
 */
public final class PojoMapper {

    private static ObjectMapper m = new ObjectMapper();
    private static JsonFactory jf = new JsonFactory();

    private PojoMapper() {
    }

    public static <T> T fromJson(String jsonAsString, Class<T> pojoClass) throws IOException {
        return m.readValue(jsonAsString, pojoClass);
    }

    public static String toJson(Object pojo) throws IOException {
        StringWriter sw = new StringWriter();
        JsonGenerator jg = jf.createGenerator(sw);
        m.writeValue(jg, pojo);
        return sw.toString();
    }

}
