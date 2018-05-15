package com.lgsc.cjbd.user.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.lgsc.cjbd.dao.BaseMapper;
import com.lgsc.cjbd.user.model.UserVip;

public interface UserVipMapper extends BaseMapper<UserVip> {
	/**
	 * 根据用户id查询用户会员状态
	 * 
	 * @param userId
	 * @return
	 */
	public String selectVipStatus(Long userId);

	/**
	 * 根据会员id查询会员到期时间
	 * 
	 * @param userId
	 * @return
	 */
	public Date selectEndDate(long userId);
	
	/**
	 * 修改用户会员时长
	 * @param userId 用户id
	 * @param beginDate 开始时间
	 * @param endDate 结束时间
	 * @return
	 */
	public int updateEndDate(@Param("userId") Long userId, @Param("beginDate") Date beginDate, @Param("endDate") Date endDate);
	
	/**
	 * 累加用户会员时长
	 * @param userId 用户id
	 * @param duration 用户购买时长
	 * @return
	 */
	public int updateAddUpEndDate(@Param("userId") Long userId, @Param("duration") Integer duration);
	
}