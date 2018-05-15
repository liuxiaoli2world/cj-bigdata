package com.lgsc.cjbd.book.model;

import java.util.Date;
import javax.persistence.*;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(value = {"createdBy", "createdAt", "updatedBy", "updatedAt", "handler"})
@Table(name = "event_user_book")
public class EventUserBook {
    /**
     * 事件编号
     */
    @Id
    @Column(name = "event_user_book_id")
    private Long eventUserBookId;

    /**
     * 用户编号
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 所在目录
     */
    @Column(name = "book_dir_id")
    private Long bookDirId;

    /**
	 * isbn = realIsbn|bookId 
	 */
	@ApiModelProperty("isbn(realIsbn|bookId)号,最大长度50")
	@NotBlank(message = "isbn不能为空！")
	@Length(min = 1, max = 50)
	private String isbn;

    /**
     * 支付状态 0=待支付 1=已支付 2=支付失败
     */
    @Column(name = "pay_status")
    private Short payStatus;

    /**
     * 推送到用户图书的状态 0=不处理 1=待处理 2=处理中 3=处理成功 4=处理失败
     */
    @Column(name = "push_status")
    private Short pushStatus;

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
     * 获取事件编号
     *
     * @return event_user_book_id - 事件编号
     */
    public Long getEventUserBookId() {
        return eventUserBookId;
    }

    /**
     * 设置事件编号
     *
     * @param eventUserBookId 事件编号
     */
    public void setEventUserBookId(Long eventUserBookId) {
        this.eventUserBookId = eventUserBookId;
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
     * 获取所在目录
     *
     * @return book_dir_id - 所在目录
     */
    public Long getBookDirId() {
        return bookDirId;
    }

    /**
     * 设置所在目录
     *
     * @param bookDirId 所在目录
     */
    public void setBookDirId(Long bookDirId) {
        this.bookDirId = bookDirId;
    }

    /**
     * 获取isbn
     *
     * @return isbn - isbn
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * 设置isbn
     *
     * @param isbn isbn
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
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
     * 获取推送到用户图书的状态 0=不处理 1=待处理 2=处理中 3=处理成功 4=处理失败
     *
     * @return push_status - 推送到用户图书的状态 0=不处理 1=待处理 2=处理中 3=处理成功 4=处理失败
     */
    public Short getPushStatus() {
        return pushStatus;
    }

    /**
     * 设置推送到用户图书的状态 0=不处理 1=待处理 2=处理中 3=处理成功 4=处理失败
     *
     * @param pushStatus 推送到用户图书的状态 0=不处理 1=待处理 2=处理中 3=处理成功 4=处理失败
     */
    public void setPushStatus(Short pushStatus) {
        this.pushStatus = pushStatus;
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