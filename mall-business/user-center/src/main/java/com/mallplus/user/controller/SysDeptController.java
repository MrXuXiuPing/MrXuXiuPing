package com.mallplus.user.controller;

import com.mallplus.user.service.ISysDeptService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xxp
 * 部门
 */
@Slf4j
@RestController
@Api(tags = "部门模块Api")
@RequestMapping("/sys/sysDept")
public class SysDeptController {
    @Autowired
    private ISysDeptService iSysDeptService;
}
