package com.lgsc.cjbd.book.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(value = { "createdBy", "updatedBy", "handler" })
public class Multiitem {
	/**
	 * 多媒体项目编号
	 */
	@Id
	@Column(name = "multiitem_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty("多媒体项目编号")
	private Long multiitemId;

	/**
	 * 多媒体编号
	 */
	@ApiModelProperty("多媒体编号，最大长度20")
	@NotNull(message = "多媒体编号不能为空！")
	@Digits(integer = 20, fraction = 0)
	@Column(name = "multifile_id")
	private Long multifileId;

	/**
	 * 文件名
	 */
	@ApiModelProperty("文件名，最大长度100")
	@Length(min = 0, max = 100)
	private String name;

	/**
	 * 文件大小
	 */
	@ApiModelProperty("文件大小，最大长度20")
	@Length(min = 0, max = 20)
	private String size;

	/**
	 * 文件路径
	 */
	@ApiModelProperty("文件路径，最大长度300")
	@Length(min = 0, max = 300)
	private String path;

	/**
	 * 排序值 封面排序值默认为0 （第一张）不参与排序
	 */
	@ApiModelProperty("排序值 封面排序值默认为0 （第一张）不参与排序")
	@Digits(integer = 11, fraction = 0)
	private Integer rank;

	/**
	 * 使用场景 1=封面 2=普通资源
	 */
	@ApiModelProperty("使用场景 1=封面 2=普通资源")
	@Length(min = 0, max = 5)
	private String scene;

	/**
	 * 后缀名
	 */
	@ApiModelProperty("后缀名，最大长度10")
	@Length(min = 0, max = 10)
	private String suffix;

	/**
	 * 浏览量
	 */
	@ApiModelProperty("浏览量，最大长度11")
	@Digits(integer = 11, fraction = 0)
	private Integer pv;

	/**
	 * 是否首页推荐 0=否 1=是
	 */
	@ApiModelProperty("是否首页推荐 0=否 1=是")
	@Digits(integer = 6, fraction = 0)
	@Column(name = "is_index_recommend")
	private Short isIndexRecommend;

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
	 * 获取多媒体项目编号
	 *
	 * @return multiitem_id - 多媒体项目编号
	 */
	public Long getMultiitemId() {
		return multiitemId;
	}

	/**
	 * 设置多媒体项目编号
	 *
	 * @param multiitemId
	 *            多媒体项目编号
	 */
	public void setMultiitemId(Long multiitemId) {
		this.multiitemId = multiitemId;
	}

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
	 * @param name
	 *            文件名
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
	 * @param size
	 *            文件大小
	 */
	public void setSize(String size) {
		this.size = size;
	}

	/**
	 * 获取文件路径
	 *
	 * @return path - 文件路径
	 */
	public String getPath() {
		return path;
	}

	/**
	 * 设置文件路径
	 *
	 * @param path
	 *            文件路径
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * 获取排序值
	 *
	 * @return rank - 排序值
	 */
	public Integer getRank() {
		return rank;
	}

	/**
	 * 设置排序值
	 *
	 * @param rank
	 *            排序值
	 */
	public void setRank(Integer rank) {
		this.rank = rank;
	}

	/**
	 * 获取使用场景 1=封面 2=普通资源
	 *
	 * @return scene - 使用场景 1=封面 2=普通资源
	 */
	public String getScene() {
		return scene;
	}

	/**
	 * 设置使用场景 1=封面 2=普通资源
	 *
	 * @param scene
	 *            使用场景 1=封面 2=普通资源
	 */
	public void setScene(String scene) {
		this.scene = scene;
	}

	/**
	 * 获取后缀名
	 *
	 * @return suffix - 后缀名
	 */
	public String getSuffix() {
		return suffix;
	}

	/**
	 * 设置后缀名
	 *
	 * @param suffix
	 *            后缀名
	 */
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	/**
	 * 获取浏览量
	 *
	 * @return pv - 浏览量
	 */
	public Integer getPv() {
		return pv;
	}

	/**
	 * 设置浏览量
	 *
	 * @param pv
	 *            浏览量
	 */
	public void setPv(Integer pv) {
		this.pv = pv;
	}

	/**
	 * 获取是否首页推荐 0=否 1=是
	 *
	 * @return is_index_recommend - 是否首页推荐 0=否 1=是
	 */
	public Short getIsIndexRecommend() {
		return isIndexRecommend;
	}

	/**
	 * 设置是否首页推荐 0=否 1=是
	 *
	 * @param isIndexRecommend
	 *            是否首页推荐 0=否 1=是
	 */
	public void setIsIndexRecommend(Short isIndexRecommend) {
		this.isIndexRecommend = isIndexRecommend;
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