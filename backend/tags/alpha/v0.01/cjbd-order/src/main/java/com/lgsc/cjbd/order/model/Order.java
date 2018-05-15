package com.lgsc.cjbd.order.model;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(value = {"createdBy", "createdAt", "updatedBy", "updatedAt", "handler"})
@Table(name="`order`")
public class Order {
    /**
     * 订单id
     */
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
    @ApiModelProperty("订单id")
	private Long orderId;

	/**
	 * 订单号
	 */
    @ApiModelProperty("订单号，最大长度50")
	@NotBlank(message = "订单号不能为空！")
	@Length(min = 0, max = 50)
	@Column(name = "order_no")
	private String orderNo;

	/**
	 * 用户编号
	 */
    @ApiModelProperty("用户编号，最大长度20")
    @NotNull(message = "用户编号不能为空！")
    @Digits(integer = 20, fraction = 0)
	@Column(name = "user_id")
	private Long userId;

	/**
	 * 商品类型 book=图书 vip=会员
	 */
    @ApiModelProperty("商品类型 book=图书 vip=会员，最大长度20")
	@NotBlank(message = "商品类型不能为空！")
	@Length(min = 0, max = 20)
	@Column(name = "good_type")
	private String goodType;

	/**
	 * 用户名
	 */
    @ApiModelProperty("用户名，最大长度50")
	@NotBlank(message = "用户名不能为空！")
	@Length(min = 0, max = 50)
	private String username;

	/**
	 * 邮箱
	 */
    @ApiModelProperty("邮箱，最大长度100")
	@NotBlank(message = "邮箱不能为空！")
	@Length(min = 0, max = 100)
    @Email
	private String email;

	/**
	 * 数量
	 */
    @ApiModelProperty("数量，最大长度11")
    @NotNull(message = "数量不能为空！")
    @Digits(integer = 11, fraction = 0)
	private Integer num;

	/**
	 * 订单金额
	 */
    @ApiModelProperty("订单金额")
    @NotNull(message = "订单金额不能为空！")
    @Digits(integer = 10, fraction = 4)
	private Double amount;

	/**
	 * 支付状态 0=待支付 1=已支付 2=支付失败
	 */
    @ApiModelProperty("支付状态 0=待支付 1=已支付 2=支付失败")
    @NotNull(message = "支付状态不能为空！")
    @Digits(integer = 6, fraction = 0)
	@Column(name = "pay_status")
	private Short payStatus;

	/**
	 * 支付时间
	 */
	@ApiModelProperty("支付时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "pay_time")
	private Date payTime;
	
	/**
	 * 支付二维码地址
	 */
    @ApiModelProperty("支付二维码地址")
	@Length(min = 0, max = 500)
	@Column(name = "pay_code_url")
	private String payCodeUrl;

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
	 * 获取订单id
	 *
	 * @return order_id - 订单id
	 */
	public Long getOrderId() {
		return orderId;
	}

	/**
	 * 设置订单id
	 *
	 * @param orderId
	 *            订单id
	 */
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
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
	 * @param orderNo
	 *            订单号
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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
	 * @param userId
	 *            用户编号
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * 获取商品类型 book=图书 vip=会员
	 *
	 * @return good_type - 商品类型 book=图书 vip=会员
	 */
	public String getGoodType() {
		return goodType;
	}

	/**
	 * 设置商品类型 book=图书 vip=会员
	 *
	 * @param goodType
	 *            商品类型 book=图书 vip=会员
	 */
	public void setGoodType(String goodType) {
		this.goodType = goodType;
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
	 * 获取数量
	 *
	 * @return num - 数量
	 */
	public Integer getNum() {
		return num;
	}

	/**
	 * 设置数量
	 *
	 * @param num
	 *            数量
	 */
	public void setNum(Integer num) {
		this.num = num;
	}

	/**
	 * 获取订单金额
	 *
	 * @return amount - 订单金额
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * 设置订单金额
	 *
	 * @param amount
	 *            订单金额
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
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
	 * @param payStatus
	 *            支付状态 0=待支付 1=已支付 2=支付失败
	 */
	public void setPayStatus(Short payStatus) {
		this.payStatus = payStatus;
	}

	public String getPayCodeUrl() {
		return payCodeUrl;
	}

	public void setPayCodeUrl(String payCodeUrl) {
		this.payCodeUrl = payCodeUrl;
	}

	/**
	 * 获取支付时间
	 *
	 * @return pay_time - 支付时间
	 */
	public Date getPayTime() {
		return payTime;
	}

	/**
	 * 设置支付时间
	 *
	 * @param payTime
	 *            支付时间
	 */
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
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

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", orderNo=" + orderNo + ", userId=" + userId + ", goodType=" + goodType
				+ ", username=" + username + ", email=" + email + ", num=" + num + ", amount=" + amount + ", payStatus="
				+ payStatus + ", payTime=" + payTime + ", createdBy=" + createdBy + ", createdAt=" + createdAt
				+ ", updatedBy=" + updatedBy + ", updatedAt=" + updatedAt + "]";
	}
}