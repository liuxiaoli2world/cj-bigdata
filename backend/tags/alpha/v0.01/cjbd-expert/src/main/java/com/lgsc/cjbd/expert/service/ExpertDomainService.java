package com.lgsc.cjbd.expert.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lgsc.cjbd.expert.mapper.ExpertDomainMapper;
import com.lgsc.cjbd.expert.model.ExpertDomain;

/**
 * 专家分类Service
 * 
 * @author Pengmeiyan
 *
 */
@Service
public class ExpertDomainService {

	@Autowired
	private ExpertDomainMapper expertDomainMapper;

	/**
	 * 新增专家分类关系
	 * 
	 * @param expertDomain
	 * @return
	 */
	public int insertSelective(ExpertDomain expertDomain) {
		return expertDomainMapper.insertSelective(expertDomain);
	}

	/**
	 * 根据id删除专家分类关系
	 * 
	 * @param expertDomainId
	 * @return
	 */
	public int deleteByPrimaryKey(long expertDomainId) {
		return expertDomainMapper.deleteByPrimaryKey(expertDomainId);
	}

	/**
	 * 修改专家分类关系
	 * 
	 * @param expertDomain
	 * @return
	 */
	public int updateByPrimaryKeySelective(ExpertDomain expertDomain) {
		return expertDomainMapper.updateByPrimaryKeySelective(expertDomain);
	}

	/**
	 * 根据id查找专家分类关系
	 * 
	 * @param expertDomainId
	 * @return
	 */
	public ExpertDomain selectByPrimaryKey(long expertDomainId) {
		return expertDomainMapper.selectByPrimaryKey(expertDomainId);
	}

	/**
	 * 查询所有专家分类关系
	 * 
	 * @return
	 */
	public List<ExpertDomain> selectAll() {
		return expertDomainMapper.selectAll();
	}

	/**
	 * 分页查询所有专家分类关系
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageInfo<ExpertDomain> selectAll(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<ExpertDomain> list = expertDomainMapper.selectAll();
		PageInfo<ExpertDomain> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

}
