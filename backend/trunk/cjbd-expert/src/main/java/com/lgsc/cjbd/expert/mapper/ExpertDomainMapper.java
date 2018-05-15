package com.lgsc.cjbd.expert.mapper;

import java.util.List;

import com.lgsc.cjbd.dao.BaseMapper;
import com.lgsc.cjbd.expert.model.ExpertDomain;

public interface ExpertDomainMapper extends BaseMapper<ExpertDomain> {
	/**
	 * 根据专家id查找分类关系
	 * 
	 * @param expertId
	 * @return
	 */
	public ExpertDomain selectByExceptId(long expertId);

	/**
	 * 根据专家分类编号查询所有的专家id
	 * 
	 * @param domainId
	 * @return
	 */
	public List<Long> selectByDomainId(long domainId);

	/**
	 * 根据专家id删除专家分类关系
	 * 
	 * @param expertId
	 * @return
	 */
	public int deleteByExpertId(Long expertId);

}