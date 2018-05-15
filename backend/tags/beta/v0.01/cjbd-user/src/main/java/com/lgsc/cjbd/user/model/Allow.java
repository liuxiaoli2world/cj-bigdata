package com.lgsc.cjbd.user.model;

import java.util.Date;
import javax.persistence.*;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("权限")
@JsonIgnoreProperties(value = {"createdBy", "createdAt", "updatedBy", "updatedAt", "handler"})
public class Allow {
    /**
     * 权限编号
     */
    @Id
    @Column(name = "allow_id")
    @ApiModelProperty("权限编号")
    private Long allowId;

    /**
     * 父权限编号
     */
    @Column(name = "parent_allow_id")
    @ApiModelProperty("父权限编号")
    private Long parentAllowId;

    /**
     * 名称
     */
    @Column(name = "allow_name")
    @ApiModelProperty("名称1-20")
    @Length(min = 1, max = 20)
    private String allowName;

    /**
     * 值
     */
    @Column(name = "allow_value")
    @ApiModelProperty("值url  1-20")
    @Length(min = 1, max = 20)
    private String allowValue;

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
     * 获取父权限编号
     *
     * @return parent_allow_id - 父权限编号
     */
    public Long getParentAllowId() {
        return parentAllowId;
    }

    /**
     * 设置父权限编号
     *
     * @param parentAllowId 父权限编号
     */
    public void setParentAllowId(Long parentAllowId) {
        this.parentAllowId = parentAllowId;
    }

    /**
     * 获取名称
     *
     * @return allow_name - 名称
     */
    public String getAllowName() {
        return allowName;
    }

    /**
     * 设置名称
     *
     * @param allowName 名称
     */
    public void setAllowName(String allowName) {
        this.allowName = allowName;
    }

    /**
     * 获取值
     *
     * @return allow_value - 值
     */
    public String getAllowValue() {
        return allowValue;
    }

    /**
     * 设置值
     *
     * @param allowValue 值
     */
    public void setAllowValue(String allowValue) {
        this.allowValue = allowValue;
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