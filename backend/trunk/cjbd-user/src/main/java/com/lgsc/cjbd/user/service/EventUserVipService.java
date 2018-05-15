package com.lgsc.cjbd.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.lgsc.cjbd.user.mapper.EventUserVipMapper;
import com.lgsc.cjbd.user.model.EventUserVip;

/**
 * 
 */
@Service
public class EventUserVipService {
	
	@Autowired
	private EventUserVipMapper eventUserVipMapper;

	public int insert(EventUserVip record) {
		return eventUserVipMapper.insert(record);
	}

	public int insertSelective(EventUserVip record) {
		return eventUserVipMapper.insertSelective(record);
	}

	public EventUserVip selectByPrimaryKey(long id) {
		return eventUserVipMapper.selectByPrimaryKey(id);
	}
    
    public List<EventUserVip> selectAll() {
		return eventUserVipMapper.selectAll();
	}
	
	public PageInfo<EventUserVip> selectAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<EventUserVip> list = eventUserVipMapper.selectAll();
		PageInfo<EventUserVip> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	
	public int updateByPrimaryKey(EventUserVip record) {
		return eventUserVipMapper.updateByPrimaryKey(record);
	}

	public int updateByPrimaryKeySelective(EventUserVip record) {
		return eventUserVipMapper.updateByPrimaryKeySelective(record);
	}

	public int deleteByPrimaryKey(long id) {
		return eventUserVipMapper.deleteByPrimaryKey(id);
	}

}
