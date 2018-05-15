package com.lgsc.cjbd.user.model;

import java.util.Date;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = {"createdBy", "createdAt", "updatedBy", "updatedAt", "handler"})
@Table(name = "event_user_vip")
public class EventUserVip {
    /**
     * 事件编号
     */
    @Id
    @Column(name = "event_user_vip_id")
    private Long eventUserVipId;

    /**
     * 会员商品编号
     */
    @Column(name = "vip_id")
    private Long vipId;

    /**
     * 用户编号
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 会员开始时间
     */
    @Column(name = "begin_date")
    private Date beginDate;

    /**
     * 会员到期时间
     */
    @Column(name = "end_date")
    private Date endDate;

    /**
     * 支付状态 0=待支付 1=已支付 2=支付失败
     */
    @Column(name = "pay_status")
    private Short payStatus;

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
     * 获取事件编号
     *
     * @return event_user_vip_id - 事件编号
     */
    public Long getEventUserVipId() {
        return eventUserVipId;
    }

    /**
     * 设置事件编号
     *
     * @param eventUserVipId 事件编号
     */
    public void setEventUserVipId(Long eventUserVipId) {
        this.eventUserVipId = eventUserVipId;
    }

    /**
     * 获取会员商品编号
     *
     * @return vip_id - 会员商品编号
     */
    public Long getVipId() {
        return vipId;
    }

    /**
     * 设置会员商品编号
     *
     * @param vipId 会员商品编号
     */
    public void setVipId(Long vipId) {
        this.vipId = vipId;
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
     * 获取会员开始时间
     *
     * @return begin_date - 会员开始时间
     */
    public Date getBeginDate() {
        return beginDate;
    }

    /**
     * 设置会员开始时间
     *
     * @param beginDate 会员开始时间
     */
    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    /**
     * 获取会员到期时间
     *
     * @return end_date - 会员到期时间
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * 设置会员到期时间
     *
     * @param endDate 会员到期时间
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * 获取支付状态 0=待支付 1=已支付 2=支付失败
     *
     * @return pay_status - 支付状态 0=待支付 1=已支付 2=支付失败
     */
    public Short getPayStatus() {
        return payStatus;
    }

    /**
     * 设置支付状态 0=待支付 1=已支付 2=支付失败
     *
     * @param payStatus 支付状态 0=待支付 1=已支付 2=支付失败
     */
    public void setPayStatus(Short payStatus) {
        this.payStatus = payStatus;
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