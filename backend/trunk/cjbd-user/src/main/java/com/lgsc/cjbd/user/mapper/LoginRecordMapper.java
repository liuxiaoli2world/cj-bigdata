package com.lgsc.cjbd.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lgsc.cjbd.dao.BaseMapper;
import com.lgsc.cjbd.user.model.LoginRecord;

public interface LoginRecordMapper extends BaseMapper<LoginRecord> {
    
	/**
	 * 查询用户的登录记录
	 * @param userId
	 * @return
	 */
	List<LoginRecord> selectByUserId(@Param("userId")Long userId);
    
	/**
	 * 查询用户登录的ip个数
	 * @param userId
	 * @return
	 */
	int selectCoutIp(@Param("userId")Long userId);
}