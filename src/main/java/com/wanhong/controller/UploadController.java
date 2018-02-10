package com.wanhong.controller;

import com.wanhong.util.FastjsonUtil;
import com.wanhong.util.StringUtil;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangmeng247
 * @date 2018-02-10 13:56
 */
@Controller
@RequestMapping("/function/upload")
public class UploadController {
    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);
    @RequestMapping(value = "/uploadFile")
    @ResponseBody
    public String uploadCServicePic(MultipartFile uploadFile, HttpServletRequest request){
        Map<String, Object> ret = new HashMap<>();
        try {
            String originalFilename = uploadFile.getOriginalFilename();
            String uploadrxPicContentType = uploadFile.getContentType();
            long len = uploadFile.getSize();

            logger.info("上传处方原始文件原名：{}", originalFilename);
            logger.info("上传处方原始文件类型：{}", uploadrxPicContentType);
            logger.info("上传的文件大小：{}",len);
            if (uploadFile == null || uploadFile.isEmpty() || StringUtil.hasBlank(originalFilename,uploadrxPicContentType) ) {
                ret.put("success", false);
                ret.put("desc", "获取文件数据出错,确认是否选中文件");
            } else if (len > 1024 * 1024 * 1024) {
                ret.put("success", false);
                ret.put("desc", "大小超出限制，不能超过5MB");
            } else {
                String realPath = request.getSession().getServletContext().getRealPath("/upload");
                File picFile =new File(realPath,originalFilename);
                FileUtils.copyInputStreamToFile(uploadFile.getInputStream(),picFile);
                ret.put("desc", "好像是成功了。");
            }
        } catch (Exception e) {
            logger.error("保存上传的图片失败", e);
            ret.put("success", false);
            ret.put("desc", "保存上传的图片失败");
        }
        String json= FastjsonUtil.objectToJson(ret);
        logger.info("客服上传处方信息："+json);
        return json;
    }

    @ExceptionHandler(Exception.class)
    public @ResponseBody String ExceptionHandler(Exception exceededException) {
        Map<String, Object> jsonResult = new HashMap<>();
        if ((exceededException.getCause() instanceof MaxUploadSizeExceededException)) {
            jsonResult.put("code","0003");
            jsonResult.put("msg","文件过大");
        }else {
            jsonResult.put("code","0000");
        }
        String str = FastjsonUtil.objectToJson(jsonResult);
        return str;
    }
}
