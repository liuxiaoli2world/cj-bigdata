package com.lgsc.cjbd.book.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.lgsc.cjbd.book.mapper.ContentAccessoryMapper;
import com.lgsc.cjbd.book.model.ContentAccessory;

/**
 * 
 */
@Service
public class ContentAccessoryService {
	
	@Autowired
	private ContentAccessoryMapper contentAccessoryMapper;

	public int insert(ContentAccessory record) {
		return contentAccessoryMapper.insert(record);
	}

	public int insertSelective(ContentAccessory record) {
		return contentAccessoryMapper.insertSelective(record);
	}

	public ContentAccessory selectByPrimaryKey(long id) {
		return contentAccessoryMapper.selectByPrimaryKey(id);
	}
    
    public List<ContentAccessory> selectAll() {
		return contentAccessoryMapper.selectAll();
	}
	
	public PageInfo<ContentAccessory> selectAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ContentAccessory> list = contentAccessoryMapper.selectAll();
		PageInfo<ContentAccessory> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	
	public int updateByPrimaryKey(ContentAccessory record) {
		return contentAccessoryMapper.updateByPrimaryKey(record);
	}

	public int updateByPrimaryKeySelective(ContentAccessory record) {
		return contentAccessoryMapper.updateByPrimaryKeySelective(record);
	}

	public int deleteByPrimaryKey(long id) {
		return contentAccessoryMapper.deleteByPrimaryKey(id);
	}

}
