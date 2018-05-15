package com.lgsc.cjbd.book.model;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(value = { "createdBy", "createdAt", "updatedBy", "updatedAt", "handler" })
public class Channel {
	/**
	 * 图书分类编号
	 */
	@Id
	@Column(name = "channel_id")
	@ApiModelProperty("图书分类编号")
	private Long channelId;

	/**
	 * 父图书分类编号，根节点为0
	 */
	@Column(name = "parent_channel_id")
	@ApiModelProperty("父图书分类编号，根节点为0，最大长度20")
	@Digits(integer = 20, fraction = 0)
	private Long parentChannelId;

	/**
	 * 分类名称
	 */
	@ApiModelProperty("分类名称，最大长度10")
	@Length(min = 0, max = 10)
	@Column(name = "channel_name")
	private String channelName;

	/**
	 * 分类简介
	 */
	@ApiModelProperty("分类名称，最大长度100")
	@Length(min = 0, max = 100)
	@Column(name = "channel_desc")
	private String channelDesc;

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
	 * 获取图书分类编号
	 *
	 * @return channel_id - 图书分类编号
	 */
	public Long getChannelId() {
		return channelId;
	}

	/**
	 * 设置图书分类编号
	 *
	 * @param channelId
	 *            图书分类编号
	 */
	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	/**
	 * 获取父图书分类编号，根节点为0
	 *
	 * @return parent_channel_id - 父图书分类编号，根节点为0
	 */
	public Long getParentChannelId() {
		return parentChannelId;
	}

	/**
	 * 设置父图书分类编号，根节点为0
	 *
	 * @param parentChannelId
	 *            父图书分类编号，根节点为0
	 */
	public void setParentChannelId(Long parentChannelId) {
		this.parentChannelId = parentChannelId;
	}

	/**
	 * 获取分类名称
	 *
	 * @return channel_name - 分类名称
	 */
	public String getChannelName() {
		return channelName;
	}

	/**
	 * 设置分类名称
	 *
	 * @param channelName
	 *            分类名称
	 */
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	/**
	 * 获取分类简介
	 *
	 * @return channel_desc - 分类简介
	 */
	public String getChannelDesc() {
		return channelDesc;
	}

	/**
	 * 设置分类简介
	 *
	 * @param channelDesc
	 *            分类简介
	 */
	public void setChannelDesc(String channelDesc) {
		this.channelDesc = channelDesc;
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
	 * @param createdBy
	 *            创建人
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
	 * @param createdAt
	 *            创建时间
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
	 * @param updatedBy
	 *            最后修改人
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
	 * @param updatedAt
	 *            最后修改时间
	 */
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}