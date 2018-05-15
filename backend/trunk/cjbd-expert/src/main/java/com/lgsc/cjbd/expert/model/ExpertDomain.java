package com.lgsc.cjbd.expert.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(value = { "createdBy", "createdAt", "updatedBy", "updatedAt", "handler" })
@Table(name = "expert_domain")
public class ExpertDomain {
	/**
	 * 专家分类关系编号
	 */
	@Id
	@Column(name = "expert_domain_id")
	@ApiModelProperty("专家分类关系编号")
	private Long expertDomainId;

	/**
	 * 专家分类编号
	 */
	@Column(name = "domain_id")
	@ApiModelProperty("专家分类编号")
	private Long domainId;

	/**
	 * 专家编号
	 */
	@Column(name = "expert_id")
	@ApiModelProperty("专家编号")
	private Long expertId;

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
	 * 获取专家分类编号
	 *
	 * @return expert_domain_id - 专家分类编号
	 */
	public Long getExpertDomainId() {
		return expertDomainId;
	}

	/**
	 * 设置专家分类编号
	 *
	 * @param expertDomainId
	 *            专家分类编号
	 */
	public void setExpertDomainId(Long expertDomainId) {
		this.expertDomainId = expertDomainId;
	}

	/**
	 * 获取专家分类编号
	 *
	 * @return domain_id - 专家分类编号
	 */
	public Long getDomainId() {
		return domainId;
	}

	/**
	 * 设置专家分类编号
	 *
	 * @param domainId
	 *            专家分类编号
	 */
	public void setDomainId(Long domainId) {
		this.domainId = domainId;
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