package com.lgsc.cjbd.expert.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lgsc.cjbd.expert.mapper.DomainMapper;
import com.lgsc.cjbd.expert.model.Domain;

/**
 * 专家分类
 * 
 * @author Pengmeiyan
 *
 */
@Service
public class DomainService {

	@Autowired
	private DomainMapper domainMapper;

	public int insert(Domain record) {
		return domainMapper.insert(record);
	}

	/**
	 * 新增专家分类
	 * 
	 * @param domain
	 * @return
	 */
	public int insertSelective(Domain domain) {
		return domainMapper.insertSelective(domain);
	}

	/**
	 * 根据专家分类编号删除专家分类
	 * 
	 * @param domainId
	 * @return
	 */
	public int deleteByPrimaryKey(long domainId) {
		return domainMapper.deleteByPrimaryKey(domainId);
	}

	/**
	 * 修改专家分类
	 * 
	 * @param domain
	 * @return
	 */
	public int updateByPrimaryKeySelective(Domain domain) {
		return domainMapper.updateByPrimaryKeySelective(domain);
	}

	/**
	 * 查询所有专家分类
	 * 
	 * @return
	 */
	public List<Domain> selectAll() {
		return domainMapper.selectAll();
	}

	/**
	 * 分页查询所有专家分类
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageInfo<Domain> selectAll(int pageNum, int pageSize,String name) {
		PageHelper.startPage(pageNum, pageSize);
		List<Domain> list =new ArrayList<Domain>();
		if (name == null)
			 list = domainMapper.selectAll();
		else
			list =domainMapper.selectDomainByName(name);
		PageInfo<Domain> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	/**
	 * 
	 * 根据id查询专家分类
	 * 
	 * @param domainId
	 * @return
	 */
	public Domain selectByPrimaryKey(long domainId) {
		return domainMapper.selectByPrimaryKey(domainId);
	}

	/**
	 * 根据专家id，查询专家分类名称
	 * 
	 * @param expertId
	 * @return
	 */
	public String selectNameByExpertId(long expertId) {
		return domainMapper.selectNameByExpertId(expertId);
	}
}
