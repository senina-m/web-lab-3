package ru.senina.itmo.web.web_lab_3.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ru.senina.itmo.web.web_lab_3.exceptions.ParsingException;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class JsonParser<T> implements Serializable {
    private static ObjectMapper objectMapper;
    private final Class<T> classT;

    public JsonParser( ObjectMapper objectMapper, Class<T> classT) {
        JsonParser.objectMapper = objectMapper;
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
        objectMapper.setDateFormat(df);
        this.classT = classT;
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public String fromObjectToString(T object) throws ParsingException {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new ParsingException("Something wrong with object while parsing in JsonParser<" + classT.toString() + ">.");
        }
    }

    public T fromStringToObject(String json) throws ParsingException {
        try {
            return objectMapper.readValue(json, classT);
        } catch (ParsingException | JsonProcessingException e) {
            e.printStackTrace();
            throw new ParsingException("Something wrong with string while parsing in JsonParser<" + classT.toString() + ">.");
        }
    }
}

