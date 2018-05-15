package com.lgsc.cjbd.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.lgsc.cjbd.order.mapper.HisVipMapper;
import com.lgsc.cjbd.order.model.HisVip;

/**
 * 
 */
@Service
public class HisVipService {
	
	@Autowired
	private HisVipMapper hisVipMapper;

	public int insert(HisVip record) {
		return hisVipMapper.insert(record);
	}

	public int insertSelective(HisVip record) {
		return hisVipMapper.insertSelective(record);
	}

	public HisVip selectByPrimaryKey(long id) {
		return hisVipMapper.selectByPrimaryKey(id);
	}
    
    public List<HisVip> selectAll() {
		return hisVipMapper.selectAll();
	}
	
	public PageInfo<HisVip> selectAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<HisVip> list = hisVipMapper.selectAll();
		PageInfo<HisVip> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	
	public int updateByPrimaryKey(HisVip record) {
		return hisVipMapper.updateByPrimaryKey(record);
	}

	public int updateByPrimaryKeySelective(HisVip record) {
		return hisVipMapper.updateByPrimaryKeySelective(record);
	}

	public int deleteByPrimaryKey(long id) {
		return hisVipMapper.deleteByPrimaryKey(id);
	}

}
