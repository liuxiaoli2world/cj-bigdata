package com.lgsc.cjbd.user.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.lgsc.cjbd.user.mapper.VipMapper;
import com.lgsc.cjbd.user.model.Vip;

/**
 * 
 */
@Service
public class VipService {

	@Autowired
	private VipMapper vipMapper;

	public int insert(Vip record) {
		return vipMapper.insert(record);
	}

	public int insertSelective(Vip record) {
		int num = 0;
		Vip vip = new Vip();
		vip.setDuration(record.getDuration());
		List<Vip> list = vipMapper.select(vip);
		if (list != null && list.size() > 0) {
			num = 1062;
		} else {
			record.setVipId(null);
			record.setCreatedAt(new Date());
			num = vipMapper.insertSelective(record);
		}
		return num;
	}

	public Vip selectByPrimaryKey(long id) {
		return vipMapper.selectByPrimaryKey(id);
	}

	public List<Vip> selectAll() {
		return vipMapper.selectAll();
	}

	public PageInfo<Vip> selectAll(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Vip> list = vipMapper.selectAll();
		PageInfo<Vip> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	public int updateByPrimaryKey(Vip record) {
		return vipMapper.updateByPrimaryKey(record);
	}

	public int updateByPrimaryKeySelective(Vip record) {
		return vipMapper.updateByPrimaryKeySelective(record);
	}

	public int deleteByPrimaryKey(long id) {
		return vipMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 获取会员商品信息For Client
	 * 
	 * @param id
	 *            会员商品id
	 * @return
	 */
	public Map<String, Object> selectDetail(Long id) {
		Vip vip = vipMapper.selectByPrimaryKey(id);
		Map<String, Object> map = new HashMap<>();
		if (vip != null) {
			map.put("name", vip.getName());
			map.put("imageUrl", vip.getImageUrl());
			map.put("duration", vip.getDuration());
			map.put("price", vip.getPrice());
		}
		return map;
	}

}
