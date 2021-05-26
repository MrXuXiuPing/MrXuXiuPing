package com.mallplus.generator.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mallplus.common.model.PageResult;
import com.mallplus.common.model.SysUser;
import com.mallplus.generator.model.TableEntity;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;

/**
 * @Author mall
 */
@Service
public interface SysGeneratorService extends IService<TableEntity> {
     IPage<TableEntity> queryList(IPage<TableEntity> map, Wrapper<TableEntity> var2);

     Map<String, String> queryTable(String tableName);

     List<Map<String, String>> queryColumns(String tableName);

     byte[] generatorCode(String[] tableNames);
}
