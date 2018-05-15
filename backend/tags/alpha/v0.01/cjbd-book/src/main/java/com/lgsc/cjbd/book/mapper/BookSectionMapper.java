package com.lgsc.cjbd.book.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lgsc.cjbd.book.dto.BookSectionDTO;
import com.lgsc.cjbd.book.model.BookSection;
import com.lgsc.cjbd.dao.BaseMapper;

public interface BookSectionMapper extends BaseMapper<BookSection> {
	
	/**
	 * 根据图书isbn查询所有章节
	 * @param isbn
	 * @return
	 */
	List<BookSection> selectByIsbn(String isbn);
	
	/**
	 * 根据ISBN和bookDirId查询章节
	 * 
	 * @param isbn
	 * @param bookDirId
	 * @return
	 */
	BookSectionDTO selectByBookDirId(@Param("isbn") String isbn, @Param("bookDirId") Long bookDirId);
	
	/**
	 * 根据id查找根章节 
	 * @param bookSectionId
	 * @return
	 */
	BookSection selectTry(@Param("bookSectionId") Long bookSectionId);
	
	/**
	 * 根据bookDirId查询同级节点
	 * @param bookDirId
	 * @return
	 */
	List<BookSection> selectSiblingList(@Param("bookDirId") Long bookDirId);
	
	/**
	 * 查询章节总数
	 * @param isbn
	 * @return
	 */
	int selectCountByIsbn(@Param("isbn") String isbn);
	
}