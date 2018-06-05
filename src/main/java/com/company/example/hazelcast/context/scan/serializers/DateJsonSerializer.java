package com.company.example.hazelcast.context.scan.serializers;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;


public class DateJsonSerializer
    extends JsonSerializer<Date> {

    private TimeZone GMT = TimeZone.getTimeZone("GMT");
    private DateFormat formatter = new ISO8601DateFormat();

    public DateJsonSerializer() {
        this.formatter.setTimeZone(this.GMT);
    }

    @Override
    public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeString(this.formatter.format(value));
    }


}
