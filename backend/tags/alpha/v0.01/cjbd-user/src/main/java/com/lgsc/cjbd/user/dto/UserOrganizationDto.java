package com.lgsc.cjbd.user.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

/**
 * 机构会员详细dto
 * @author 罗天宇
 *
 */
@JsonIgnoreProperties(value = {"handler"})
public class UserOrganizationDto {
	
	/**
     * 用户编号
     */
    @Id
    @Column(name = "user_id")
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
     * 邮箱
     */
    @ApiModelProperty("邮箱，最大长度100")
    @Length(min = 1, max = 100)
    @Email
    private String email;
    
    /**
	 * 是否可用 0=不可用 1=可用
	 */
	@ApiModelProperty("是否可用 0=不可用 1=可用")
	@Digits(integer = 6, fraction = 0)
	private Short enable;
	/**
	 * 会员状态
	 */
	@ApiModelProperty("会员状态")
	private  String vipStatus;
	/**
	 * 最近登录时间
	 */
	@ApiModelProperty("最近登录时间")
	@Column(name = "updated_at")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastLoginTime;
	/**
	 * 创建时间
	 */
	@ApiModelProperty("创建时间")
	@Column(name = "created_at")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createdAt;
	/**
	 * 最近登录ip个数
	 */
	private Integer countIp;
	/**
	 * 是否异常1异常 0代表正常
	 */
	@ApiModelProperty("是否异常1异常 0代表正常")
	private Integer  isException;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Short getEnable() {
		return enable;
	}
	public void setEnable(Short enable) {
		this.enable = enable;
	}
	public String getVipStatus() {
		return vipStatus;
	}
	public void setVipStatus(String vipStatus) {
		this.vipStatus = vipStatus;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Integer getCountIp() {
		return countIp;
	}
	public void setCountIp(Integer countIp) {
		this.countIp = countIp;
	}
	public Integer getIsException() {
		return isException;
	}
	public void setIsException(Integer isException) {
		this.isException = isException;
	}

	
}
