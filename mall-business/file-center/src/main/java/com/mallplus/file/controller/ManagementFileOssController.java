package com.mallplus.file.controller;

import com.mallplus.common.utils.CommonResult;
import com.mallplus.file.utils.OssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 文件上传demo
 *
 * @author :  sdb
 * Create at:  2020/8/20 10:17
 * Copyright: 沈阳峰行科技版权所有
 * @version : 1.0
 */
@RestController
@RequestMapping("/aliyun/oss")
@Slf4j
public class ManagementFileOssController {


    @PostMapping("/upload")
    @ResponseBody
    public Object uploadFile(MultipartFile file) throws Exception {
        String filename = file.getOriginalFilename();
        String fileKey = OssUtil.uploadObjectByInputStream(file.getInputStream(),"management/",filename);
        String filePath = OssUtil.getFileUrl(fileKey,100);
        Map<String,Object> params = new HashMap<>();
        params.put("fileKey",fileKey);
        params.put("filename",filename);
        params.put("filePath",filePath);
        log.info("上传返回:{}",params.toString());
        return new CommonResult().success(params);
    }

    /**
     * 通过fileKey获取oss流数据并返回
     * @param map
     * @return
     */
    @RequestMapping("/getByteByOssFileKey")
    public byte[] getByteByOssFileKey(@RequestBody Map<String,String> map) throws Exception{
        return OssUtil.getFileByteByKey(map.get("ossFileKey"));
    }

    /**
     * 通过fileKey获取oss文件的url 测试接口
     * @param map
     * @return
     */
    @RequestMapping("/getUrlByOssFileKey")
    public Object getUrlByOssFileKey(@RequestBody Map<String,String> map){
        String url = OssUtil.getFileUrl(map.get("ossFileKey"),24);
        Map<String,Object> params = new HashMap<>();
        params.put("fileurl",url);
        return new CommonResult().success(params);
    }

}