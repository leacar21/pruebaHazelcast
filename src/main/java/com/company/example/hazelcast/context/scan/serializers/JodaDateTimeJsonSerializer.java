package com.company.example.hazelcast.context.scan.serializers;

import java.io.IOException;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class JodaDateTimeJsonSerializer
    extends JsonSerializer<DateTime> {

    private DateTimeFormatter formatter;
    private DateTimeZone GMT = DateTimeZone.forTimeZone(TimeZone.getTimeZone("GMT"));

    public JodaDateTimeJsonSerializer() {
        this.formatter = ISODateTimeFormat.dateTimeNoMillis();
    }

    @Override
    public void serialize(DateTime value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        value = value.withZone(this.GMT);
        jgen.writeString(this.formatter.print(value));
    }

}

