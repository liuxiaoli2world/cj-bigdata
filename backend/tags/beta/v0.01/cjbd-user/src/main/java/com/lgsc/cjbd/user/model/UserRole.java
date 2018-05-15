package com.lgsc.cjbd.user.model;

import java.util.Date;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

@ApiModel("用户角色")
@JsonIgnoreProperties(value = {"createdBy", "createdAt", "updatedBy", "updatedAt", "handler"})
@Table(name = "user_role")
public class UserRole {
    /**
     * 用户角色关系编号
     */
    @Id
    @Column(name = "user_role_id")
    @ApiParam("用户角色关系编号")
    private Long userRoleId;

    /**
     * 角色编号
     */
    @ApiParam("角色编号")
    @Column(name = "role_id")
    private Long roleId;

    /**
     * 用户编号
     */
    @ApiParam("用户编号")
    @Column(name = "user_id")
    private Long userId;

    /**
     * 创建人
     */
    @ApiParam("创建人")
    @Column(name = "created_by")
    private String createdBy;

    /**
     * 创建时间
     */
    @ApiParam("创建人")
    @Column(name = "created_at")
    private Date createdAt;

    /**
     * 最后修改人
     */
    @ApiParam("最后修改人")
    @Column(name = "updated_by")
    private String updatedBy;

    /**
     * 最后修改时间
     */
    @ApiParam("最后修改时间")
    @Column(name = "updated_at")
    private Date updatedAt;

    /**
     * 获取用户角色关系编号
     *
     * @return user_role_id - 用户角色关系编号
     */
    public Long getUserRoleId() {
        return userRoleId;
    }

    /**
     * 设置用户角色关系编号
     *
     * @param userRoleId 用户角色关系编号
     */
    public void setUserRoleId(Long userRoleId) {
        this.userRoleId = userRoleId;
    }

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
     * 获取用户编号
     *
     * @return user_id - 用户编号
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户编号
     *
     * @param userId 用户编号
     */
    public void setUserId(Long userId) {
        this.userId = userId;
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
}