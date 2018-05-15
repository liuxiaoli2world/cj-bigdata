package com.lgsc.cjbd.book.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lgsc.cjbd.book.model.Book;
import com.lgsc.cjbd.dao.BaseMapper;

public interface BookMapper extends BaseMapper<Book> {

	/**
	 * 条件查询图书
	 * 
	 * @param pageNum
	 *            页码
	 * @param pageSize
	 *            每页数量
	 * @param realname
	 *            作者姓名
	 * @param channelId
	 *            图书分类id
	 * @param parentMenuId
	 *            父菜单id
	 * @param childMenuId
	 *            子菜单id
	 * @param where
	 *            关键词使用位置	index=首页	list=列表
	 * @param keyword
	 *            关键词
	 * @param isRelease
	 *            是否发布 0=未发布 1=已发布
	 * @return
	 */
	List<Map<String, Object>> selectBy(@Param("realname") String realname, @Param("channelId") Long channelId, @Param("parentMenuId") Long parentMenuId,
			@Param("childMenuId") Long childMenuId, @Param("keyword") String keyword,
			@Param("isRelease") String isRelease);

	/**
	 * 查询一本图书
	 * @param bookId
	 * @return
	 */
	Map<String, Object> selectById(@Param("bookId")Long bookId);
}