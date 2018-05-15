package com.lgsc.cjbd.book.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lgsc.cjbd.book.mapper.MultiitemMapper;
import com.lgsc.cjbd.book.model.Multiitem;

import tk.mybatis.mapper.entity.Example;

/**
 * 多媒体项目
 * 
 * @author Pengmeiyan
 *
 */
@Service
public class MultiitemService {

	@Autowired
	private MultiitemMapper multiitemMapper;

	public int insert(Multiitem record) {
		return multiitemMapper.insert(record);
	}

	/**
	 * 添加多媒体项目
	 * @param multiitem
	 * @return
	 */
	public int insertSelective(Multiitem multiitem) {
		if (multiitem.getScene() == null) {
			multiitem.setScene("2");// 使用场景 1=封面 2=普通资源
		} else if (multiitem.getScene().equals("1")) {
			multiitem.setRank(0);
		}
		int maxRank = multiitemMapper.selectMaxRank();
		multiitem.setRank(maxRank + 1);
		Date date = new Date();
		multiitem.setCreatedAt(date);
		multiitem.setUpdatedAt(date);
		return multiitemMapper.insertSelective(multiitem);
	}

	public Multiitem selectByPrimaryKey(long multiitemId,String form) {
		if ("index".equals(form)) {
			updatePv(multiitemId);
		}
		return multiitemMapper.selectByPrimaryKey(multiitemId);
	}

	public List<Multiitem> selectAll() {
		return multiitemMapper.selectAll();
	}

	public PageInfo<Multiitem> selectAll(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Multiitem> list = multiitemMapper.selectAll();
		PageInfo<Multiitem> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	public int updateByPrimaryKey(Multiitem record) {
		return multiitemMapper.updateByPrimaryKey(record);
	}

	public int updateByPrimaryKeySelective(Multiitem multiitem) {
		if (multiitem.getScene() == null) {
			multiitem.setScene("2");// 使用场景 1=封面 2=普通资源
		} else if (multiitem.getScene().equals("1")) {
			multiitem.setRank(0);
		}
		Date date = new Date();
		multiitem.setUpdatedAt(date);
		return multiitemMapper.updateByPrimaryKeySelective(multiitem);
	}

	public int deleteByPrimaryKey(long id) {
		return multiitemMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 根据相册id查询图片
	 * 
	 * @param multifileId
	 * @return
	 */
	public List<Map<String, Object>> selectPicsById(long multifileId) {
		
		return multiitemMapper.selectPicsById(multifileId);
	}

	/**
	 * 查询首页视频列表
	 * 
	 * @return
	 */
	public List<Map<String, Object>> selectIndexVideoList(Long multifileId) {
		return multiitemMapper.selectIndexVideoList(multifileId);
	}
	
	/**
	 * 根据多媒体id查询所有多媒体项目
	 * 
	 * @param id
	 *            多媒体id
	 * @return
	 */
	public List<Multiitem> selectByMultifileId(Long id,String multiType, String from) {
		//查询
		List<Multiitem> multiitems = multiitemMapper.selectByMultifileId(id);
		
		//前台页面更新点击量
		if ("index".equals(from) && multiType.equals("pic"))
			for (Multiitem multiitem : multiitems) {
				Integer pv = multiitem.getPv();
				multiitem.setPv(++pv);
				//更新浏览量
				updatePv(multiitem.getMultifileId());
			}
		
		return multiitems;
	}
	
	/**
	 * 更新点击量
	 * 
	 * @param id
	 * @return
	 */
	private int updatePv(Long multiitemId) {
		int pv = multiitemMapper.selectPv(multiitemId);
		Multiitem multiitem = new Multiitem();
		multiitem.setPv(++pv);
		multiitem.setMultiitemId(multiitemId);
		return multiitemMapper.updateByPrimaryKeySelective(multiitem);
	}

	/**
	 * 修改排序
	 * 
	 * @param mis
	 * @return
	 */
	@Transactional
	public int updateRank(Multiitem[] mis) {
		int num = 0;
		for (Multiitem multiitem : mis) {
			multiitem.setUpdatedAt(new Date());
			multiitemMapper.updateByPrimaryKeySelective(multiitem);
			num++;
		}
		return num;
	}

	/**
	 * 查询图片、音频、视频数量
	 * @return
	 */
	public Map<String, Object> selectSum() {
		return multiitemMapper.selectSum();
	}
}
