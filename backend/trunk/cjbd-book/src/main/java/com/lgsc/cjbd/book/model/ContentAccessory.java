package com.lgsc.cjbd.book.model;

import java.util.Date;
import javax.persistence.*;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("内容附件")
@Table(name = "content_accessory")
@JsonIgnoreProperties(value = {"createdBy", "createdAt", "updatedBy", "updatedAt", "handler"})
public class ContentAccessory {
    /**
     * 内容附件编号
     */
    @Id
    @Column(name = "content_accessory_id")
    @ApiModelProperty("内容附件编号")
    private Long contentAccessoryId;

    /**
     * 内容编号
     */
    @Column(name = "content_id")
    @ApiModelProperty("内容编号")
    private Long contentId;

    /**
     * 文件名
     */
    @ApiModelProperty("文件名")
    private String name;

    /**
     * 文件大小
     */
    @ApiModelProperty("文件大小")
    private String size;

    /**
     * 附件地址
     */
    @ApiModelProperty("附件地址")
    @Column(name = "accessory_url")
    private String accessoryUrl;

    /**
     * 后缀名 如：.pdf .word
     */
    @ApiModelProperty("后缀名")
    private String suffix;

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
     * 获取内容附件编号
     *
     * @return content_accessory_id - 内容附件编号
     */
    public Long getContentAccessoryId() {
        return contentAccessoryId;
    }

    /**
     * 设置内容附件编号
     *
     * @param contentAccessoryId 内容附件编号
     */
    public void setContentAccessoryId(Long contentAccessoryId) {
        this.contentAccessoryId = contentAccessoryId;
    }

    /**
     * 获取内容编号
     *
     * @return content_id - 内容编号
     */
    public Long getContentId() {
        return contentId;
    }

    /**
     * 设置内容编号
     *
     * @param contentId 内容编号
     */
    public void setContentId(Long contentId) {
        this.contentId = contentId;
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
     * 获取文件大小
     *
     * @return size - 文件大小
     */
    public String getSize() {
        return size;
    }

    /**
     * 设置文件大小
     *
     * @param size 文件大小
     */
    public void setSize(String size) {
        this.size = size;
    }

    /**
     * 获取附件地址
     *
     * @return accessory_url - 附件地址
     */
    public String getAccessoryUrl() {
        return accessoryUrl;
    }

    /**
     * 设置附件地址
     *
     * @param accessoryUrl 附件地址
     */
    public void setAccessoryUrl(String accessoryUrl) {
        this.accessoryUrl = accessoryUrl;
    }

    /**
     * 获取后缀名 如：.pdf .word
     *
     * @return suffix - 后缀名 如：.pdf .word
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * 设置后缀名 如：.pdf .word
     *
     * @param suffix 后缀名 如：.pdf .word
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix;
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