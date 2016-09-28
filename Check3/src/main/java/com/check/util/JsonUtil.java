package com.check.util;

/**
 * Created by Administrator on 2016/7/12 0012.
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Json 工具类
 *
 * @author 任时汉
 * @since 1.0.0
 * */
public final class JsonUtil {
    private static final Logger LOGGER= LoggerFactory.getLogger(JsonUtil.class);
    private static final ObjectMapper OBJECT_MAPPER=new ObjectMapper();
    /**
     * 将POJO转为JSON
     * */
    public static <T> String toJson(T obj){
        String json;
        try {
            json=OBJECT_MAPPER.writeValueAsString(obj);
        }catch (Exception ex){
            LOGGER.error("POJO转为JSON失败",ex);
            throw new RuntimeException(ex);
        }
        return json;
    }

    /**
     * 将JSON转为POJO
     * */
    public static <T> T fromJson(String json,Class<T> tClass){
        T pojo;
        try {
            pojo=OBJECT_MAPPER.readValue(json,tClass);
        }catch (Exception ex){
            LOGGER.error("JSON转换POJO失败",ex);
            throw new RuntimeException(ex);
        }
        return pojo;
    }
}
