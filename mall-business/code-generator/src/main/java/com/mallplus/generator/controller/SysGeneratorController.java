package com.mallplus.generator.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mallplus.common.model.PageResult;
import com.mallplus.common.model.SysUser;
import com.mallplus.common.utils.CommonResult;
import com.mallplus.generator.model.TableEntity;
import com.mallplus.generator.service.SysGeneratorService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;

/**
 * @Author: mall
 */
@Slf4j
@RestController
@Api(tags = "代码生成器")
@RequestMapping("/generator")
public class SysGeneratorController {
    @Autowired
    private SysGeneratorService sysGeneratorService;

    /**
     * 列表
     */
    @ResponseBody
    @GetMapping("/list")
    public Object getTableList(TableEntity tableEntity,
                               @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                               @RequestParam(value = "tableName", defaultValue = "") String tableName) {
        try {
//            log.info("tableEntity:{}",tableEntity);
//            QueryWrapper<TableEntity> tableEntityQueryWrapper = new QueryWrapper<>();
//            new QueryWrapper<>(tableEntity)
//                    .eq("table_schema","(select database())")
//                    .like("table_name",tableEntity.getTableName())
//                    .orderByDesc("create_time");

            return new CommonResult().success(sysGeneratorService.queryList(
                    new Page<TableEntity>(pageNum, pageSize),tableEntity, tableName));

        } catch (Exception e) {
            log.error("根据条件查询所有用户列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    /**
     * 生成代码FileUtil
     */
    @GetMapping("/code")
    public void makeCode(String tables, HttpServletResponse response) throws IOException {
        byte[] data = sysGeneratorService.generatorCode(tables.split(","));
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"generator.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }
}
