package com.wanhong.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.List;

/**
 * Describe : fastjson util
 * User: wangmeng
 * Date: 2016-06-13
 * Time: 15:52
 */
public class FastjsonUtil {

    private static String dateFormat = "yyyy-MM-dd HH:mm:ss";

    public static <T> T jsonToObject(String json, Class<T> clazz) {
        return JSON.parseObject(json,clazz);
    }

    public static <T> List<T> jsonToArray(String json, Class<T> clazz) {
        return JSON.parseArray(json,clazz);
    }

    public static <T> String objectToJson(T t) {
        //添加默认日期格式，禁用循环检测
        return JSON.toJSONStringWithDateFormat(t, dateFormat, SerializerFeature.DisableCircularReferenceDetect);
        //return JSON.toJSONString(t);
    }
}
