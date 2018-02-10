package com.wanhong.util;

/**
 * Describe : Html util
 * User: wangmeng
 * Date: 2016-09-26
 * Time: 15:29
 */

import org.apache.commons.lang3.StringEscapeUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class HtmlUtil {

    /**
     * 向response响应json数据,同时设置ContentType="application/json"
     * @param response
     * @param object
     */
    public static void writerJson(HttpServletResponse response, Object object) {
        response.setContentType("application/json");
        writer(response, FastjsonUtil.objectToJson(object));
    }

    /**
     * 向response响应xml数据,同时设置ContentType="application/xml"
     * @param response
     * @param xmlStr
     */
    public static void writerXml(HttpServletResponse response, String xmlStr) {
        response.setContentType("application/xml");
        writer(response, xmlStr);
    }

    /*
     * 向response响应文本数据,同时设置ContentType="text/html"
     *
     * @param response
     *
     * @param text
     */
    public static void writerText(HttpServletResponse response, String text) {
        response.setContentType("text/html");
        writer(response,text);
    }

    private static void writer(HttpServletResponse response, String str) {
        try {
            // 设置页面不缓存
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = null;
            out = response.getWriter();
            out.print(str);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String escapeHtml4(String input){
        //对于{和[{开头的json字符串不进行处理
        if(input.startsWith("{") || input.startsWith("[{")){
            return input;
        }else{
            return StringEscapeUtils.escapeHtml4(input);
        }
    }

    public static String unescapeHtml4(String input){
        return StringEscapeUtils.unescapeHtml4(input);
    }

    /**
     * 从request中提取用户ip
     */
    public static String getRemoteIp(HttpServletRequest request) {
        if(request == null) {
            return null;
        } else {
            String ip = request.getHeader("x-forwarded-for");
            if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }

            if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }

            if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }

            if(StringUtil.isNotEmpty(ip) && ip.contains(",")) {
                ip = ip.substring(0, ip.indexOf(","));
            }

            return ip != null?ip.split(":")[0]:null;
        }
    }
}
