package com.mallplus.user.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mallplus.common.model.SysPermission;
import com.mallplus.common.model.SysRole;
import com.mallplus.common.utils.ExcelUtil;
import com.mallplus.common.vo.Tree;
import com.mallplus.user.mapper.SysMenuMapper;
import com.mallplus.user.mapper.SysRolePermissionMapper;
import com.mallplus.user.model.BuildTree;
import com.mallplus.user.model.SysPermissionNode;
import com.mallplus.user.service.ISysMenuService;
import com.mallplus.user.vo.MenuDto;
import com.mallplus.user.vo.MenuMetaVo;
import com.mallplus.user.vo.MenuVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 作者 owen E-mail: 624191343@qq.com
 */
@Slf4j
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, MenuDto> implements ISysMenuService {
 	@Resource
	private SysRolePermissionMapper sysRoleMenuMapper;

 	@Resource
	private SysMenuMapper menuMapper;

	@Override
	public List<MenuDto> findByRoles(List<SysRole> roles) {
		List<Long> roleIds = roles.stream().map(i ->{
			Long role = i.getId();
			return role;
		}).collect(Collectors.toList());
		List<MenuDto> list = menuMapper.selectListByRoles(roleIds);
		return list;
	}
	/**
	 * 构建菜单树
	 *
	 * @param menuDtos 原始数据
	 * @return /
	 */
	@Override
	public Map<String, Object> buildTree(List<MenuDto> menuDtos) {
		List<MenuDto> trees = new ArrayList<>();
		Set<Long> ids = new HashSet<>();
		for (MenuDto menuDto : menuDtos) {
			if (menuDto.getPid() == 0) {
				trees.add(menuDto);
			}
			for (MenuDto it : menuDtos) {
				if (it.getPid().equals(menuDto.getId())) {
					if (menuDto.getChildren() == null) {
						menuDto.setChildren(new ArrayList<>());
					}
					menuDto.getChildren().add(it);
					ids.add(it.getId());
				}
			}
		}
		Map<String,Object> map = new HashMap<>(2);
		if(trees.size() == 0){
			trees = menuDtos.stream().filter(s -> !ids.contains(s.getId())).collect(Collectors.toList());
		}
		map.put("content",trees);
		map.put("totalElements", menuDtos.size());
		return map;
	}


	/**
	 * 构建菜单树
	 *
	 * @param menuDtos /
	 * @return /
	 */
	@Override
	public List<MenuVo> buildMenus(List<MenuDto> menuDtos) {
		List<MenuVo> list = new LinkedList<>();
		menuDtos.forEach(menuDTO -> {
					if (menuDTO!=null){
						List<MenuDto> menuDtoList = menuDTO.getChildren();
						MenuVo menuVo = new MenuVo();
						menuVo.setName(ObjectUtil.isNotEmpty(menuDTO.getComponentName())  ? menuDTO.getComponentName() : menuDTO.getName());
						// 一级目录需要加斜杠，不然会报警告
						menuVo.setPath(menuDTO.getPid() == 0 ? "/" + menuDTO.getPath() :menuDTO.getPath());
						menuVo.setHidden(menuDTO.getHidden());
						// 如果不是外链
						if(!menuDTO.getIFrame()){
							if(menuDTO.getPid() == 0){
								menuVo.setComponent(StrUtil.isEmpty(menuDTO.getComponent())?"Layout":menuDTO.getComponent());
							}else if(!StrUtil.isEmpty(menuDTO.getComponent())){
								menuVo.setComponent(menuDTO.getComponent());
							}
						}
						menuVo.setMeta(new MenuMetaVo(menuDTO.getName(),menuDTO.getIcon(),!menuDTO.getCache()));
						if(menuDtoList !=null && menuDtoList.size()!=0){
							menuVo.setAlwaysShow(true);
							menuVo.setRedirect("noredirect");
							menuVo.setChildren(buildMenus(menuDtoList));
							// 处理是一级菜单并且没有子菜单的情况
						} else if(menuDTO.getPid() == 0){
							MenuVo menuVo1 = new MenuVo();
							menuVo1.setMeta(menuVo.getMeta());
							// 非外链
							if(!menuDTO.getIFrame()){
								menuVo1.setPath("index");
								menuVo1.setName(menuVo.getName());
								menuVo1.setComponent(menuVo.getComponent());
							} else {
								menuVo1.setPath(menuDTO.getPath());
							}
							menuVo.setName(null);
							menuVo.setMeta(null);
							menuVo.setComponent("Layout");
							List<MenuVo> list1 = new ArrayList<>();
							list1.add(menuVo1);
							menuVo.setChildren(list1);
						}
						list.add(menuVo);
					}
				}
		);
		return list;
	}





	@Override
	public int updateShowStatus(List<Long> ids, Integer showStatus) {
//		SysPermission productCategory = new SysPermission();
//		productCategory.setStatus(showStatus);
//		return baseMapper.update(productCategory, new QueryWrapper<SysPermission>().in("id",ids));
return 0;
	}

	@Override
	public List<Tree<SysPermission>> getPermissionsByUserId(Long id) {
		List<Tree<SysPermission>> trees = new ArrayList<Tree<SysPermission>>();
		//  List<SysPermission> menuDOs = permissionMapper.listMenuByUserId(id);
		List<SysPermission> menuDOs = baseMapper.listMenuByUserId(id);
		for (SysPermission sysMenuDO : menuDOs) {
			Tree<SysPermission> tree = new Tree<SysPermission>();
			tree.setId(sysMenuDO.getId().toString());
			tree.setParentId(sysMenuDO.getPid().toString());
			tree.setTitle(sysMenuDO.getName());
			Map<String, Object> attributes = new HashMap<>(16);
			attributes.put("url", sysMenuDO.getUri());
			attributes.put("icon", sysMenuDO.getIcon());
			tree.setMeta(attributes);
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		List<Tree<SysPermission>> list = BuildTree.buildList(trees, "0");
		return list;
	}
	/**
	 * 将权限转换为带有子级的权限对象
	 * 当找不到子级权限的时候map操作不会再递归调用covert
	 */
	private SysPermissionNode covert(SysPermission permission, List<SysPermission> permissionList) {
		SysPermissionNode node = new SysPermissionNode();
		BeanUtils.copyProperties(permission, node);
		List<SysPermissionNode> children = permissionList.stream()
				.filter(subPermission -> subPermission.getPid().equals(permission.getId()))
				.map(subPermission -> covert(subPermission, permissionList)).collect(Collectors.toList());
		node.setChildren(children);
		return node;
	}
	@Override
	public List<SysPermissionNode> treeList() {
//		List<SysPermission> permissionList = baseMapper.selectList(new QueryWrapper<SysPermission>().orderByAsc("sort"));
//		List<SysPermissionNode> result = permissionList.stream()
//				.filter(permission -> permission.getPid().equals(0L))
//				.map(permission -> covert(permission, permissionList)).collect(Collectors.toList());
//		return result;
		return null;
	}




    /**
     * 查询所有菜单
     */
	@Override
	public List<SysPermission> findAll() {
//		return baseMapper.selectList(
//                new QueryWrapper<SysPermission>().orderByAsc("sort")
//        );
		return null;
	}

    /**
     * 查询所有一级菜单
     */
	@Override
	public List<SysPermission> findOnes() {
//        return baseMapper.selectList(
//                new QueryWrapper<SysPermission>()
//                        .eq("type", 1)
//                        .orderByAsc("sort")
//        );
        return null;
	}
}
