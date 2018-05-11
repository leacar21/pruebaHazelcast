package com.despegar.example.hazelcast.context.scan;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.despegar.example.hazelcast.context.scan.serializers.DateJsonDeserializer;
import com.despegar.example.hazelcast.context.scan.serializers.DateJsonSerializer;
import com.despegar.example.hazelcast.context.scan.serializers.JodaDateTimeJsonDeserializer;
import com.despegar.example.hazelcast.context.scan.serializers.JodaDateTimeJsonSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;

@Configuration
@EnableWebMvc
@ComponentScan({"com.despegar.example.hazelcast"})
@PropertySource("classpath:/conf/repository_beta.properties")
public class ServiceContext implements WebMvcConfigurer{

	@Bean
	public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	//@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new StringHttpMessageConverter());
        converters.add(this.buildSnakeJackson2HttpMessageConverter());
    }
    
    protected MappingJackson2HttpMessageConverter buildSnakeJackson2HttpMessageConverter() {
        // Jackson Http Converter
        MappingJackson2HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(this.getModule());
        objectMapper.registerModule(new AfterburnerModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        jacksonConverter.setObjectMapper(objectMapper);
        return jacksonConverter;
    }
    
    protected SimpleModule getModule() {
        // Register custom serializers
        SimpleModule module = new SimpleModule("DefaultModule", new Version(0, 0, 1, null, null, null));

        // Joda DateTime
        module.addDeserializer(DateTime.class, new JodaDateTimeJsonDeserializer());
        module.addSerializer(DateTime.class, new JodaDateTimeJsonSerializer());

        // Java Date
        module.addDeserializer(Date.class, new DateJsonDeserializer());
        module.addSerializer(Date.class, new DateJsonSerializer());

        return module;
    }

}
