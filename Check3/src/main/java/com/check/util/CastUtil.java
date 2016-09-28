package com.check.util;

/**
 * Created by Administrator on 2016/6/30 0030.
 * 转型操作工具类
 */
public class CastUtil {
    /**
     * 转为String型
     * */
    public static String castString(Object object){
        return CastUtil.castString(object,"");
    }
    /**
     * 转为String型(提供默认值)
     * */
    public static String castString(Object obj, String defaultValue){
        return obj!=null? String.valueOf(obj):defaultValue;
    }
    /**
     * 转为double型
     * */
    public static double castDouble(Object object){
        return CastUtil.castDouble(object,0);
    }
    /**
     * 转为double型(提供默认值)
     * */
    public static double castDouble(Object obj, double defaultValue){
        double doubleValue=defaultValue;
        if(obj!=null){
            String strValue=castString(obj);
            if(StringUtil.isNotEmpty(strValue)){
                try{
                    doubleValue= Double.parseDouble(strValue);
                }catch (NumberFormatException ex){
                    doubleValue=defaultValue;
                }
            }
        }
        return doubleValue;
    }
    /**
     * 转为long型
     * */
    public static long castLong(Object object){
        return castLong(object,0);
    }
    /**
     * 转为long型(提供默认值)
     * */
    public static long castLong(Object object, long defaultValue){
        long longValue=defaultValue;
        if(object!=null){
            String strValue=castString(object);
            if(StringUtil.isNotEmpty(strValue)){
                try{
                    longValue= Long.parseLong(strValue);
                }catch (NumberFormatException ex){
                    longValue=defaultValue;
                }
            }
        }
        return longValue;
    }
    /**
     * 转为int型(提供默认值)
     * */
    public static int castInt(Object object, int defaultValue){
        int intValue=defaultValue;
        if(object!=null){
            String strValue=castString(object);
            if(StringUtil.isNotEmpty(strValue)){
                try {
                    intValue= Integer.parseInt(strValue);
                }catch (NumberFormatException ex){
                    intValue=defaultValue;
                }
            }
        }
        return intValue;
    }
    /**
     * 转为int型
     * */
    public static  int castInt(Object object){
        return castInt(object,0);
    }
    /***
     * 转为boolean型,提供默认值
     */
    public static boolean castBoolean(Object object, boolean defaultValue){
        boolean booleanValue=defaultValue;
        if(object!=null){
            booleanValue= Boolean.parseBoolean(castString(object));
        }
        return booleanValue;
    }
    /**
     * 转为boolean
     * */
    public static boolean castBoolean(Object object){
        return CastUtil.castBoolean(object,false);
    }
}
