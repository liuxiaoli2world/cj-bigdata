package com.lgsc.cjbd.user.model;

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

@JsonIgnoreProperties(value = {"createdBy", "createdAt", "updatedBy", "updatedAt", "handler"})
public class Bookmark {
    /**
     * 书签编号
     */
    @Id
	@ApiModelProperty("书签编号")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private Long bookmarkId;

    /**
     * 用户编号
     */
	@ApiModelProperty("用户编号")
	@NotNull(message = "用户编号不能为空！")
	@Digits(integer = 20, fraction = 0)
    @Column(name = "user_id")
    private Long userId;

	/**
	 * isbn = realIsbn|bookId 
	 */
	@ApiModelProperty("isbn(realIsbn|bookId)号,最大长度50")
	@NotBlank(message = "isbn不能为空！")
	@Length(min = 1, max = 50)
	private String isbn;
	
    /**
     * 图书章节编号
     */
	@ApiModelProperty("图书章节编号")
	@NotNull(message = "图书章节编号不能为空！")
	@Digits(integer = 20, fraction = 0)
    @Column(name = "book_section_id")
    private Long bookSectionId;

    /**
     * 商品名
     */
	@ApiModelProperty("商品名")
	@Length(min = 0, max = 50)
    private String name;

    /**
     * 当前页数
     */
	@ApiModelProperty("当前页数")
	@Digits(integer = 11, fraction = 0)
    private Integer page;

    /**
     * 坐标
     */
	@ApiModelProperty("坐标")
	@Length(min = 0, max = 10)
    private String xyz;

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
     * 获取书签编号
     *
     * @return bookmark_id - 书签编号
     */
    public Long getBookmarkId() {
        return bookmarkId;
    }

    /**
     * 设置书签编号
     *
     * @param bookmarkId 书签编号
     */
    public void setBookmarkId(Long bookmarkId) {
        this.bookmarkId = bookmarkId;
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
     * 获取商品名
     *
     * @return name - 商品名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置商品名
     *
     * @param name 商品名
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