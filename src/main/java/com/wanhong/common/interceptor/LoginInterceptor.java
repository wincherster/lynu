package com.wanhong.common.interceptor;

import com.wanhong.common.errorcode.BusinessCode;
import com.wanhong.common.web.UrlBuilder;
import com.wanhong.common.web.UrlBuilder.Builder;
import com.wanhong.domain.ResultJson;
import com.wanhong.domain.UserInfo;
import com.wanhong.util.FastjsonUtil;
import com.wanhong.util.HtmlUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author wangmeng247
 * @date 2018-02-05 10:19
 */
public class LoginInterceptor implements HandlerInterceptor {
    private String excludePath;
    private List<String> excludePathCache;
    private String appHomeUrl;
    private UrlBuilder appHomeUrlBuilder;
    private UrlBuilder loginUrlBuilder;
    private String loginUrl = "http://localhost:8080";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
            if (isExclude(request)){
            return true;
        }else{

            UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
            if (userInfo == null){
                if (request.getRequestURI().startsWith("/function/upload/uploadFile")){
                    String str = FastjsonUtil.objectToJson(new ResultJson<>(BusinessCode.NOT_LOG_IN));
                    HtmlUtil.writerText(response,str);
                }else{
                    HtmlUtil.writerJson(response,new ResultJson<>(BusinessCode.NOT_LOG_IN));
                }
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {

    }

    public boolean isExclude(HttpServletRequest request) {
        return this.isExclude(request.getRequestURI());
    }

    public boolean isExclude(String uri) {
        if (this.excludePathCache != null && !this.excludePathCache.isEmpty()) {
            Iterator i$ = this.excludePathCache.iterator();

            String path;
            do {
                if (!i$.hasNext()) {
                    return false;
                }

                path = (String)i$.next();
            } while(!uri.startsWith(path));

            return true;
        } else {
            return false;
        }
    }

    public void setExcludePath(String excludePath) {
        this.excludePath = excludePath;
        if (StringUtils.isNotBlank(excludePath)) {
            this.excludePathCache = new ArrayList();
            String[] path = excludePath.split(",");
            String[] arr$ = path;
            int len$ = path.length;
            for(int i$ = 0; i$ < len$; ++i$) {
                String p = arr$[i$];
                this.excludePathCache.add(p.trim());
            }
        }

    }



    public void toLoginPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if ("XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))) {
            response.setStatus(401);
            response.setHeader("Location", this.getLoginUrl(request));
        } else {
            response.sendRedirect(this.getLoginUrl(request));
        }

    }

    protected String getLoginUrl(HttpServletRequest request) throws MalformedURLException {
        Builder currentUrlBuilder = null;
        if (this.appHomeUrlBuilder != null) {
            currentUrlBuilder = this.appHomeUrlBuilder.forPath(request.getRequestURI());
        } else {
            currentUrlBuilder = (new UrlBuilder(request.getRequestURL().toString(), "ISO8859-1", true)).forPath((String)null);
        }

        currentUrlBuilder.put(request.getParameterMap());
        Builder loginUrlBuilder = this.loginUrlBuilder.forPath((String)null);
        loginUrlBuilder.put("ReturnUrl", currentUrlBuilder.build());
        return loginUrlBuilder.build();
    }
}
