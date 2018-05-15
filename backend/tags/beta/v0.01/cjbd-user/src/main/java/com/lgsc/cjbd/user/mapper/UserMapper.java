package com.lgsc.cjbd.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lgsc.cjbd.dao.BaseMapper;
import com.lgsc.cjbd.user.dto.UserIndexDto;
import com.lgsc.cjbd.user.dto.UserLoginDto;
import com.lgsc.cjbd.user.dto.UserManagementDto;
import com.lgsc.cjbd.user.dto.UserOrganizationDto;
import com.lgsc.cjbd.user.dto.UserResponseDto;

import com.lgsc.cjbd.user.model.User;

public interface UserMapper extends BaseMapper<User> {

	/**
	 * 根据邮箱，用户名，手机号查询用户
	 * 
	 * @param Tag
	 * @return
	 */
	public User findUserByTag(@Param("Tag") String Tag);

	/**
	 * 根据机构编号删除用户
	 * 
	 * @param id
	 * @return
	 */
	public int deleteBySqeNum(@Param("id") Integer id);

	/**
	 * 根据机构编号查询用户数量
	 * 
	 * @param id
	 * @return
	 */
	public int selectCountBySeqNum(@Param("id") Integer id);

	/**
	 * 查询用户详细
	 * 
	 * @param id
	 * @return
	 */
	public UserResponseDto selectById(@Param("id") Long id);
	
	/**
	 * 查询所有用户信息dto
	 * @return
	 */
	public List<UserManagementDto> selectAllUserManagementDTO();
	
	/**
	 * 查询用户登录信息详细
	 * @param id
	 * @return
	 */
	public UserLoginDto selectUserLoginDto(@Param("id") Long id);
    /**
     *
     *设置质检员
     * @param id
     * @return
     */
	public int setInspector(@Param("id") Long id);
    /**
     * 查询新增粉丝的信息
     * @return
     */
	public UserIndexDto selectUserForIndex(@Param("createdAt") String createdAt);
    /**
     * 查询机构用户详细
     * @param sqe_num
     * @return
     */
	public List<UserOrganizationDto> selectUserOrganization(@Param("sqeNum") Integer sqe_num);

	/**
	 * 后台条件查询用户
	 * @param enable 账号状态 0=不可用 1=可用
	 * @param vipStatus 会员状态 0=普通用户 1=会员 2=过期会员 
	 * @param organization 机构名称
	 * @return
	 */
	public List<UserOrganizationDto> selectBy(@Param("enable")Short enable, @Param("vipStatus")Short vipStatus, @Param("organization")String organization,@Param("username")String username);
  

}