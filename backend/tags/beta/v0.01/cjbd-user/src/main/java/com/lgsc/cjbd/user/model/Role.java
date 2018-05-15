package com.lgsc.cjbd.user.model;

import java.util.Date;
import javax.persistence.*;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("角色")
@JsonIgnoreProperties(value = {"createdBy", "createdAt", "updatedBy", "updatedAt", "handler"})
public class Role {
    /**
     * 角色编号
     */
    @Id
    @Column(name = "role_id")
    @ApiModelProperty("角色编号")
    private Long roleId;

    /**
     * 角色名称
     */
    @Column(name = "role_name")
    @ApiModelProperty("角色名称 长度为30")
    @Length(min=1,max=30)
    private String roleName;
    
    @ApiModelProperty("展示的角色名 长度为30")
    private String showName;

    /**
     * 说明
     */
    @Column(name = "role_desc")
    @ApiModelProperty("角色详细描述 长度为200")
    @Length(min=1,max=200)
    private String roleDesc;

    /**
     * 创建人
     */
    @Column(name = "created_by")
    @Length(min = 1, max = 30)
    private String createdBy;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @Column(name = "created_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    /**
     * 最后修改人
     */
    @Column(name = "updated_by")
    @Length(min = 1, max = 30)
    private String updatedBy;

    /**
     * 最后修改时间
     */
    @ApiModelProperty("最近登录时间")
    @Column(name = "updated_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;
    /**
     * 获取角色编号
     *
     * @return role_id - 角色编号
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * 设置角色编号
     *
     * @param roleId 角色编号
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取角色名称
     *
     * @return role_name - 角色名称
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * 设置角色名称
     *
     * @param roleName 角色名称
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * 获取说明
     *
     * @return role_desc - 说明
     */
    public String getRoleDesc() {
        return roleDesc;
    }

    /**
     * 设置说明
     *
     * @param roleDesc 说明
     */
    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    /**
     * 获取创建人
     *
     * @return created_by - 创建人
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * 设置创建人
     *
     * @param createdBy 创建人
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * 获取创建时间
     *
     * @return created_at - 创建时间
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * 设置创建时间
     *
     * @param createdAt 创建时间
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 获取最后修改人
     *
     * @return updated_by - 最后修改人
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * 设置最后修改人
     *
     * @param updatedBy 最后修改人
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * 获取最后修改时间
     *
     * @return updated_at - 最后修改时间
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 设置最后修改时间
     *
     * @param updatedAt 最后修改时间
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}
    
}