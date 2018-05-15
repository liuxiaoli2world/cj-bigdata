package com.lgsc.cjbd.book.model;

import java.util.Date;
import javax.persistence.*;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(value = {"createdBy", "createdAt", "updatedBy", "updatedAt", "handler"})
public class Crumb {
    /**
     * 网站导航编号
     */
    @Id
    @Column(name = "crumb_id")
    private Long crumbId;

    /**
     * 父导航，根节点为0
     */
    @Column(name = "parent_crumb_id")
    private Long parentCrumbId;

    /**
     * 文件名
     */
    private String name;

    /**
	 * 创建人
	 */
	@ApiModelProperty("创建人")
	@Column(name = "created_by")
	@Length(min = 0, max = 30)
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
	@ApiModelProperty("最后修改人")
	@Column(name = "updated_by")
	@Length(min = 0, max = 30)
	private String updatedBy;

	/**
	 * 最后修改时间
	 */
	@ApiModelProperty("最后修改时间")
	@Column(name = "updated_at")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updatedAt;

    /**
     * 获取网站导航编号
     *
     * @return crumb_id - 网站导航编号
     */
    public Long getCrumbId() {
        return crumbId;
    }

    /**
     * 设置网站导航编号
     *
     * @param crumbId 网站导航编号
     */
    public void setCrumbId(Long crumbId) {
        this.crumbId = crumbId;
    }

    /**
     * 获取父导航，根节点为0
     *
     * @return parent_crumb_id - 父导航，根节点为0
     */
    public Long getParentCrumbId() {
        return parentCrumbId;
    }

    /**
     * 设置父导航，根节点为0
     *
     * @param parentCrumbId 父导航，根节点为0
     */
    public void setParentCrumbId(Long parentCrumbId) {
        this.parentCrumbId = parentCrumbId;
    }

    /**
     * 获取文件名
     *
     * @return name - 文件名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置文件名
     *
     * @param name 文件名
     */
    public void setName(String name) {
        this.name = name;
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