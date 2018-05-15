package com.lgsc.cjbd.user.mapper;

import java.util.List;

import com.lgsc.cjbd.dao.BaseMapper;
import com.lgsc.cjbd.user.model.UserRole;

public interface UserRoleMapper extends BaseMapper<UserRole> {
	/**
	 * 根据userId删除用户角色关系(专家只有一个角色)
	 * 
	 * @param userId
	 * @return
	 */
	int deleteRoleByuserId(long userId);
	
	/**
	 * 根据用户id查询用户角色状态
	 * 
	 * @param userId
	 * @return
	 */
	public List<String> selectVipStatus(Long userId);
}