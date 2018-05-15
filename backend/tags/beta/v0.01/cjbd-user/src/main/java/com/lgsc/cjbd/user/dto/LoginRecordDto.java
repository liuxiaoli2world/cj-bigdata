package com.lgsc.cjbd.user.dto;

import java.util.Date;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 登录状态dto
 * @author 罗天宇
 *
 */
@JsonIgnoreProperties(value = {"handler"})
public class LoginRecordDto {
	

    /**
     * 登陆时间
     */
    @Column(name = "login_time")
    private Date loginTime;
    
    /**
     * 登录ip
     */
    @Column(name = "login_ip")
    private String loginIp;
    /**
     * 地址
     */
    private String address;
    
	
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	public String getLoginIp() {
		return loginIp;
	}
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
    
    
    
}
