package com.lgsc.cjbd.book.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lgsc.cjbd.book.mapper.HotWordMapper;
import com.lgsc.cjbd.book.model.HotWord;

/**
 * 热词Service
 * 
 * @author Pengmeiyan
 *
 */
@Service
public class HotWordService {

	@Autowired
	private HotWordMapper hotWordMapper;

	/**
	 * 新增热词
	 * 
	 * @param HotWord
	 * @return
	 */
	public int insertHotWord(HotWord hotWord) {
		return hotWordMapper.insertSelective(hotWord);
	}

	/**
	 * 根据id删除热词
	 * 
	 * @param hotWordId
	 * @return
	 */
	public int deleteHotWord(long hotWordId) {
		return hotWordMapper.deleteByPrimaryKey(hotWordId);
	}

	/**
	 * 修改热词
	 * 
	 * @param HotWord
	 * @return
	 */
	public int updateHotWord(HotWord hotWord) {
		return hotWordMapper.updateByPrimaryKeySelective(hotWord);
	}

	/**
	 * 根据id查询热词
	 * 
	 * @param hotWordId
	 * @return
	 */
	public HotWord selectByHotWordId(long hotWordId) {
		return hotWordMapper.selectByPrimaryKey(hotWordId);
	}

	/**
	 * 查询所有热词
	 * 
	 * @return
	 */
	public List<HotWord> selectAllHotWord() {
		return hotWordMapper.selectAll();
	}

	/**
	 * 随机返回8个热词
	 * 
	 * @return
	 */
	public List<HotWord> selectRandom() {
		List<HotWord> lists = hotWordMapper.selectAll();
		if (lists.size() <= 8)
			return hotWordMapper.selectAll();
		else
			return hotWordMapper.selectRandom();
	}

	/**
	 * 分页查询所有热词
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageInfo<HotWord> selectAll(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<HotWord> list = hotWordMapper.selectAll();
		PageInfo<HotWord> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

}
