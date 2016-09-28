package com.check.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Administrator on 2016/6/30 0030.
 * 属性文件工具类
 */
public class PropsUtil {
    private static final Logger LOGGER= LoggerFactory.getLogger(PropsUtil.class);
    /**
     * 加载属性文件
     * */
    public static Properties loadProps(String fileName) {
        Properties properties = null;
        InputStream is = null;
        try {
            //把目标文件输入流输入
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if (is == null) {
                throw new FileNotFoundException(fileName + "文件找不到");
            }
            properties = new Properties();
            /**
             * 先通过输入流将目标文件读取到缓冲中，然后创建的文件对象通过加载缓冲，间接将实体文件填充入对象中
             * */
            properties.load(is);
        } catch (IOException ex) {
            LOGGER.error("加载配置文件失败", ex);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ex) {
                    LOGGER.error("关闭输入流失败", ex);
                }
            }
            return properties;
        }
    }
    /**
     * 获取字符型属性(默认值为空字符串)
     * */
    public static String getString(Properties properties, String key){
        return getString(properties,key,"");
    }
    /**
     * 获取字符串型属性（可指定默认值）
     * */
    public static String getString(Properties properties, String key, String defaultValue){
        String value=defaultValue;
        if(properties.containsKey(key)){
            value=properties.getProperty(key);
        }
        return value;
    }
    /**
     * 获取数值型属性(默认值为0)
     * */
    public static int getInt(Properties properties, String key){
        return getInt(properties,key,0);
    }
    /**
     * 获取数值型属性(可指定默认值)
     * */
    public static int getInt(Properties properties, String key, int defaultValue){
        int value=defaultValue;
        if(properties.containsKey(key)){
            value=CastUtil.castInt(properties.getProperty(key));
        }
        return value;
    }
    /**
     * 获取布尔型属性(默认值为false)
     * */
    public static boolean getBoolean(Properties properties, String key){
        return getBoolean(properties,key,false);
    }
    /**
     *获取布尔型属性(可指定默认值)
     *
     * */
    public static boolean getBoolean(Properties properties, String key, Boolean defaultValue){
        boolean value=defaultValue;
        if(properties.containsKey(key)){
            value=CastUtil.castBoolean(properties.getProperty(key));
        }
        return value;
    }
}
