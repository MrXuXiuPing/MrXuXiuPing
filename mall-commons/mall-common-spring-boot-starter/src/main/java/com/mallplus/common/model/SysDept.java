package com.mallplus.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 部门管理
 * </p>
 *
 * @author zscat
 * @since 2019-05-21
 */
@Data
//@TableName("sys_dept")
public class SysDept implements Serializable {

    private static final long serialVersionUID = 1L;

//    @TableId(value = "id", type = IdType.AUTO)
    private Long id;



}
