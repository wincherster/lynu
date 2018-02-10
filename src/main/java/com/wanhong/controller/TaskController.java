package com.wanhong.controller;


import com.wanhong.common.errorcode.BusinessCode;
import com.wanhong.domain.ResultJson;
import com.wanhong.domain.UserInfo;
import com.wanhong.domain.common.Page;
import com.wanhong.util.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 请填写类的描述
 *
 * @author wangmeng36
 * @date 2017-11-14 14:55
 */
@Controller
@RequestMapping("/function/task")
public class TaskController {
    //添加一个日志器
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @RequestMapping("/getPageData")
    @ResponseBody
    public ResultJson<Page<List<UserInfo>>> getPageData(String body, Long userId){
        UserInfo userInfo = new UserInfo();
        userInfo.setMsgCode("我是msgCode");
        userInfo.setNickName("jhahha");
        userInfo.setUserId(234738495345L);
        userInfo.setCreateDate(new Date());
        UserInfo userInfo1 = new UserInfo();
        UserInfo userInfo2 = new UserInfo();
        UserInfo userInfo3 = new UserInfo();
        UserInfo userInfo4 = new UserInfo();
        UserInfo userInfo5 = new UserInfo();
        UserInfo userInfo6 = new UserInfo();
        BeanUtil.copyProperties(userInfo,userInfo1);
        BeanUtil.copyProperties(userInfo,userInfo2);
        BeanUtil.copyProperties(userInfo,userInfo3);
        BeanUtil.copyProperties(userInfo,userInfo4);
        BeanUtil.copyProperties(userInfo,userInfo5);
        BeanUtil.copyProperties(userInfo,userInfo6);
        List<UserInfo> userInfos = new ArrayList<>();
        userInfos.add(userInfo);
        userInfos.add(userInfo1);
        userInfos.add(userInfo2);
        userInfos.add(userInfo3);
        userInfos.add(userInfo4);
        userInfos.add(userInfo5);
        userInfos.add(userInfo6);
        Page<List<UserInfo>> userInfoPage = new Page<>(userInfos);
        userInfoPage.setIndex(1);
        userInfoPage.setTotalItem(35);
        //返回一个index.jsp这个视图
        ResultJson<Page<List<UserInfo>>> resultJson = new ResultJson<>(BusinessCode.SUCCESS,userInfoPage);
        return resultJson;
    }
}
