package com.lgsc.cjbd.book.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lgsc.cjbd.book.model.MenuSource;
import com.lgsc.cjbd.dao.BaseMapper;

public interface MenuSourceMapper extends BaseMapper<MenuSource> {

	/**
	 * 更改菜单资源中引用的menu_id为null
	 */
	public void updateMenuIdNull(Long menuId);

	/**
	 * 根据sourceId更新，类别在MenuSource中
	 * 
	 * @param ms
	 */
	public void updateBySourceId(MenuSource ms);

	/**
	 * 通过sourceId删除,类别在MenuSource中
	 * 
	 * @param ms
	 * @return
	 */
	public int deleteBySourceId(MenuSource ms);

	public List<Map<String, Object>> selectSearch(@Param("keyword") String keyword, @Param("scope") String scope);

	/**
	 * 查询菜单及子菜单下是否有资源
	 * @param menuId
	 * @return
	 */
	public String selectHasContent(@Param("menuId")Long menuId);

}