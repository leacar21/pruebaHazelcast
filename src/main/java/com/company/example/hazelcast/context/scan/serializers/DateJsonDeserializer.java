package com.company.example.hazelcast.context.scan.serializers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

public class DateJsonDeserializer
    extends JsonDeserializer<Date> {

    private TimeZone GMT = TimeZone.getTimeZone("GMT");
    private DateFormat formatter = new ISO8601DateFormat();

    public DateJsonDeserializer() {
        this.formatter.setTimeZone(this.GMT);
    }

    @Override
    public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.VALUE_STRING) {
            String str = jp.getText()
                .trim();
            if (str.length() == 0) { // [JACKSON-360]
                return null;
            }

            try {
                return this.formatter.parse(str);
            } catch (ParseException | IllegalArgumentException e) {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid date format \'" + str
                    + "\'. A string \'yyyy-MM-ddThh:mm:ssZ\' was expected.");
            }
        } else {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid date format. A string \'yyyy-MM-ddThh:mm:ssZ\' was expected.");
        }
    }
}
