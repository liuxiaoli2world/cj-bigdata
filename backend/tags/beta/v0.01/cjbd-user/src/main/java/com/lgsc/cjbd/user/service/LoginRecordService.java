package com.lgsc.cjbd.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.lgsc.cjbd.user.mapper.LoginRecordMapper;
import com.lgsc.cjbd.user.model.LoginRecord;

/**
 * 
 */
@Service
public class LoginRecordService {
	
	@Autowired
	private LoginRecordMapper loginRecordMapper;

	public int insert(LoginRecord record) {
		return loginRecordMapper.insert(record);
	}

	public int insertSelective(LoginRecord record) {
		return loginRecordMapper.insertSelective(record);
	}

	public LoginRecord selectByPrimaryKey(long id) {
		return loginRecordMapper.selectByPrimaryKey(id);
	}
    
    public List<LoginRecord> selectAll() {
		return loginRecordMapper.selectAll();
	}
	
	public PageInfo<LoginRecord> selectAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<LoginRecord> list = loginRecordMapper.selectAll();
		PageInfo<LoginRecord> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	
	public int updateByPrimaryKey(LoginRecord record) {
		return loginRecordMapper.updateByPrimaryKey(record);
	}

	public int updateByPrimaryKeySelective(LoginRecord record) {
		return loginRecordMapper.updateByPrimaryKeySelective(record);
	}

	public int deleteByPrimaryKey(long id) {
		return loginRecordMapper.deleteByPrimaryKey(id);
	}
    /**
     * 通过 用户的id查询用户的登录记录
     * @param userId
     * @return
     */
	public List<LoginRecord> selectByUserId(Long userId) {
		
		return loginRecordMapper.selectByUserId(userId);
	}
	
	
	

}
