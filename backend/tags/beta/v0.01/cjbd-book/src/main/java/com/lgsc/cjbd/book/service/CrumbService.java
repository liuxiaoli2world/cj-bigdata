package com.lgsc.cjbd.book.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.lgsc.cjbd.book.mapper.CrumbMapper;
import com.lgsc.cjbd.book.model.Crumb;

/**
 * 
 */
@Service
public class CrumbService {
	
	@Autowired
	private CrumbMapper crumbMapper;

	public int insert(Crumb record) {
		return crumbMapper.insert(record);
	}

	public int insertSelective(Crumb record) {
		return crumbMapper.insertSelective(record);
	}

	public Crumb selectByPrimaryKey(long id) {
		return crumbMapper.selectByPrimaryKey(id);
	}
    
    public List<Crumb> selectAll() {
		return crumbMapper.selectAll();
	}
	
	public PageInfo<Crumb> selectAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Crumb> list = crumbMapper.selectAll();
		PageInfo<Crumb> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	
	public int updateByPrimaryKey(Crumb record) {
		return crumbMapper.updateByPrimaryKey(record);
	}

	public int updateByPrimaryKeySelective(Crumb record) {
		return crumbMapper.updateByPrimaryKeySelective(record);
	}

	public int deleteByPrimaryKey(long id) {
		return crumbMapper.deleteByPrimaryKey(id);
	}

}
