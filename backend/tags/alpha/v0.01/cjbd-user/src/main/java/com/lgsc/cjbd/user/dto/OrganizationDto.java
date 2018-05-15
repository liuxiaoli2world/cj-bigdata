package com.lgsc.cjbd.user.dto;

import javax.persistence.Column;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;
@JsonIgnoreProperties(value = {"handler"})
public class OrganizationDto {
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
	@ApiModelProperty("机构名称")
	private String name;

	/**
	 * 机构代号
	 */
	@Column(name = "seq_num")
	@ApiModelProperty("机构代号")
	private Integer seqNum;
	/**
	 * 用户数量
	 */
	@ApiModelProperty("用户数量")
	private Integer countUser;
	

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	public Integer getCountUser() {
		return countUser;
	}

	public void setCountUser(Integer countUser) {
		this.countUser = countUser;
	}

    
}
