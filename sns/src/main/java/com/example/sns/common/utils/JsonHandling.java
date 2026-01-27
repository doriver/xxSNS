package com.example.sns.common.utils;

import tools.jackson.databind.ObjectMapper;

public class JsonHandling {

    public static String objToJsonString(Object obj) {
        ObjectMapper objectMapper = new ObjectMapper();
        // 객체를 JSON String으로 변환
        String jsonString = objectMapper.writeValueAsString(obj);
        return jsonString;
    }
}
