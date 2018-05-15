package com.lgsc.cjbd.expert.mapper;

import java.util.List;

import com.lgsc.cjbd.dao.BaseMapper;
import com.lgsc.cjbd.expert.model.Domain;

public interface DomainMapper extends BaseMapper<Domain> {
	/**
	 * 根据分类名称模糊查询专家分类
	 * 
	 * @param name
	 * @return
	 */
	public List<Domain> selectDomainByName(String name);

	/**
	 * 根据分类名称查询专家分类id
	 * 
	 * @param name
	 * @return
	 */
	public Long selectByClassifyName(String name);

	/**
	 * 根据专家id，查询专家分类名称
	 * 
	 * @param expertId
	 * @return
	 */
	public String selectNameByExpertId(Long expertId);
}