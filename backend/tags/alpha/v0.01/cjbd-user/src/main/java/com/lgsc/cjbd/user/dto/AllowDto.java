package com.lgsc.cjbd.user.dto;

import javax.persistence.Column;
import javax.persistence.Id;



public class AllowDto {
	@Id
    @Column(name = "allow_id")
    private Long allowId;

    /**
     * 父权限编号
     */
    @Column(name = "parent_allow_id")
    private Long parentAllowId;

    /**
     * 名称
     */
    @Column(name = "allow_name")
    private String allowName;
    
    private  boolean isAllows;

	public Long getAllowId() {
		return allowId;
	}

	public void setAllowId(Long allowId) {
		this.allowId = allowId;
	}

	public Long getParentAllowId() {
		return parentAllowId;
	}

	public void setParentAllowId(Long parentAllowId) {
		this.parentAllowId = parentAllowId;
	}

	public String getAllowName() {
		return allowName;
	}

	public void setAllowName(String allowName) {
		this.allowName = allowName;
	}

	public boolean getIsAllows() {
		return isAllows;
	}

	public void setIsAllows(boolean isAllows) {
		this.isAllows = isAllows;
	}

	
    
    
}
