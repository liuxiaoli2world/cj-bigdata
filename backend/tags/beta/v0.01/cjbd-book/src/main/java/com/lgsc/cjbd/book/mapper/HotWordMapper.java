package com.lgsc.cjbd.book.mapper;

import java.util.List;

import com.lgsc.cjbd.book.model.HotWord;
import com.lgsc.cjbd.dao.BaseMapper;

public interface HotWordMapper extends BaseMapper<HotWord> {
	/**
	 * 随机查询8个热词
	 * 
	 * @return
	 */
	public List<HotWord> selectRandom();
}