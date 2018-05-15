package com.lgsc.cjbd.book.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lgsc.cjbd.book.mapper.MenuMapper;
import com.lgsc.cjbd.book.mapper.MenuSourceMapper;
import com.lgsc.cjbd.book.model.Menu;
import com.lgsc.cjbd.book.model.MenuSource;

/**
 * 菜单
 */
@Service
public class MenuService {

	@Autowired
	private MenuMapper menuMapper;

	@Autowired
	private MenuSourceMapper menuSourceMapper;

	public int insert(Menu record) {
		return menuMapper.insert(record);
	}

	/**
	 * 添加菜单
	 * 
	 * @param menu
	 * @return
	 */
	public int insertSelective(Menu menu) {
		Long parentMenuId = menu.getParentMenuId();
		if (parentMenuId == null) {
			menu.setParentMenuId((long) 0);
		} else if (parentMenuId != 0) {// 查询父菜单是否存在
			Menu parentMenu = menuMapper.selectByPrimaryKey(parentMenuId);
			if (parentMenu == null) {
				return 0;
			}
			menu.setImageUrl(parentMenu.getImageUrl());// 父菜单存在就将父菜单的图片存到子菜单中
		}
		Date date = new Date();
		menu.setCreatedAt(date);
		menu.setUpdatedAt(date);
		return menuMapper.insertSelective(menu);
	}

	public Menu selectByPrimaryKey(long id) {
		return menuMapper.selectByPrimaryKey(id);
	}

	/**
	 * 查询所有菜单
	 */
	public List<Menu> selectAll() {
		return menuMapper.selectAll();
	}

	public PageInfo<Menu> selectAll(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Menu> list = menuMapper.selectAll();
		PageInfo<Menu> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	public int updateByPrimaryKey(Menu record) {
		return menuMapper.updateByPrimaryKey(record);
	}

	public int updateByPrimaryKeySelective(Menu record) {
		return menuMapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 删除菜单
	 */
	@Transactional
	public String deleteByPrimaryKey(Long menuId) {
		Menu menu = menuMapper.selectByPrimaryKey(menuId);
		MenuSource menuSource = new MenuSource();
		if (menu != null) {
			Long parentMenuId = menu.getParentMenuId();
			if (parentMenuId == 0) { // 父目录
				List<Menu> childMenus = menuMapper.selectChildList(menuId);
				int num = 0;
				if (childMenus != null) {
					for (Menu menu2 : childMenus) {
						menuSource.setMenuId(menu2.getMenuId());
						num += menuSourceMapper.selectCount(menuSource);
					}
				}

				if (num > 0)
					return "该菜单下有资源,不能删除!";
				else {
					int sum = 0;
					if (childMenus != null) {
						for (Menu menu2 : childMenus)
							sum += menuMapper.deleteByPrimaryKey(menu2.getMenuId());
					}
					sum += menuMapper.deleteByPrimaryKey(menuId);
					if (sum > 0)
						return "删除成功!";
				}

			} else { // 子目录
				menuSource.setMenuId(menuId);
				if (menuSourceMapper.selectCount(menuSource) > 0)
					return "该分类下有资源,不能删除!";

				if (menuMapper.deleteByPrimaryKey(menuId) > 0)
					return "删除成功!";
			}
		}

		return "删除失败!";

	}

	/**
	 * 查询所有根菜单
	 */
	public List<Menu> selectRoot() {
		Menu menu = new Menu();
		menu.setParentMenuId(0L);
		return menuMapper.select(menu);
	}

	/**
	 * 根据菜单id查询祖先节点，不包括当前菜单
	 */
	public List<Menu> selectParentList(Long menuId) {
		return menuMapper.selectParentList(menuId);
	}

	/**
	 * 根据菜单id查询后代节点，不包括当前菜单
	 */
	public List<Menu> selectChildList(Long menuId) {
		return menuMapper.selectChildList(menuId);
	}

	/**
	 * 根据菜单id查询子菜单
	 */
	public List<Menu> selectChild(Long menuId) {
		return menuMapper.selectChild(menuId);
	}

}
