package com.lgsc.cjbd.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.lgsc.cjbd.order.mapper.HisBookMapper;
import com.lgsc.cjbd.order.model.HisBook;

/**
 * 
 */
@Service
public class HisBookService {
	
	@Autowired
	private HisBookMapper hisBookMapper;

	public int insert(HisBook record) {
		return hisBookMapper.insert(record);
	}

	public int insertSelective(HisBook record) {
		return hisBookMapper.insertSelective(record);
	}

	public HisBook selectByPrimaryKey(long id) {
		return hisBookMapper.selectByPrimaryKey(id);
	}
    
    public List<HisBook> selectAll() {
		return hisBookMapper.selectAll();
	}
	
	public PageInfo<HisBook> selectAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<HisBook> list = hisBookMapper.selectAll();
		PageInfo<HisBook> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	
	public int updateByPrimaryKey(HisBook record) {
		return hisBookMapper.updateByPrimaryKey(record);
	}

	public int updateByPrimaryKeySelective(HisBook record) {
		return hisBookMapper.updateByPrimaryKeySelective(record);
	}

	public int deleteByPrimaryKey(long id) {
		return hisBookMapper.deleteByPrimaryKey(id);
	}

}
