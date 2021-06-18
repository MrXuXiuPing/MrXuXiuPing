package com.mallplus.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mallplus.common.model.SysPermission;
import com.mallplus.user.vo.MenuDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 菜单
 *
 * @author mall
 */
public interface SysMenuMapper extends BaseMapper<MenuDto> {

    List<SysPermission> listMenuByUserId(Long id);

    List<SysPermission> listPermissByUserId(Long roleId);

    List<MenuDto> selectListByRoles(@Param("roleIds") List<Long> roleIds);
}
