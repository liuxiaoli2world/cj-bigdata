package com.lgsc.cjbd.user.model;

import java.util.Date;
import javax.persistence.*;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="异常登录设置")
@JsonIgnoreProperties(value = {"createdBy", "createdAt", "updatedBy", "updatedAt", "handler"})
@Table(name = "exception_login")
public class ExceptionLogin {
    /**
     * 异常登录设置编号
     */
    @Id
    @Column(name = "exception_login_id")
    @ApiModelProperty("异常登录设置编号")
    private Long exceptionLoginId;

    /**
     * 登录ip个数
     */
    @Column(name = "ip_num")
    @ApiModelProperty("登录ip个数 11位int")
    private Integer ipNum;

    /**
     * 是否发送邮件
     */
    @Column(name = "is_send_email")
    @ApiModelProperty("是否发送邮件")
    private Short isSendEmail;

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
    @ApiModelProperty("最近登录时间")
    @Column(name = "updated_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;
    /**
     * 获取异常登录设置编号
     *
     * @return exception_login_id - 异常登录设置编号
     */
    public Long getExceptionLoginId() {
        return exceptionLoginId;
    }

    /**
     * 设置异常登录设置编号
     *
     * @param exceptionLoginId 异常登录设置编号
     */
    public void setExceptionLoginId(Long exceptionLoginId) {
        this.exceptionLoginId = exceptionLoginId;
    }

    /**
     * 获取登录ip个数
     *
     * @return ip_num - 登录ip个数
     */
    public Integer getIpNum() {
        return ipNum;
    }

    /**
     * 设置登录ip个数
     *
     * @param ipNum 登录ip个数
     */
    public void setIpNum(Integer ipNum) {
        this.ipNum = ipNum;
    }

    /**
     * 获取是否发送邮件
     *
     * @return is_send_email - 是否发送邮件
     */
    public Short getIsSendEmail() {
        return isSendEmail;
    }

    /**
     * 设置是否发送邮件
     *
     * @param isSendEmail 是否发送邮件
     */
    public void setIsSendEmail(Short isSendEmail) {
        this.isSendEmail = isSendEmail;
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
}