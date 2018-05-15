package com.lgsc.cjbd.book.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.lgsc.cjbd.book.mapper.EventUserBookMapper;
import com.lgsc.cjbd.book.model.EventUserBook;

/**
 * 
 */
@Service
public class EventUserBookService {
	
	@Autowired
	private EventUserBookMapper eventUserBookMapper;

	public int insert(EventUserBook record) {
		return eventUserBookMapper.insert(record);
	}

	public int insertSelective(EventUserBook record) {
		return eventUserBookMapper.insertSelective(record);
	}

	public EventUserBook selectByPrimaryKey(long id) {
		return eventUserBookMapper.selectByPrimaryKey(id);
	}
    
    public List<EventUserBook> selectAll() {
		return eventUserBookMapper.selectAll();
	}
	
	public PageInfo<EventUserBook> selectAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<EventUserBook> list = eventUserBookMapper.selectAll();
		PageInfo<EventUserBook> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	
	public int updateByPrimaryKey(EventUserBook record) {
		return eventUserBookMapper.updateByPrimaryKey(record);
	}

	public int updateByPrimaryKeySelective(EventUserBook record) {
		return eventUserBookMapper.updateByPrimaryKeySelective(record);
	}

	public int deleteByPrimaryKey(long id) {
		return eventUserBookMapper.deleteByPrimaryKey(id);
	}

}
