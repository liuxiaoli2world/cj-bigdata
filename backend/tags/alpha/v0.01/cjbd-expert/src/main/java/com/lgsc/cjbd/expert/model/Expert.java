package com.lgsc.cjbd.expert.model;

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

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(value = { "createdBy", "createdAt", "updatedBy", "updatedAt", "handler" })
public class Expert {
	/**
	 * 专家编号
	 */
	@Id
	@Column(name = "expert_id")
	@ApiModelProperty("专家编号")
	@GeneratedValue(generator = "JDBC")
	private Long expertId;

	/**
	 * 登录名
	 */
	@ApiModelProperty(value = "登陆名，最大长度50", required = true)
	@Length(min = 0, max = 50)
	@Transient
	private String loginName;

	/**
	 * 密码
	 */
	@ApiModelProperty(value = "登陆密码", required = true)
	@Length(min = 0, max = 500)
	@Transient
	private String password;

	/**
	 * 民族
	 */
	@ApiModelProperty(value = "民族，最大长度10", required = true)
	@Length(min = 0, max = 10)
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
	@ApiModelProperty(value = "职务，最大长度20", required = true)
	@Length(min = 0, max = 20)
	private String duty;

	/**
	 * 专业领域
	 */
	@Column(name = "professional_field")
	@ApiModelProperty(value = "专业领域，最大长度30", required = true)
	@Length(min = 0, max = 30)
	private String professionalField;

	/**
	 * 分类名称
	 */
	@Transient
	@Column(name = "expert_classify")
	@ApiModelProperty(value = "分类名称，最大长度50", required = true)
	@Length(min = 0, max = 50)
	private String expertClassify;

	/**
	 * 是否专家推荐 0=否 1=是
	 */
	@Column(name = "is_recommend")
	@ApiModelProperty(value = "是否专家推荐 0=否 1=是", required = true)
	@Digits(integer = 1, fraction = 0)
	private Short isRecommend;

	/**
	 * 头像
	 */
	@Column(name = "image_url")
	@ApiModelProperty(value = "头像，最大长度300", required = true)
	@Length(min = 0, max = 300)
	private String imageUrl;

	/**
	 * 所属地区
	 */
	@ApiModelProperty(value = "所属地区，最大长度20", required = true)
	@Length(min = 0, max = 20)
	private String region;

	/**
	 * 简介
	 */
	@Column(name = "expert_desc")
	@ApiModelProperty(value = "简介，最大长度1500", required = true)
	@Length(min = 0, max = 1500)
	private String expertDesc;
	/**
	 * 专家著作
	 */
	@Column(name = "expert_composition")
	@ApiModelProperty(value = "专家著作", required = true)
	@Transient
	private List<Composition> expertComposition;

	/**
	 * 创建人
	 */
	@Column(name = "created_by")
	private String createdBy;

	/**
	 * 创建时间
	 */
	@Column(name = "created_at")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updatedAt;
	
	/**
	 * 用户编号
	 */
	@Column(name = "user_id")
	private Long userId;
	/**
	 * 姓名
	 */
	@Column(name = "real_name")
	@ApiModelProperty(value = "姓名，最大长度50", required = true)
	@Length(min = 0, max = 50)
	private String realName;

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
	 * 获取登录名
	 *
	 * @return login_name - 登录名
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * 设置登录名
	 *
	 * @param loginName
	 *            登录名
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * 获取密码
	 *
	 * @return password - 密码
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 设置密码
	 *
	 * @param password
	 *            密码
	 */
	public void setPassword(String password) {
		this.password = password;
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

	/**
	 * 获取用户编号
	 * 
	 * @return
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * 设置用户编号
	 * 
	 * @param userId
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

}