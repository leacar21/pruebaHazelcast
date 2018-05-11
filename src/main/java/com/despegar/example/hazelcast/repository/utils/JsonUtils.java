package com.despegar.example.hazelcast.repository.utils;

import java.io.Serializable;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JsonUtils
    implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtils.class);

    @Resource
    private ObjectMapper objectMapperCamelCase;

    public String toJson(Object object) {
        try {
            return this.objectMapperCamelCase.writeValueAsString(object);
        } catch (Exception e) {
            LOGGER.error("Error al serializar la clase={} : {}", object.getClass(), object);
            return null;
        }
    }

    public <T> T toObject(String json, Class<T> clazz) {
        try {
            T object = this.objectMapperCamelCase.readValue(json, clazz);
            return object;
        } catch (Exception e) {
            LOGGER.error("Error al deserializar el json {} a la clase={} : {} {}", json, clazz, e);
            return null;
        }
    }

}
