package com.lgsc.cjbd.user.model;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("机构")
@JsonIgnoreProperties(value = {"createdBy", "updatedBy", "createdAt","updatedAt","handler"})
public class Organization {
    /**
     * 机构id
     */
    @Id
    @ApiModelProperty("机构id")
    @Column(name = "organization_id")
    private Long organizationId;

    /**
     * 机构名称
     */
    @ApiModelProperty("机构名称 最大长度为20位汉字")
    @Length(min=1,max=20)
    private String name;

    /**
     * 机构代号
     */
    @Column(name = "seq_num")
    @Digits(integer=6,fraction=0)
    @ApiModelProperty("机构代号")
    private Integer seqNum;
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
     * 获取机构id
     *
     * @return organization_id - 机构id
     */
    public Long getOrganizationId() {
        return organizationId;
    }

    /**
     * 设置机构id
     *
     * @param organizationId 机构id
     */
    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    /**
     * 获取机构名称
     *
     * @return name - 机构名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置机构名称
     *
     * @param name 机构名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取机构代号
     *
     * @return seq_num - 机构代号
     */
    public Integer getSeqNum() {
        return seqNum;
    }

    /**
     * 设置机构代号
     *
     * @param seqNum 机构代号
     */
    public void setSeqNum(Integer seqNum) {
        this.seqNum = seqNum;
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