package com.lgsc.cjbd.expert.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lgsc.cjbd.expert.model.Composition;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(value = { "handler" })
public class ExpertDto {
	/**
	 * 专家编号
	 */
	@Id
	@Column(name = "expert_id")
	@ApiModelProperty("专家编号")
	@GeneratedValue(generator = "JDBC")
	private Long expertId;

	/**
	 * 姓名
	 */
	@Column(name = "real_name")
	@ApiModelProperty(value = "昵称，最大长度50", required = true)
	@Length(min = 1, max = 50)
	private String realName;

	/**
	 * 民族
	 */
	@ApiModelProperty(value = "民族", required = true)
	@Length(min = 1, max = 30)
	private String nation;

	/**
	 * 出生年月
	 */
	@ApiModelProperty(value = "出生年月", required = true)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date birthday;

	/**
	 * 职务
	 */
	@ApiModelProperty(value = "职务", required = true)
	@Length(min = 1, max = 50)
	private String duty;

	/**
	 * 专业领域
	 */
	@Column(name = "professional_field")
	@ApiModelProperty(value = "专业领域", required = true)
	@Length(min = 1, max = 50)
	private String professionalField;

	/**
	 * 分类名称
	 */
	@Transient
	@Column(name = "expert_classify")
	@ApiModelProperty(value = "分类名称", required = true)
	@Length(min = 1, max = 50)
	private String expertClassify;

	/**
	 * 是否专家推荐 0=否 1=是
	 */
	@Column(name = "is_recommend")
	@ApiModelProperty(value = "是否专家推荐 0=否 1=是", required = true)
	@Digits(integer = 6, fraction = 0)
	private Short isRecommend;

	/**
	 * 头像
	 */
	@Column(name = "image_url")
	@ApiModelProperty(value = "头像", required = true)
	private String imageUrl;

	/**
	 * 所属地区
	 */
	@ApiModelProperty(value = "所属地区", required = true)
	@Length(min = 1, max = 50)
	private String region;

	/**
	 * 简介
	 */
	@Column(name = "expert_desc")
	@ApiModelProperty(value = "简介", required = true)
	@Length(min = 1, max = 500)
	private String expertDesc;
	/**
	 * 专家著作
	 */
	@Column(name = "expert_composition")
	@ApiModelProperty(value = "专家著作", required = true)
	@Transient
	private List<Composition> expertComposition;

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
	 * 获取姓名
	 *
	 * @return real_name - 姓名
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * 设置姓名
	 *
	 * @param realName
	 *            姓名
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * 获取民族
	 *
	 * @return nation - 民族
	 */
	public String getNation() {
		return nation;
	}

	/**
	 * 设置民族
	 *
	 * @param nation
	 *            民族
	 */
	public void setNation(String nation) {
		this.nation = nation;
	}

	/**
	 * 获取出生年月
	 *
	 * @return birthday - 出生年月
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * 设置出生年月
	 *
	 * @param birthday
	 *            出生年月
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	/**
	 * 获取职务
	 *
	 * @return duty - 职务
	 */
	public String getDuty() {
		return duty;
	}

	/**
	 * 设置职务
	 *
	 * @param duty
	 *            职务
	 */
	public void setDuty(String duty) {
		this.duty = duty;
	}

	/**
	 * 获取专业领域
	 *
	 * @return professional_field - 专业领域
	 */
	public String getProfessionalField() {
		return professionalField;
	}

	/**
	 * 设置专业领域
	 *
	 * @param professionalField
	 *            专业领域
	 */
	public void setProfessionalField(String professionalField) {
		this.professionalField = professionalField;
	}

	/**
	 * 获取是否专家推荐 0=否 1=是
	 *
	 * @return is_recommend - 是否专家推荐 0=否 1=是
	 */
	public Short getIsRecommend() {
		return isRecommend;
	}

	/**
	 * 获取分类名称
	 * 
	 * @return
	 */
	public String getExpertClassify() {
		return expertClassify;
	}

	/**
	 * 设置分类名称
	 * 
	 * @param expertClassify
	 */
	public void setExpertClassify(String expertClassify) {
		this.expertClassify = expertClassify;
	}

	/**
	 * 设置是否专家推荐 0=否 1=是
	 *
	 * @param isRecommend
	 *            是否专家推荐 0=否 1=是
	 */
	public void setIsRecommend(Short isRecommend) {
		this.isRecommend = isRecommend;
	}

	/**
	 * 获取头像
	 *
	 * @return image_url - 头像
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * 设置头像
	 *
	 * @param imageUrl
	 *            头像
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	/**
	 * 获取所属地区
	 *
	 * @return region - 所属地区
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * 设置所属地区
	 *
	 * @param region
	 *            所属地区
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * 获取简介
	 *
	 * @return expert_desc - 简介
	 */
	public String getExpertDesc() {
		return expertDesc;
	}

	/**
	 * 设置简介
	 *
	 * @param expertDesc
	 *            简介
	 */
	public void setExpertDesc(String expertDesc) {
		this.expertDesc = expertDesc;
	}

	/**
	 * 获取著作
	 * 
	 * @return
	 */
	public List<Composition> getExpertComposition() {
		return expertComposition;
	}

	/**
	 * 设置著作
	 * 
	 * @param expertComposition
	 */
	public void setExpertComposition(List<Composition> expertComposition) {
		this.expertComposition = expertComposition;
	}
}
