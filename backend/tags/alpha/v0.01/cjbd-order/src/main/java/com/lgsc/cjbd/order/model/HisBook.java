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

@JsonIgnoreProperties(value = {"createdBy", "createdAt", "updatedBy", "updatedAt", "handler"})
@Table(name = "his_book")
public class HisBook {
    /**
     * 图书商品历史信息编号
     */
    @Id
    @Column(name = "his_book_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("图书商品历史信息编号")
    private Long hisBookId;

	/**
	 * 订单号
	 */
    @ApiModelProperty("订单号，最大长度50")
	@NotBlank(message = "订单号不能为空！")
	@Length(min = 0, max = 50)
	@Column(name = "order_no")
	private String orderNo;

    /**
     * 图书目录编号
     */
    @ApiModelProperty("图书目录编号，最大长度20")
    @NotNull(message = "图书目录编号不能为空！")
    @Digits(integer = 20, fraction = 0)
    @Column(name = "book_dir_id")
    private Long bookDirId;

    /**
     * 目录名称
     */
    @ApiModelProperty("目录名称，最大长度30")
	@Length(min = 0, max = 30)
    @Column(name = "dir_name")
    private String dirName;

    /**
     * 商品名称
     */
    @ApiModelProperty("商品名称，最大长度50")
	@Length(min = 0, max = 50)
    @Column(name = "book_name")
    private String bookName;

    /**
     * 商品图
     */
    @ApiModelProperty("商品图，最大长度300")
	@Length(min = 0, max = 300)
    @Column(name = "image_url")
    private String imageUrl;

    /**
     * isbn
     */
    @ApiModelProperty("isbn，最大长度13")
	@Length(min = 1, max = 13)
	@NotBlank(message = "isbn不能为空！")
    private String isbn;

    /**
     * 章节单价
     */
    @ApiModelProperty("章节单价")
    @NotNull(message = "章节单价不能为空！")
    @Digits(integer = 10, fraction = 4)
    @Column(name = "chapter_price")
    private Double chapterPrice;

    /**
     * 是否为全本图书 0=否 1=是
     */
    @ApiModelProperty("是否为全本图书 0=否 1=是")
    @NotNull(message = "是否为全本图书不能为空！")
    @Digits(integer = 1, fraction = 0)
    private Short isFull;
    
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
	@NotNull(message = "创建时间不能为空！")
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
     * 获取图书商品历史信息编号
     *
     * @return his_book_id - 图书商品历史信息编号
     */
    public Long getHisBookId() {
        return hisBookId;
    }

    /**
     * 设置图书商品历史信息编号
     *
     * @param hisBookId 图书商品历史信息编号
     */
    public void setHisBookId(Long hisBookId) {
        this.hisBookId = hisBookId;
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
     * 获取图书目录编号
     *
     * @return book_dir_id - 图书目录编号
     */
    public Long getBookDirId() {
        return bookDirId;
    }

    /**
     * 设置图书目录编号
     *
     * @param bookDirId 图书目录编号
     */
    public void setBookDirId(Long bookDirId) {
        this.bookDirId = bookDirId;
    }

    /**
     * 获取目录名称
     *
     * @return dir_name - 目录名称
     */
    public String getDirName() {
        return dirName;
    }

    /**
     * 设置目录名称
     *
     * @param dirName 目录名称
     */
    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    /**
     * 获取商品名称
     *
     * @return book_name - 商品名称
     */
    public String getBookName() {
        return bookName;
    }

    /**
     * 设置商品名称
     *
     * @param bookName 商品名称
     */
    public void setBookName(String bookName) {
        this.bookName = bookName;
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
     * 获取章节单价
     *
     * @return chapter_price - 章节单价
     */
    public Double getChapterPrice() {
        return chapterPrice;
    }

    /**
     * 设置章节单价
     *
     * @param chapterPrice 章节单价
     */
    public void setChapterPrice(Double chapterPrice) {
        this.chapterPrice = chapterPrice;
    }

    public Short getIsFull() {
		return isFull;
	}

	public void setIsFull(Short isFull) {
		this.isFull = isFull;
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