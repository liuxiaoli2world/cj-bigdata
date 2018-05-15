package com.lgsc.cjbd.user.dto;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(value = {"handler"})
public class UserResponseDto {
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
     * 手机号
     */
    @ApiModelProperty("手机号，最大位数11")
    @Digits(integer = 11, fraction = 0)
    private Long phone;

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
    @Digits(integer = 11, fraction = 0)
    private Integer seqNum;
    
    @ApiModelProperty("用户昵称")
    @NotBlank(message="用户昵称不能为空")
    @Column(name="nick_name")
    @Length(min=1,max=50)
    private String nickName;

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

	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Short getEnable() {
		return enable;
	}

	public void setEnable(Short enable) {
		this.enable = enable;
	}

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
    

}
