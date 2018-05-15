package com.lgsc.cjbd.book.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.lgsc.cjbd.book.mapper.EventBookmarkMapper;
import com.lgsc.cjbd.book.model.EventBookmark;

/**
 * 
 */
@Service
public class EventBookmarkService {
	
	@Autowired
	private EventBookmarkMapper eventBookmarkMapper;

	public int insert(EventBookmark record) {
		return eventBookmarkMapper.insert(record);
	}

	public int insertSelective(EventBookmark record) {
		return eventBookmarkMapper.insertSelective(record);
	}

	public EventBookmark selectByPrimaryKey(long id) {
		return eventBookmarkMapper.selectByPrimaryKey(id);
	}
    
    public List<EventBookmark> selectAll() {
		return eventBookmarkMapper.selectAll();
	}
	
	public PageInfo<EventBookmark> selectAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<EventBookmark> list = eventBookmarkMapper.selectAll();
		PageInfo<EventBookmark> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	
	public int updateByPrimaryKey(EventBookmark record) {
		return eventBookmarkMapper.updateByPrimaryKey(record);
	}

	public int updateByPrimaryKeySelective(EventBookmark record) {
		return eventBookmarkMapper.updateByPrimaryKeySelective(record);
	}

	public int deleteByPrimaryKey(long id) {
		return eventBookmarkMapper.deleteByPrimaryKey(id);
	}

}
