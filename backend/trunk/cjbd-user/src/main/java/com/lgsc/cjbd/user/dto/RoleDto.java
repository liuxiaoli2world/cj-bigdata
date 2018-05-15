package com.lgsc.cjbd.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 权限dto
 * @author 罗天宇
 *
 */
@JsonIgnoreProperties(value = {"handler"})
public class RoleDto {
	 /**
     * 角色编号
     */
   
    private Long roleId;
    /**
     * 展示的名字
     */
    private String showName;

    /**
     * 说明
     */
    private String roleDesc;
    
    /**
     * 权限用户的数量
     */
    private Integer countUser;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public Integer getCountUser() {
		return countUser;
	}

	public void setCountUser(Integer countUser) {
		this.countUser = countUser;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}
    
    
}
