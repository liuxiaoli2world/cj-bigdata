package com.lgsc.cjbd.user.model;

import java.util.Date;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = {"createdBy", "createdAt", "updatedBy", "updatedAt", "handler"})
@Table(name = "role_allow")
public class RoleAllow {
    /**
     * 角色权限关系编号
     */
    @Id
    @Column(name = "role_allow_id")
    private Long roleAllowId;

    /**
     * 权限编号
     */
    @Column(name = "allow_id")
    private Long allowId;

    /**
     * 角色编号
     */
    @Column(name = "role_id")
    private Long roleId;

    /**
     * 创建人
     */
    @Column(name = "created_by")
    private String createdBy;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private Date createdAt;

    /**
     * 最后修改人
     */
    @Column(name = "updated_by")
    private String updatedBy;

    /**
     * 最后修改时间
     */
    @Column(name = "updated_at")
    private Date updatedAt;

    /**
     * 获取角色权限关系编号
     *
     * @return role_allow_id - 角色权限关系编号
     */
    public Long getRoleAllowId() {
        return roleAllowId;
    }

    /**
     * 设置角色权限关系编号
     *
     * @param roleAllowId 角色权限关系编号
     */
    public void setRoleAllowId(Long roleAllowId) {
        this.roleAllowId = roleAllowId;
    }

    /**
     * 获取权限编号
     *
     * @return allow_id - 权限编号
     */
    public Long getAllowId() {
        return allowId;
    }

    /**
     * 设置权限编号
     *
     * @param allowId 权限编号
     */
    public void setAllowId(Long allowId) {
        this.allowId = allowId;
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