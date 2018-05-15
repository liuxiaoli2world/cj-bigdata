package com.lgsc.cjbd.book.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.lgsc.cjbd.book.mapper.MenuSourceMapper;
import com.lgsc.cjbd.book.model.MenuSource;

/**
 * 菜单资源
 */
@Service
public class MenuSourceService {
	
	@Autowired
	private MenuSourceMapper menuSourceMapper;
	
	public int insert(MenuSource record) {
		return menuSourceMapper.insert(record);
	}

	/**
	 * 添加菜单资源
	 * @param menuSource
	 * @return
	 */
	public int insertSelective(MenuSource menuSource) {
		return menuSourceMapper.insertSelective(menuSource);
	}

	public MenuSource selectByPrimaryKey(long id) {
		return menuSourceMapper.selectByPrimaryKey(id);
	}
    
    public List<MenuSource> selectAll() {
		return menuSourceMapper.selectAll();
	}
	
	public PageInfo<MenuSource> selectAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<MenuSource> list = menuSourceMapper.selectAll();
		PageInfo<MenuSource> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	
	public int updateByPrimaryKey(MenuSource record) {
		return menuSourceMapper.updateByPrimaryKey(record);
	}

	public int updateByPrimaryKeySelective(MenuSource record) {
		return menuSourceMapper.updateByPrimaryKeySelective(record);
	}

	public int deleteByPrimaryKey(long id) {
		return menuSourceMapper.deleteByPrimaryKey(id);
	}
	
	public PageInfo<Map<String, Object>> selectSearch(Integer pageNum, Integer pageSize, String keyword, String scope) {
		PageHelper.startPage(pageNum, pageSize);
		List<Map<String, Object>> list = menuSourceMapper.selectSearch(keyword, scope);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	/**
	 * 查询菜单及子菜单下是否有资源
	 * @param menuId
	 * @return
	 */
	public String selectHasContent(Long menuId) {
		return menuSourceMapper.selectHasContent(menuId);
	}

}
