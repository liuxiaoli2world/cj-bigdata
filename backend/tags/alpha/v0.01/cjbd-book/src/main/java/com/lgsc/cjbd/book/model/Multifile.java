package com.lgsc.cjbd.book.model;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(value = { "createdBy", "createdAt", "updatedBy", "updatedAt", "handler" })
public class Multifile {
	/**
	 * 多媒体编号
	 */
	@Id
	@Column(name = "multifile_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty("多媒体编号")
	private Long multifileId;

	/**
	 * 资源类型 pic=图片 music=音频 video=视频 pdf=pdf文件 txt=txt文件
	 */
	@ApiModelProperty("资源类型 pic=图片 music=音频 video=视频 pdf=pdf文件 txt=txt文件")
	@NotBlank(message = "资源类型不能为空！")
	@Length(min = 0, max = 10)
	@Column(name = "multi_type")
	private String multiType;

	/**
	 * 标题
	 */
	@ApiModelProperty("标题，最大长度30")
	@Length(min = 0, max = 30)
	private String title;

	/**
	 * 资源简介
	 */
	@ApiModelProperty("资源简介，最大长度300")
	@Length(min = 0, max = 300)
	@Column(name = "multi_desc")
	private String multiDesc;

	/**
	 * 全书单价
	 */
	@ApiModelProperty("全书单价")
	@Digits(integer = 6, fraction = 4)
	@Column(name = "full_price")
	private Double fullPrice;

	/**
	 * 是否提供下载
	 */
	@ApiModelProperty("是否提供下载")
	@Digits(integer = 6, fraction = 0)
	@Column(name = "is_download")
	private Short isDownload;

	/**
	 * 资源详情
	 */
	@ApiModelProperty("资源详情，最大长度500")
	@Length(min = 0, max = 500)
	private String detail;

	/**
	 * 使用场景 1=首页
	 */
	@ApiModelProperty("使用场景 1=首页，最大长度5")
	@Length(min = 0, max = 5)
	private String scene;
	
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
	 * 获取多媒体编号
	 *
	 * @return multifile_id - 多媒体编号
	 */
	public Long getMultifileId() {
		return multifileId;
	}

	/**
	 * 设置多媒体编号
	 *
	 * @param multifileId
	 *            多媒体编号
	 */
	public void setMultifileId(Long multifileId) {
		this.multifileId = multifileId;
	}

	/**
	 * 获取资源类型 pic=图片 music=音频 video=视频 pdf=pdf文件 txt=txt文件
	 *
	 * @return multi_type - 资源类型 pic=图片 music=音频 video=视频 pdf=pdf文件 txt=txt文件
	 */
	public String getMultiType() {
		return multiType;
	}

	/**
	 * 设置资源类型 pic=图片 music=音频 video=视频 pdf=pdf文件 txt=txt文件
	 *
	 * @param multiType
	 *            资源类型 pic=图片 music=音频 video=视频 pdf=pdf文件 txt=txt文件
	 */
	public void setMultiType(String multiType) {
		this.multiType = multiType;
	}

	/**
	 * 获取标题
	 *
	 * @return title - 标题
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置标题
	 *
	 * @param title
	 *            标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 获取资源简介
	 *
	 * @return multi_desc - 资源简介
	 */
	public String getMultiDesc() {
		return multiDesc;
	}

	/**
	 * 设置资源简介
	 *
	 * @param multiDesc
	 *            资源简介
	 */
	public void setMultiDesc(String multiDesc) {
		this.multiDesc = multiDesc;
	}

	/**
	 * 获取全书单价
	 *
	 * @return full_price - 全书单价
	 */
	public Double getFullPrice() {
		return fullPrice;
	}

	/**
	 * 设置全书单价
	 *
	 * @param fullPrice
	 *            全书单价
	 */
	public void setFullPrice(Double fullPrice) {
		this.fullPrice = fullPrice;
	}

	/**
	 * 获取是否提供下载
	 *
	 * @return is_download - 是否提供下载
	 */
	public Short getIsDownload() {
		return isDownload;
	}

	/**
	 * 设置是否提供下载
	 *
	 * @param isDownload
	 *            是否提供下载
	 */
	public void setIsDownload(Short isDownload) {
		this.isDownload = isDownload;
	}

	/**
	 * 获取资源详情
	 *
	 * @return detail - 资源详情
	 */
	public String getDetail() {
		return detail;
	}

	/**
	 * 设置资源详情
	 *
	 * @param detail
	 *            资源详情
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	/**
	 * 获取使用场景
	 * @return
	 */
	public String getScene() {
		return scene;
	}

	/**
	 * 设置使用场景 1=首页
	 * @param scene
	 */
	public void setScene(String scene) {
		this.scene = scene;
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