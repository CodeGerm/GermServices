package org.cg.services.core.util;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

/**
 * A JsonSerializer that serializes {@link CGDateTime}
 * @author WZ
 *
 */
public class CGDateTimeSerializer extends JsonSerializer<CGDateTime> {

    @Override
    public void serialize(CGDateTime value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) 
    		throws IOException, JsonProcessingException {
    	jsonGenerator.writeString(value.toString());
    }
}
