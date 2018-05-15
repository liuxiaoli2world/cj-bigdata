package com.lgsc.cjbd.order.model;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(value = {"createdBy", "createdAt", "updatedBy", "updatedAt", "beginDate", "endDate", "handler"})
@Table(name = "his_vip")
public class HisVip {
    /**
     * 会员商品历史信息编号
     */
    @Id
    @Column(name = "his_vip_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("会员商品历史信息编号")
    private Long hisVipId;

	/**
	 * 订单号
	 */
    @ApiModelProperty("订单号，最大长度50")
	@NotBlank(message = "订单号不能为空！")
	@Length(min = 0, max = 50)
	@Column(name = "order_no")
	private String orderNo;

    /**
     * 会员商品编号
     */
    @ApiModelProperty("会员商品编号，最大长度20")
    @NotNull(message = "会员商品编号不能为空！")
    @Digits(integer = 20, fraction = 0)
    @Column(name = "vip_id")
    private Long vipId;

    /**
     * 购买时长
     */
    @ApiModelProperty("购买时长，最大长度5")
	@Length(min = 0, max = 5)
    private String duration;

    /**
     * 会员开始时间
     */
    @ApiModelProperty(value = "会员开始时间", hidden = true)
 	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
 	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "begin_date")
    private Date beginDate;

    /**
     * 会员到期时间
     */
    @ApiModelProperty(value = "会员到期时间", hidden = true)
 	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
 	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "end_date")
    private Date endDate;

    /**
     * 商品名称
     */
    @ApiModelProperty("商品名称，最大长度20")
	@Length(min = 0, max = 20)
    private String name;

    /**
     * 商品图
     */
    @ApiModelProperty("商品图，最大长度300")
	@Length(min = 0, max = 300)
    @Column(name = "image_url")
    private String imageUrl;

    /**
     * 单价
     */
    @ApiModelProperty("单价")
    @NotNull(message = "单价不能为空！")
    @Digits(integer = 10, fraction = 4)
    private Double price;

    /**
	 * 创建人
	 */
	@ApiModelProperty("创建人")
	@Column(name = "created_by")
	@Length(min = 0, max = 30)
	private String createdBy;

	/**
	 * 创建时间
	 */
	@ApiModelProperty("创建时间")
	@NotNull(message = "创建时间不能为空！")
	@Column(name = "created_at")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createdAt;

	/**
	 * 最后修改人
	 */
	@ApiModelProperty("最后修改人")
	@Column(name = "updated_by")
	@Length(min = 0, max = 30)
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
     * 获取会员商品历史信息编号
     *
     * @return his_vip_id - 会员商品历史信息编号
     */
    public Long getHisVipId() {
        return hisVipId;
    }

    /**
     * 设置会员商品历史信息编号
     *
     * @param hisVipId 会员商品历史信息编号
     */
    public void setHisVipId(Long hisVipId) {
        this.hisVipId = hisVipId;
    }

    /**
     * 获取订单号
     *
     * @return order_no - 订单号
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 设置订单号
     *
     * @param orderNo 订单号
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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
     * 获取购买时长
     *
     * @return duration - 购买时长
     */
    public String getDuration() {
        return duration;
    }

    /**
     * 设置购买时长
     *
     * @param duration 购买时长
     */
    public void setDuration(String duration) {
        this.duration = duration;
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
     * 获取商品名称
     *
     * @return name - 商品名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置商品名称
     *
     * @param name 商品名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取商品图
     *
     * @return image_url - 商品图
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * 设置商品图
     *
     * @param imageUrl 商品图
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * 获取单价
     *
     * @return price - 单价
     */
    public Double getPrice() {
        return price;
    }

    /**
     * 设置单价
     *
     * @param price 单价
     */
    public void setPrice(Double price) {
        this.price = price;
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