package com.lgsc.cjbd.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lgsc.cjbd.dao.BaseMapper;
import com.lgsc.cjbd.user.model.Idea;

public interface IdeaMapper extends BaseMapper<Idea> {
    
	/**
	 * 通过状态和关键词查询意见
	 * @param satues
	 * @param keyWord
	 * @return
	 */
	List<Idea> selectIdeaForAdmin(@Param("status")Integer status, @Param("keyWord")String keyWord);
}