package com.lgsc.cjbd.book.model;

import java.util.Date;
import javax.persistence.*;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(value = {"createdBy", "createdAt", "updatedBy", "updatedAt", "handler"})
@Table(name = "event_bookmark")
public class EventBookmark {
    /**
     * 事件编号
     */
    @Id
    @Column(name = "event_bookmark_id")
    private Long eventBookmarkId;

    /**
     * 用户编号
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * isbn
     */
    private String isbn;

    /**
     * 图书章节编号
     */
    @Column(name = "book_section_id")
    private Long bookSectionId;

    /**
     * 文件名
     */
    private String name;

    /**
     * 当前页数
     */
    private Integer page;

    /**
     * 坐标
     */
    private String xyz;

    /**
     * 操作类型 insert=新增 delete=删除
     */
    @Column(name = "handle_type")
    private String handleType;

    /**
     * 推送到用户书签的状态 0=不处理 1=待处理 2=处理中 3=处理成功 4=处理失败
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
     * @return event_bookmark_id - 事件编号
     */
    public Long getEventBookmarkId() {
        return eventBookmarkId;
    }

    /**
     * 设置事件编号
     *
     * @param eventBookmarkId 事件编号
     */
    public void setEventBookmarkId(Long eventBookmarkId) {
        this.eventBookmarkId = eventBookmarkId;
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
     * 获取图书章节编号
     *
     * @return book_section_id - 图书章节编号
     */
    public Long getBookSectionId() {
        return bookSectionId;
    }

    /**
     * 设置图书章节编号
     *
     * @param bookSectionId 图书章节编号
     */
    public void setBookSectionId(Long bookSectionId) {
        this.bookSectionId = bookSectionId;
    }

    /**
     * 获取文件名
     *
     * @return name - 文件名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置文件名
     *
     * @param name 文件名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取当前页数
     *
     * @return page - 当前页数
     */
    public Integer getPage() {
        return page;
    }

    /**
     * 设置当前页数
     *
     * @param page 当前页数
     */
    public void setPage(Integer page) {
        this.page = page;
    }

    /**
     * 获取坐标
     *
     * @return xyz - 坐标
     */
    public String getXyz() {
        return xyz;
    }

    /**
     * 设置坐标
     *
     * @param xyz 坐标
     */
    public void setXyz(String xyz) {
        this.xyz = xyz;
    }

    /**
     * 获取操作类型 insert=新增 delete=删除
     *
     * @return handle_type - 操作类型 insert=新增 delete=删除
     */
    public String getHandleType() {
        return handleType;
    }

    /**
     * 设置操作类型 insert=新增 delete=删除
     *
     * @param handleType 操作类型 insert=新增 delete=删除
     */
    public void setHandleType(String handleType) {
        this.handleType = handleType;
    }

    /**
     * 获取推送到用户书签的状态 0=不处理 1=待处理 2=处理中 3=处理成功 4=处理失败
     *
     * @return push_status - 推送到用户书签的状态 0=不处理 1=待处理 2=处理中 3=处理成功 4=处理失败
     */
    public Short getPushStatus() {
        return pushStatus;
    }

    /**
     * 设置推送到用户书签的状态 0=不处理 1=待处理 2=处理中 3=处理成功 4=处理失败
     *
     * @param pushStatus 推送到用户书签的状态 0=不处理 1=待处理 2=处理中 3=处理成功 4=处理失败
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