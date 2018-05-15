package com.lgsc.cjbd.book.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lgsc.cjbd.book.model.Menu;
import com.lgsc.cjbd.dao.BaseMapper;

public interface MenuMapper extends BaseMapper<Menu> {

	/**
	 * 根据菜单id查询祖先节点，不包括当前目录
	 */
	List<Menu> selectParentList(@Param("menuId") Long menuId);

	/**
	 * 根据菜单id查询后代节点，不包括当前目录
	 */
	List<Menu> selectChildList(@Param("menuId")Long menuId);

	/**
	 * 根据菜单id查询子菜单
	 */
	List<Menu> selectChild(@Param("menuId")Long menuId);
}