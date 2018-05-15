package com.lgsc.cjbd.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lgsc.cjbd.user.mapper.RoleMapper;
import com.lgsc.cjbd.user.mapper.UserRoleMapper;
import com.lgsc.cjbd.user.model.UserRole;

/**
 * 
 */
@Service
public class UserRoleService {

	@Autowired
	private UserRoleMapper userRoleMapper;
	@Autowired
	private RoleMapper roleMapper;

	public int insert(UserRole record) {
		return userRoleMapper.insert(record);
	}

	public int insertSelective(UserRole record) {
		return userRoleMapper.insertSelective(record);
	}

	public UserRole selectByPrimaryKey(long id) {
		return userRoleMapper.selectByPrimaryKey(id);
	}

	/**
	 * 根据userId删除用户角色关系(专家只有一个角色)
	 * 
	 * @param userId
	 * @return
	 */
	public int deleteRoleByuserId(long userId) {
		return userRoleMapper.deleteRoleByuserId(userId);
	}

	public List<UserRole> selectAll() {
		return userRoleMapper.selectAll();
	}

	public PageInfo<UserRole> selectAll(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<UserRole> list = userRoleMapper.selectAll();
		PageInfo<UserRole> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	public int updateByPrimaryKey(UserRole record) {
		return userRoleMapper.updateByPrimaryKey(record);
	}

	public int updateByPrimaryKeySelective(UserRole record) {
		return userRoleMapper.updateByPrimaryKeySelective(record);
	}

	public int deleteByPrimaryKey(long id) {
		return userRoleMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 新增专家学者角色关系
	 * 
	 * @param userId
	 * @return
	 */
	public int insertExpertRole(Long userId) {
		UserRole userRole = new UserRole();
		userRole.setUserId(userId);
		// 根据角色名称查询角色id
		Long roleId = roleMapper.selectRoleIdByName("ROLE_AUTHOR");
		userRole.setRoleId(roleId);
		return userRoleMapper.insertSelective(userRole);
	}
	
	
	/**
	 * 根据用户id查询用户角色状态
	 * @param userId
	 * @return
	 */
	public List<String> selectRoleStatus(Long userId) {
		return userRoleMapper.selectVipStatus(userId);
	}

}
