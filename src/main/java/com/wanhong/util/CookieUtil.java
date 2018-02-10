package com.wanhong.util;

import org.apache.http.cookie.ClientCookie;
import org.apache.http.impl.cookie.BasicClientCookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

/**
 * cookie操作类
 */
public class CookieUtil {
  /**
   * 根据key获取value的值
   */
  public static String getCookieValue(HttpServletRequest request, String name) {
    String value = null;
    if (name != null) {
      Cookie[] oCookies = request.getCookies();
      if (oCookies != null) {
        for (Cookie oItem : oCookies) {
          String sName = oItem.getName();
          if (name.equals(sName)) {
            value = oItem.getValue();
            break;
          }
        }
      }
    }
    return value;
  }

  /**
   * 获取cookie
   *
   * note: uri不建议传空，否则cookie可能传递不到后台服务
   */
  public static Collection<ClientCookie> getCookies(HttpServletRequest request, URI uri) {
    Collection<ClientCookie> clientCookie = new ArrayList<ClientCookie>();
    Cookie[] oCookies = request.getCookies();
    if (oCookies != null) {
      String host = (uri == null ? null : uri.getHost());
      for (final Cookie oItem : oCookies) {
        String name = oItem.getName();
        String value = oItem.getValue();
        BasicClientCookie cc = new BasicClientCookie(name, value);
        cc.setDomain(host);
        clientCookie.add(cc);
      }
    }
    return clientCookie;
  }
}
