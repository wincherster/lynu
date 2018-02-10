package com.wanhong.controller;

import com.wanhong.common.errorcode.BusinessCode;
import com.wanhong.common.spring.SpringContextUtil;
import com.wanhong.domain.ResultJson;
import com.wanhong.domain.UserInfo;
import com.wanhong.domain.common.Page;
import com.wanhong.util.BeanUtil;
import com.wanhong.util.VerifyCodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wangmeng
 * @date 2018-02-10 11:01
 */
@Controller
@RequestMapping("/function/login")
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    SpringContextUtil springContextUtil;

    @RequestMapping("/submit")
    @ResponseBody
    public ResultJson<UserInfo> submit(String body){
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(9999L);
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes)ra).getRequest();
        request.getSession().setAttribute("userInfo",userInfo);
        ResultJson<UserInfo> resultJson = new ResultJson<>(BusinessCode.SUCCESS,userInfo);
        return resultJson;
    }

    @RequestMapping("/getVerifyCode")
    public void getVerifyCode(HttpServletRequest request,HttpServletResponse response){
        response.setHeader("Pragma","No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        //生成随机字符串
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        //存入Session, 此处可以根据自己的需求
        HttpSession session = request.getSession();
        session.setAttribute("verifyCode",verifyCode);
        //生成图片
        int w = 100, h = 35;
        try {
            //将图片写入到 response 的输出流即可将图片返回到客户端了
            VerifyCodeUtils.outputImage(w, h , response.getOutputStream(), verifyCode);
        } catch (IOException e) {
            logger.error("生成验证码失败, Cause by: {}", e.getMessage(), e);
        }
    }

    @RequestMapping("/isLogin")
    @ResponseBody
    public ResultJson<Boolean> isLogin(HttpServletRequest request){
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
        if (userInfo == null){
            return new ResultJson<>(BusinessCode.NOT_LOG_IN);
        }
        return new ResultJson<>(BusinessCode.IS_LOG_IN,true);
    }
}
