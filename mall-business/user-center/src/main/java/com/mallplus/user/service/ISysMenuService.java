package com.mallplus.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mallplus.common.model.SysPermission;
import com.mallplus.common.model.SysRole;
import com.mallplus.common.vo.Tree;
import com.mallplus.user.model.SysPermissionNode;
import com.mallplus.user.vo.MenuDto;
import com.mallplus.user.vo.MenuVo;

import java.util.List;
import java.util.Map;

/**
 * @author mall
 */
public interface ISysMenuService extends IService<MenuDto> {
	/**
	 * 构建菜单树
	 * @param menuDtos 原始数据
	 * @return /
	 */
	Map<String,Object> buildTree(List<MenuDto> menuDtos);
	/**
	 * 构建菜单树
	 * @param menuDtos /
	 * @return /
	 */
	List<MenuVo> buildMenus(List<MenuDto> menuDtos);

	List<Tree<SysPermission>> getPermissionsByUserId(Long id);

	List<SysPermissionNode> treeList();

	int updateShowStatus(List<Long> ids, Integer showStatus);
	/**
	 * 查询所有菜单
	 */
	List<SysPermission> findAll();

	/**
	 * 查询所有一级菜单
	 */
	List<SysPermission> findOnes();


	List<MenuDto> findByRoles(List<SysRole> rolesByUserId);
}
