package com.lgsc.cjbd.expert.mapper;

import java.util.List;

import com.lgsc.cjbd.dao.BaseMapper;
import com.lgsc.cjbd.expert.model.Composition;

public interface CompositionMapper extends BaseMapper<Composition> {
	/**
	 * 根据专家编号查询著作
	 * @param expertId
	 * @return
	 */
	public List<Composition> selectByExpertId(Long expertId);
}