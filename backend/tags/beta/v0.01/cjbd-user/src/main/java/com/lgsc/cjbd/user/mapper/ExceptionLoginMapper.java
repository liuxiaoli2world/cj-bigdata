package com.lgsc.cjbd.user.mapper;

import org.apache.ibatis.annotations.Param;

import com.lgsc.cjbd.dao.BaseMapper;
import com.lgsc.cjbd.user.model.ExceptionLogin;

public interface ExceptionLoginMapper extends BaseMapper<ExceptionLogin> {
    
	/**
	 * 查询异常登录的ip个数
	 * @param id
	 * @return
	 */
	int selectIpNum(@Param("id") Long id);

	Short selectIfSendEmail(@Param("id") Long exceptionLoginId);
}