package com.lgsc.cjbd.book.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lgsc.cjbd.book.model.Multifile;
import com.lgsc.cjbd.dao.BaseMapper;

public interface MultifileMapper extends BaseMapper<Multifile> {
	/**
	 * 根据菜单id查询相册
	 * 
	 * @param menuPId
	 * @param menuId
	 * @return
	 */
	List<Map<String, Object>> selectPicByMeauId(@Param("menuPId") Long menuPId, @Param("menuId") Long menuId);

	/**
	 * 根据多媒体id查询多媒体标题
	 * 
	 * @param multifileId
	 * @return
	 */
	String selectTitleById(long multifileId);

	/**
	 * 条件查询多媒体
	 * 
	 * @param parentMenuId
	 *            父菜单id
	 * @param childMenuId
	 *            子菜单id
	 * @param keyword
	 *            关键字（标题）
	 * @param multiType
	 *            资源类型
	 * @return
	 */
	List<Map<String, Object>> selectBy(@Param("parentMenuId") Long parentMenuId, @Param("childMenuId") Long childMenuId,
			@Param("keyword") String keyword, @Param("multiType") String multiType);

	/**
	 * 根据相册id查询相册
	 * 
	 * @param multifileId
	 * @return
	 */
	Map<String, Object> selectPicByMultifileId(long multifileId);

	/**
	 * 查询首页视频热点
	 * @return
	 */
	Map<String, Object> selectIndexVideo();
	

	Map<String, Object> selectById(@Param("multifileId") Long multifileId);

	void setOtherSceneZero();

}
