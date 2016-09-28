package com.check.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Administrator on 2016/6/30 0030.
 * 字符串工具
 */
public final class StringUtil {
    /***
     *
     * 判断字符串是否为空
     */
    public static final String SEPARATOR=String.valueOf((char) 29);
    public static boolean isEmpty(String string){
        if(string!=null){
            string=string.trim();
        }
        return StringUtils.isEmpty(string);
    }
    /**
     *
     * 判断字符串是否为非空
     * */
    public static boolean isNotEmpty(String string){
        return !isEmpty(string);
    }
    /**
     * 对字符串进行拆分
     * */
    public static String[] splitString(String string,String symbol){
        String[] strings=null;
        if(string!=null){
            strings=string.split(symbol);
        }
        return strings;
    }
}
