package com.lgsc.cjbd.book.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lgsc.cjbd.book.model.Multiitem;
import com.lgsc.cjbd.dao.BaseMapper;

public interface MultiitemMapper extends BaseMapper<Multiitem> {
	/**
	 * 根据相册id查询图片
	 * 
	 * @param multifileId
	 * @return
	 */
	List<Map<String, Object>> selectPicsById(long multifileId);

	/**
	 * 根据多媒体id查询所有多媒体项目
	 * 
	 * @param multifileId
	 * @return
	 */
	List<Multiitem> selectByMultifileId(@Param("multifileId") Long multifileId);

	/**
	 * 查询最大rank值，以便新增item
	 * @return
	 */
	int selectMaxRank();

	/**
	 * 查询图片、音频、视频数量
	 * @return
	 */
	Map<String, Object> selectSum();
	
	/**
	 * 根据multiitemId查询点击量
	 * @param multiitemId
	 * @return
	 */
	int selectPv(long multiitemId);
	
	/**
	 * 查询首页视频列表
	 * @param multifileId
	 * @return
	 */
	List<Map<String, Object>> selectIndexVideoList(@Param("multifileId") Long multifileId);

	/**
	 * 根据相册id和图片排序值查找该图片
	 * 
	 * @param multifileId
	 * @param rank
	 * @return
	 */
	// Multiitem selectPicByMultifileIdAndRank(@Param("multifileId") long
	// multifileId, @Param("rank") Integer rank);
}