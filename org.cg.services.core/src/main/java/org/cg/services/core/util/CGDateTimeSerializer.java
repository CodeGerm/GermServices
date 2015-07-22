package org.cg.services.core.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * A JsonSerializer that serializes {@link CGDateTime}
 * @author WZ
 *
 */
public class CGDateTimeSerializer extends JsonSerializer<CGDateTime> {

    @Override
    public void serialize(CGDateTime value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) 
    		throws IOException, JsonProcessingException {
    	if (value.isFormated()) {
    		jsonGenerator.writeString(value.getAsString());
    	} else {
    		jsonGenerator.writeNumber(value.getAsLong());
    	}
    }
}
