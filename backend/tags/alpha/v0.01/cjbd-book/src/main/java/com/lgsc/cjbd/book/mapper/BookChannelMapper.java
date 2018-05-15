package com.lgsc.cjbd.book.mapper;

import org.apache.ibatis.annotations.Param;

import com.lgsc.cjbd.book.model.BookChannel;
import com.lgsc.cjbd.book.model.Channel;
import com.lgsc.cjbd.dao.BaseMapper;

public interface BookChannelMapper extends BaseMapper<BookChannel> {

	/**
	 * 根据isbn查询分类
	 * 
	 * @param isbn
	 * @return
	 */
	public Channel selectByIsbn(@Param("isbn") String isbn);
}