package com.mallplus.generator.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mallplus.generator.mapper.SysGeneratorMapper;
import com.mallplus.generator.model.TableEntity;
import com.mallplus.generator.service.SysGeneratorService;
import com.mallplus.generator.utils.GenUtils;
import com.mallplus.common.model.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * @Author mall
 */
@Slf4j
@Service
public class SysGeneratorServiceImpl extends ServiceImpl<SysGeneratorMapper,TableEntity> implements SysGeneratorService {
    @Autowired
    private SysGeneratorMapper sysGeneratorMapper;

    @Override
    public IPage<TableEntity> queryList(IPage<TableEntity> page, Wrapper<TableEntity> var2) {
//        Page<Map<String, Object>> page = new Page<>(, MapUtils.getInteger(map, "pageSize"));
        return sysGeneratorMapper.queryList(page, var2);
    }

    @Override
    public Map<String, String> queryTable(String tableName) {
        return sysGeneratorMapper.queryTable(tableName);
    }

    @Override
    public List<Map<String, String>> queryColumns(String tableName) {
        return sysGeneratorMapper.queryColumns(tableName);
    }

    @Override
    public byte[] generatorCode(String[] tableNames) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (
                ZipOutputStream zip = new ZipOutputStream(outputStream)
        ) {
            for (String tableName : tableNames) {
                //查询表信息
                Map<String, String> table = queryTable(tableName);
                //查询列信息
                List<Map<String, String>> columns = queryColumns(tableName);
                //生成代码
                GenUtils.generatorCode(table, columns, zip);
            }
        } catch (IOException e) {
            log.error("generatorCode-error: ", e);
        }
        return outputStream.toByteArray();
    }
}
