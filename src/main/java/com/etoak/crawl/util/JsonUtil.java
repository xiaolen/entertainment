package com.etoak.crawl.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class JsonUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    private JsonUtil() {
        throw new IllegalAccessError("Utility class");
    }

    public static <T> T parse(String content, Class<T> t) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(content, t);
        } catch (IOException e) {
            LOGGER.error("content:" + content + e.getMessage(), e);
        }
        return null;
    }

    public static String toString(Object t) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return "";
    }

    public static <T> List<T> toList(List<String> str) {

        return null;
    }

    public static <T> List<T> toList(String str) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(str, new TypeReference<List<T>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map toMap(String str) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(str, new TypeReference<Map>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List parseList(String str) {
        List list = parse(str, List.class);
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        return list;
    }


}