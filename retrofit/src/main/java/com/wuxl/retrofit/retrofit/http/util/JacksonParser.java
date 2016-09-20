package com.wuxl.retrofit.retrofit.http.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * json解析
 * Created by WUXL on 2016/8/17.
 */
public class JacksonParser {

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:MM:ss";

    private static JacksonParser jacksonParser = new JacksonParser();

    private JacksonParser() {
    }

    public static JacksonParser getInstance() {
        if (jacksonParser != null) {
            jacksonParser = new JacksonParser();
        }
        return jacksonParser;
    }

    public ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper().setDateFormat(new SimpleDateFormat(DATE_FORMAT));
        //空值转换为空对象
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        //配置为false表示mapper在遇到mapper对象中存在json对象中没有的数据变量时不报错，可以进行反序列化
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //解决转义字符-异常
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        return objectMapper;
    }

    public <T> T jsonToObject(String json, Class<T> object) {
        try {
            ObjectMapper objectMapper = JacksonParser.getInstance().getObjectMapper();
            T t = objectMapper.readValue(json, object);
            return t;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public <T> List<T> jsonToList(String json, Class<T> entityCls) {
        try {
            ObjectMapper objectMapper = JacksonParser.getInstance().getObjectMapper();
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, entityCls);
            List<T> t = objectMapper.readValue(json, javaType);
            return t;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public <V,T> Map<V,T> jsonToMap(String json, Class<V> keyCls,Class<T> valueCls) {
        try {
            ObjectMapper objectMapper = JacksonParser.getInstance().getObjectMapper();
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(HashMap.class,keyCls, valueCls);
            Map<V,T> t = objectMapper.readValue(json, javaType);
            return t;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String objectToJson(Object object){
        try {
            ObjectMapper objectMapper = JacksonParser.getInstance().getObjectMapper();
            String json=objectMapper.writeValueAsString(object);
            return json;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    };
}
