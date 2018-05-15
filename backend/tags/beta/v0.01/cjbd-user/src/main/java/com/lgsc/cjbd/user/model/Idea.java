package com.lgsc.cjbd.user.model;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel(value="意见")
@JsonIgnoreProperties(value = {"createdBy", "updatedBy", "handler"})
public class Idea {
    /**
     * 意见编号
     */
    @Id
    @Column(name = "idea_id")
    @ApiModelProperty("意见编号")
    private Long ideaId;

    /**
     * 用户邮箱
     */
    @Column(name = "user_email")
    @ApiModelProperty("用户邮箱 最大50个字符")
    @Length(min=1,max=50)
    private String userEmail;

    /**
     * 意见
     */
    @ApiModelProperty("意见 最大长度1000个汉字")
    @Length(min=1,max=10000)
    private String idea;

    /**
     * 回复
     */
    @ApiModelProperty("回复内容 最大长度500个汉字")
    @Length(min=1,max=500)
    private String reply;

    /**
     * 状态 0=未回复 1=已回复
     */
    @ApiModelProperty("状态 0=未回复 1=已回复")
    @Digits(integer=6,fraction=0)
    private Short status;

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
     * 获取意见编号
     *
     * @return idea_id - 意见编号
     */
    public Long getIdeaId() {
        return ideaId;
    }

    /**
     * 设置意见编号
     *
     * @param ideaId 意见编号
     */
    public void setIdeaId(Long ideaId) {
        this.ideaId = ideaId;
    }

    /**
     * 获取用户邮箱
     *
     * @return user_email - 用户邮箱
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * 设置用户邮箱
     *
     * @param userEmail 用户邮箱
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    /**
     * 获取意见
     *
     * @return idea - 意见
     */
    public String getIdea() {
        return idea;
    }

    /**
     * 设置意见
     *
     * @param idea 意见
     */
    public void setIdea(String idea) {
        this.idea = idea;
    }

    /**
     * 获取回复
     *
     * @return reply - 回复
     */
    public String getReply() {
        return reply;
    }

    /**
     * 设置回复
     *
     * @param reply 回复
     */
    public void setReply(String reply) {
        this.reply = reply;
    }

    /**
     * 获取状态 0=未回复 1=已回复
     *
     * @return status - 状态 0=未回复 1=已回复
     */
    public Short getStatus() {
        return status;
    }

    /**
     * 设置状态 0=未回复 1=已回复
     *
     * @param status 状态 0=未回复 1=已回复
     */
    public void setStatus(Short status) {
        this.status = status;
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