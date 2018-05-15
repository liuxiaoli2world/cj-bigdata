package com.lgsc.cjbd.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lgsc.cjbd.dao.BaseMapper;
import com.lgsc.cjbd.user.dto.RoleDto;
import com.lgsc.cjbd.user.model.Role;

public interface RoleMapper extends BaseMapper<Role> {

	/**
	 * 根据用户id查找角色
	 * 
	 * @param id
	 * @return
	 */
	public List<Role> selectRoleByUserId(@Param("id") Long id);

	/**
	 * 根据角色id查询角色名称
	 * 
	 * @param roleName
	 * @return
	 */
	public Long selectRoleIdByName(String roleName);
	/**
	 * 查询所有角色
	 * @return
	 */
	public List<RoleDto> selectAllRole();
}