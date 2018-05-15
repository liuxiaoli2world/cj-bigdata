package com.lgsc.cjbd.user.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "用户")
@JsonIgnoreProperties(value = { "createdBy", "updatedBy", "salt", "handler" })
public class User {
	/**
	 * 用户编号
	 */
	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty("用户编号")
	private Long userId;

	/**
	 * 用户名
	 */
	@ApiModelProperty("用户名，最大长度50")
	@NotBlank(message = "用户名不能为空！")
	@Length(min = 1, max = 50)
	private String username;

	/**
	 * 密码
	 */
	@ApiModelProperty("密码")
	@NotBlank(message = "密码不能为空！")
	@Length(min = 1, max = 500)
	private String password;

	/**
	 * 盐
	 */
	@Length(min = 1, max = 32)
	private String salt;

	/**
	 * 邮箱
	 */
	@ApiModelProperty("邮箱，最大长度100")
	@Length(min = 1, max = 100)
	@Email
	private String email;

	/**
	 * 手机号
	 */
	@ApiModelProperty("手机号，最大位数11")
	@Digits(integer = 11, fraction = 0)
	private Long phone;
    
    @ApiModelProperty("用户昵称")
    @Column(name="nick_name")
    @Length(min=1,max=50)
    private String nickName;

	/**
	 * 姓名
	 */
	@ApiModelProperty("姓名，最大长度50")
	@Column(name = "real_name")
	@Length(min = 1, max = 50)
	private String realName;

	/**
	 * 是否可用 0=不可用 1=可用
	 */
	@ApiModelProperty("是否可用 0=不可用 1=可用")
	@Digits(integer = 6, fraction = 0)
	private Short enable;

	/**
	 * 机构代号
	 */
	@ApiModelProperty("机构代号，最大位数11")
	@Column(name = "seq_num")
	private Integer seqNum;

	/**
	 * 创建人
	 */
	@Column(name = "created_by")
	@Length(min = 1, max = 30)
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
	@Column(name = "updated_by")
	@Length(min = 1, max = 30)
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
	 * 首页 token创建时间
	 */
	@ApiModelProperty("首页token创建时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "token_created_at")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date tokenCreatedAt;
	/**
	 * 后台token的创建时间
	 */
	@ApiModelProperty("首页token创建时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "bg_token_created_at")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date bgTokenCreatedAt;
	/**
	 * 获取用户编号
	 *
	 * @return user_id - 用户编号
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * 设置用户编号
	 *
	 * @param userId
	 *            用户编号
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * 获取用户名
	 *
	 * @return username - 用户名
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 设置用户名
	 *
	 * @param username
	 *            用户名
	 */
	public void setUsername(String username) {
		this.username = username;
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
	 * 获取邮箱
	 *
	 * @return email - 邮箱
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 设置邮箱
	 *
	 * @param email
	 *            邮箱
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 获取手机号
	 *
	 * @return phone - 手机号
	 */
	public Long getPhone() {
		return phone;
	}

	/**
	 * 设置手机号
	 *
	 * @param phone
	 *            手机号
	 */
	public void setPhone(Long phone) {
		this.phone = phone;
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
	 * 获取是否可用 0=不可用 1=可用
	 *
	 * @return enable - 是否可用 0=不可用 1=可用
	 */
	public Short getEnable() {
		return enable;
	}

	/**
	 * 设置是否可用 0=不可用 1=可用
	 *
	 * @param enable
	 *            是否可用 0=不可用 1=可用
	 */
	public void setEnable(Short enable) {
		this.enable = enable;
	}

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
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

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Date getTokenCreatedAt() {
		return tokenCreatedAt;
	}

	public void setTokenCreatedAt(Date tokenCreatedAt) {
		this.tokenCreatedAt = tokenCreatedAt;
	}

	public Date getBgTokenCreatedAt() {
		return bgTokenCreatedAt;
	}

	public void setBgTokenCreatedAt(Date bgTokenCreatedAt) {
		this.bgTokenCreatedAt = bgTokenCreatedAt;
	}
	
	
}