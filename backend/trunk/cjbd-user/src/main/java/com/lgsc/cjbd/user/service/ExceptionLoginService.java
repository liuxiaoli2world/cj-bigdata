package com.lgsc.cjbd.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgsc.cjbd.user.mapper.ExceptionLoginMapper;
import com.lgsc.cjbd.user.model.ExceptionLogin;

/**
 * 
 */
@Service
public class ExceptionLoginService {

	@Autowired
	private ExceptionLoginMapper exceptionLoginMapper;

	public ExceptionLogin selectByPrimaryKey() {
		List<ExceptionLogin> list = exceptionLoginMapper.selectAll();
		return exceptionLoginMapper.selectByPrimaryKey(list.get(0).getExceptionLoginId());
	}

	public int updateByPrimaryKeySelective(ExceptionLogin record) {
		return exceptionLoginMapper.updateByPrimaryKeySelective(record);
	}

	public int selectIpNum() {
		List<ExceptionLogin> list = exceptionLoginMapper.selectAll();
		return exceptionLoginMapper.selectIpNum(list.get(0).getExceptionLoginId());
	}

	public Short IfSendEmail() {
		List<ExceptionLogin> list = exceptionLoginMapper.selectAll();
		return exceptionLoginMapper.selectIfSendEmail(list.get(0).getExceptionLoginId());
	}

}
