package com.despegar.example.hazelcast.context.scan.serializers;

import java.io.IOException;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class JodaDateTimeJsonDeserializer
    extends JsonDeserializer<DateTime> {

    private DateTimeFormatter formatterNoMillis;
    private DateTimeFormatter formatterWithMillis;


    public JodaDateTimeJsonDeserializer() {
        this.formatterNoMillis = ISODateTimeFormat.dateTimeNoMillis();
        this.formatterWithMillis = ISODateTimeFormat.dateTime();
    }

    @Override
    public DateTime deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.VALUE_STRING) {
            String str = jp.getText()
                .trim();
            if (str.length() == 0) { // [JACKSON-360]
                return null;
            }

            try {
                return this.formatterNoMillis.parseDateTime(str);
            } catch (IllegalArgumentException e) {
                try {
                    return this.formatterWithMillis.parseDateTime(str);
                } catch (IllegalArgumentException ex) {
                    throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid date format \'" + str
                        + "\'. A string \'yyyy-MM-ddThh:mm:ssZ\' or \'yyyy-MM-ddThh:mm:ss.SSSZ\' was expected.");
                }
            }
        } else {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid date format. A string \'yyyy-MM-ddThh:mm:ssZ\' was expected.");
        }
    }
}