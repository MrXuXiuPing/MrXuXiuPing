package com.mallplus.generator.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mallplus.common.model.SysUser;
import com.mallplus.db.mapper.SuperMapper;
import com.mallplus.generator.model.TableEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Author mall
 */
@Component
@Mapper
public interface SysGeneratorMapper extends BaseMapper<TableEntity> {
    IPage<TableEntity> queryList(IPage<TableEntity> page,  TableEntity entity,@Param("tableName") String tableName);
//    IPage<TableEntity> queryList(IPage<TableEntity> page, @Param(Constants.WRAPPER) Wrapper<TableEntity> wrapper);

    int queryTotal(Map<String, Object> map);

    Map<String, String> queryTable(String tableName);

    List<Map<String, String>> queryColumns(String tableName);
}
