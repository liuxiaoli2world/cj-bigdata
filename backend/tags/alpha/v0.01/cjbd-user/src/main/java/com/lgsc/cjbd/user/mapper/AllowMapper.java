package com.lgsc.cjbd.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lgsc.cjbd.dao.BaseMapper;
import com.lgsc.cjbd.user.model.Allow;

public interface AllowMapper extends BaseMapper<Allow> {
	 /**
	  * 查询所有权限
	  * @return
	  */
	 public List<Allow> findAll();
	 /**
	  * 根据用户id查询其权限
	  * @param userId
	  * @return
	  */
	 public List<Allow> findByUserId(@Param("id") Long userId);
	 /**
	  * 通过角色编号查询权限
	  * @param roleId
	  * @return
	  */
	 public List<Allow> findByRoleId(@Param("roleId") Long roleId);
}