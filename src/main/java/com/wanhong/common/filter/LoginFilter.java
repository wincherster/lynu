package com.wanhong.common.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author wangmeng247
 * @date 2018-02-07 20:12
 */
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
//        request.g

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        System.out.println("----调用service之前执行一段代码----");
        filterChain.doFilter(request, response); // 执行目标资源，放行
        System.out.println("----调用service之后执行一段代码----");
    }

    @Override
    public void destroy() {

    }
}
