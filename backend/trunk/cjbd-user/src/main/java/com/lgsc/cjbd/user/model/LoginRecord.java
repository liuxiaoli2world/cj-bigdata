package com.lgsc.cjbd.user.model;

import java.util.Date;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = {"createdBy", "createdAt", "updatedBy", "updatedAt", "handler"})
@Table(name = "login_record")
public class LoginRecord {
    /**
     * 登录信息编号
     */
    @Id
    @Column(name = "login_record_id")
    private Long loginRecordId;

    /**
     * 用户编号
     */
    @Column(name = "user_id")
    private Long userId;

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
     * 获取登录信息编号
     *
     * @return login_record_id - 登录信息编号
     */
    public Long getLoginRecordId() {
        return loginRecordId;
    }

    /**
     * 设置登录信息编号
     *
     * @param loginRecordId 登录信息编号
     */
    public void setLoginRecordId(Long loginRecordId) {
        this.loginRecordId = loginRecordId;
    }

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
     * @param userId 用户编号
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取登陆时间
     *
     * @return login_time - 登陆时间
     */
    public Date getLoginTime() {
        return loginTime;
    }

    /**
     * 设置登陆时间
     *
     * @param loginTime 登陆时间
     */
    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    /**
     * 获取登录ip
     *
     * @return login_ip - 登录ip
     */
    public String getLoginIp() {
        return loginIp;
    }

    /**
     * 设置登录ip
     *
     * @param loginIp 登录ip
     */
    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    /**
     * 获取地址
     *
     * @return address - 地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置地址
     *
     * @param address 地址
     */
    public void setAddress(String address) {
        this.address = address;
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
     * @param createdBy 创建人
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
     * @param createdAt 创建时间
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
     * @param updatedBy 最后修改人
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
     * @param updatedAt 最后修改时间
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
    
	public LoginRecord() {
		
	}

	public LoginRecord(Long userId, Date loginTime, String loginIp, String address) {
		this.userId = userId;
		this.loginTime = loginTime;
		this.loginIp = loginIp;
		this.address = address;
	}
    
    
    
    
    
    
    
    
}