package com.wanhong.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Describe : String Util
 * User: wangmeng
 * Date: 2016-09-27
 * Time: 20:30
 */
public class StringUtil extends StringUtils {

    /**
     * 检测传入的可变数组是否有空字符串
     * @param args 多个字符参数
     * @return 有空字符串返回true
     */
    public static boolean hasBlank(String... args){
        boolean ret = false;
        for(String str:args){
            if(StringUtils.isBlank(str)){
                return true;
            }
        }
        return ret;
    }

    /**
     * 从开头截取字符串
     * @param str  待处理字符串
     * @param len  截取长度
     * @return 截取后的字符串
     */
    public static String getLimitLenStr(String str, int len){
        return StringUtil.substring(str,0,len);
    }

}
