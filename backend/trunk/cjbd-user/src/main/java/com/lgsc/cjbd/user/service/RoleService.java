package com.lgsc.cjbd.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lgsc.cjbd.user.dto.RoleDto;
import com.lgsc.cjbd.user.mapper.RoleMapper;
import com.lgsc.cjbd.user.mapper.UserRoleMapper;
import com.lgsc.cjbd.user.model.Role;
import com.lgsc.cjbd.user.model.UserRole;
import com.lgsc.cjbd.user.util.ChineseToEnglishUtil;

/**
 * 
 */
@Service
public class RoleService {

	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private ChineseToEnglishUtil chinestToEnglishUtil;

	@Autowired
	private UserRoleMapper userRoleMapper;

	public int insert(Role record) {
		return roleMapper.insert(record);
	}

	public int insertSelective(Role record) {
		record.setRoleName(chinestToEnglishUtil.getPingYin(record.getShowName()));
		return roleMapper.insertSelective(record);
	}

	public Role selectByPrimaryKey(long id) {
		return roleMapper.selectByPrimaryKey(id);
	}

	/**
	 * 根据角色名称查询角色id
	 * 
	 * @param roleName
	 * @return
	 */
	public Long selectRoleIdByName(String roleName) {
		return roleMapper.selectRoleIdByName(roleName);
	}

	public List<Role> selectAll() {
		return roleMapper.selectAll();
	}

	public PageInfo<RoleDto> selectAll(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<RoleDto> list = roleMapper.selectAllRole();
		PageInfo<RoleDto> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	public int updateByPrimaryKey(Role record) {
		return roleMapper.updateByPrimaryKey(record);
	}

	public int updateByPrimaryKeySelective(Role record) {
		record.setRoleName(chinestToEnglishUtil.getPingYin(record.getShowName()));
		return roleMapper.updateByPrimaryKeySelective(record);
	}

	public int deleteByPrimaryKey(long id) {
		UserRole userRole = new UserRole();
		userRole.setRoleId(id);
		userRoleMapper.delete(userRole);
		return roleMapper.deleteByPrimaryKey(id);
	}

	public Role getUserByShowName(String showName) {
	    Role role = new Role();
	    role.setShowName(showName);
		return roleMapper.selectOne(role);
	}

}
