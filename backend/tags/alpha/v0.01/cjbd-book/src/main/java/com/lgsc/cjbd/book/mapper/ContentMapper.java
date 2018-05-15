package com.lgsc.cjbd.book.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lgsc.cjbd.book.dto.HistoryDto;
import com.lgsc.cjbd.book.model.Content;
import com.lgsc.cjbd.dao.BaseMapper;

public interface ContentMapper extends BaseMapper<Content> {

	/**
	 * 分类查询内容
	 * 
	 * @param flag
	 * @return
	 */
	List<Content> selectAllForIndex(@Param("flag") String flag,@Param("keyword") String keyword);

	/**
	 * 插入内容返回主键
	 * 
	 * @param contenr
	 * @return
	 */
	int insertConent(Content record);

	/**
	 * 返回前点击量前八的咨询
	 * 
	 * @param contentType
	 * @return
	 */
	List<Content> selectByPvRank(@Param("contentType") String contentType);

	/**
	 * 条件查询
	 * 
	 * @param 内容类型
	 * @param 条件查询
	 * @return
	 */
	List<Content> selectNewsByConditon(@Param("contentType") String contentType, @Param("condition") String condition);

	/**
	 * 根据菜单id查询期刊
	 * 
	 * @param menuId
	 * @return
	 */
	List<Content> selectPeriodicalByMeauId(@Param("menuPId") Long menuPId, @Param("menuId") Long menuId,
			@Param("keyword") String keyword);

	/**
	 * 查询所有人文历史
	 * 
	 * @return
	 */
	List<HistoryDto> selectAllHistory();

	/**
	 * 根据作者查询期刊
	 * 
	 * @param author
	 * @return
	 */
	List<Map<String, Object>> selectPeriodicalByAuthor(String author);
    /**
     * 查询浏览量的总数  
     * @return
     */
	Integer getTotalPv();
    /**
     * 全文索引查询内容
     * @param keyword
     * @return
     */
	List<Content> selectFullText(@Param("keyword")String keyword);

	List<Content> selectForHotWords(@Param("title")String title,@Param("author")String author, @Param("keyword")String keyword,@Param("orderBy")Integer orderBy);
    /**
     * 获取实时文章总数
     * @return
     */
	Integer getTotalContent();

	/**
	 * 根据id查询内容详细，带菜单
	 * @param contentId
	 * @return
	 */
	Map<String, Object> selectById(@Param("contentId")Long contentId);
	
	/**
	 * 根据id查询内容详细，不带菜单
	 * @param contentId
	 * @return
	 */
	Map<String, Object> selectByIdWithoutMenu(@Param("contentId")Long contentId);
	
	List<Content> selectContentByCondition(@Param("contentType")String contentType, @Param("condition")String condition);

}