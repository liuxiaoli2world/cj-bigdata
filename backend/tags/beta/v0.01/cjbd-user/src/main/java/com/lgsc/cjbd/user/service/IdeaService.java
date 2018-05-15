package com.lgsc.cjbd.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.lgsc.cjbd.user.mapper.IdeaMapper;
import com.lgsc.cjbd.user.model.Idea;

/**
 * 
 */
@Service
public class IdeaService {

	@Autowired
	private IdeaMapper ideaMapper;

	public int insert(Idea record) {
		return ideaMapper.insert(record);
	}

	public int insertSelective(Idea record) {
		return ideaMapper.insertSelective(record);
	}

	public Idea selectByPrimaryKey(long id) {
		return ideaMapper.selectByPrimaryKey(id);
	}

	public List<Idea> selectAll() {
		return ideaMapper.selectAll();
	}

	public PageInfo<Idea> selectAll(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Idea> list = ideaMapper.selectAll();
		PageInfo<Idea> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	public int updateByPrimaryKey(Idea record) {
		return ideaMapper.updateByPrimaryKey(record);
	}

	public int updateByPrimaryKeySelective(Idea record) {
		return ideaMapper.updateByPrimaryKeySelective(record);
	}

	public int deleteByPrimaryKey(long id) {
		return ideaMapper.deleteByPrimaryKey(id);
	}

	public PageInfo<Idea> selectIdeaForAdmin(Integer pageNum, Integer pageSize, Integer status, String keyWord) {
		PageHelper.startPage(pageNum, pageSize);
		List<Idea> list = ideaMapper.selectIdeaForAdmin(status-1,keyWord);
		PageInfo<Idea> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

}
