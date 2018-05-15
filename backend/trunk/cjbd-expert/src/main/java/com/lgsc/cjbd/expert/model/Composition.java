package com.lgsc.cjbd.expert.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(value = { "createdBy", "createdAt", "updatedBy", "updatedAt", "handler" })
public class Composition {
	/**
	 * 著作编号
	 */
	@Id
	@Column(name = "composition_id")
	@ApiModelProperty("著作编号")
	@Digits(integer = 20, fraction = 0)
	private Long compositionId;

	/**
	 * 专家编号
	 */
	@Column(name = "expert_id")
	@ApiModelProperty("专家编号")
	@Digits(integer = 20, fraction = 0)
	private Long expertId;

	/**
	 * 著作名称
	 */
	@Column(name = "composition_name")
	@ApiModelProperty("著作名称，最大长度100")
	@Length(min = 0, max = 100)
	private String compositionName;

	/**
	 * 著作链接
	 */
	@Column(name = "composition_link")
	@ApiModelProperty("著作链接，最大长度300")
	@Length(min = 0, max = 300)
	private String compositionLink;

	/**
	 * 创建人
	 */
	@Column(name = "created_by")
	@ApiModelProperty("创建人 ")
	private String createdBy;

	/**
	 * 创建时间
	 */
	@Column(name = "created_at")
	@ApiModelProperty("创建时间 ")
	private Date createdAt;

	/**
	 * 最后修改人
	 */
	@Column(name = "updated_by")
	@ApiModelProperty("最后修改人")
	private String updatedBy;

	/**
	 * 最后修改时间
	 */
	@Column(name = "updated_at")
	@ApiModelProperty("最后修改时间 ")
	private Date updatedAt;

	/**
	 * 获取著作编号
	 *
	 * @return composition_id - 著作编号
	 */
	public Long getCompositionId() {
		return compositionId;
	}

	/**
	 * 设置著作编号
	 *
	 * @param compositionId
	 *            著作编号
	 */
	public void setCompositionId(Long compositionId) {
		this.compositionId = compositionId;
	}

	/**
	 * 获取专家编号
	 *
	 * @return expert_id - 专家编号
	 */
	public Long getExpertId() {
		return expertId;
	}

	/**
	 * 设置专家编号
	 *
	 * @param expertId
	 *            专家编号
	 */
	public void setExpertId(Long expertId) {
		this.expertId = expertId;
	}

	/**
	 * 获取著作名称
	 *
	 * @return composition_name - 著作名称
	 */
	public String getCompositionName() {
		return compositionName;
	}

	/**
	 * 设置著作名称
	 *
	 * @param compositionName
	 *            著作名称
	 */
	public void setCompositionName(String compositionName) {
		this.compositionName = compositionName;
	}

	/**
	 * 获取著作链接
	 *
	 * @return composition_link - 著作链接
	 */
	public String getCompositionLink() {
		return compositionLink;
	}

	/**
	 * 设置著作链接
	 *
	 * @param compositionLink
	 *            著作链接
	 */
	public void setCompositionLink(String compositionLink) {
		this.compositionLink = compositionLink;
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