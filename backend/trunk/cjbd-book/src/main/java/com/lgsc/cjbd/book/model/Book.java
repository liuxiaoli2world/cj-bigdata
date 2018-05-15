package com.lgsc.cjbd.book.model;

import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(value = { "createdBy", "createdAt", "updatedBy", "updatedAt", "handler" })
@ApiModel("图书")
public class Book {
	/**
	 * 图书编号
	 */
	@Id
	@ApiModelProperty("图书编号")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "book_id")
	private Long bookId;

	/**
	 * 商品名称
	 */
	@ApiModelProperty("商品名称，最大长度100")
	@Length(min = 0, max = 100)
	@Column(name = "book_name")
	private String bookName;

	/**
	 * 出版社
	 */
	@ApiModelProperty("出版社，最大长度20")
	@Length(min = 0, max = 20)
	private String press;

	/**
	 * 作者
	 */
	@ApiModelProperty("作者，最大长度512")
	@Length(min = 0, max = 512)
	private String author;

	/**
	 * 译者
	 */
	@ApiModelProperty("译者，最大长度128")
	@Length(min = 0, max = 128)
	private String translator;

	/**
	 * isbn = realIsbn_bookId 
	 */
	@ApiModelProperty("isbn(realIsbn_bookId)号,最大长度50")
	@NotBlank(message = "isbn不能为空！")
	@Length(min = 1, max = 50)
	private String isbn;

	/**
	 * 出版时间
	 */
	@ApiModelProperty("出版时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "release_date")
	private Date releaseDate;

	/**
	 * 全书单价
	 */
	@ApiModelProperty("全书单价")
	@Digits(integer = 6, fraction = 4)
	@Column(name = "full_price")
	private Double fullPrice;

	/**
	 * 章节单价
	 */
	@ApiModelProperty("章节单价")
	@Digits(integer = 6, fraction = 4)
	@Column(name = "chapter_price")
	private Double chapterPrice;

	/**
	 * 是否试读 0=否 1=是
	 */
	@ApiModelProperty("是否试读 0=否 1=是")
	@Digits(integer = 6, fraction = 0)
	@Column(name = "is_try")
	private Short isTry;

	/**
	 * 是否发布 0=未发布 1=已发布
	 */
	@ApiModelProperty("是否发布 0=未发布 1=已发布")
	@Digits(integer = 6, fraction = 0)
	@Column(name = "is_release")
	private Short isRelease;

	/**
	 * 简介
	 */
	@ApiModelProperty("简介")
	@Length(min = 0, max = 3000)
	@Column(name = "book_desc")
	private String bookDesc;

	/**
	 * 详情
	 */
	@ApiModelProperty("详情，最大长度500")
	@Length(min = 0, max = 500)
	@Column(name = "book_detail")
	private String bookDetail;

	/**
	 * 阅读关键字
	 */
	@ApiModelProperty("阅读关键字，最大长度30")
	@Length(min = 0, max = 30)
	private String keyword;

	/**
	 * 是否推荐阅读 0=否 1=是
	 */
	@ApiModelProperty("是否推荐阅读 0=否 1=是")
	@Digits(integer = 6, fraction = 0)
	@Column(name = "is_recommend")
	private Short isRecommend;

	/**
	 * 图片
	 */
	@ApiModelProperty("图片")
	@Transient
	private List<BookImage> bookImages;

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
	 * 真实的isbn号
	 */
	@Column(name = "real_isbn")
	@ApiModelProperty("真实的isbn，最大长度13")
	@NotBlank(message = "isbn不能为空！")
	@Length(min = 1, max = 13)
	private String realIsbn;
	
	/**
	 * 单位
	 */
	private String unit;

	/**
	 * 获取图书编号
	 *
	 * @return book_id - 图书编号
	 */
	public Long getBookId() {
		return bookId;
	}

	/**
	 * 设置图书编号
	 *
	 * @param bookId
	 *            图书编号
	 */
	public void setBookId(Long bookId) {
		this.bookId = bookId;
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
	 * @param bookName
	 *            商品名称
	 */
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	/**
	 * 获取出版社
	 *
	 * @return press - 出版社
	 */
	public String getPress() {
		return press;
	}

	/**
	 * 设置出版社
	 *
	 * @param press
	 *            出版社
	 */
	public void setPress(String press) {
		this.press = press;
	}

	/**
	 * 获取作者
	 *
	 * @return author - 作者
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * 设置作者
	 *
	 * @param author
	 *            作者
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * 获取译者
	 *
	 * @return translator - 译者
	 */
	public String getTranslator() {
		return translator;
	}

	/**
	 * 设置译者
	 *
	 * @param translator
	 *            译者
	 */
	public void setTranslator(String translator) {
		this.translator = translator;
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
	 * @param isbn
	 *            isbn
	 */
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	/**
	 * 获取出版时间
	 *
	 * @return release_date - 出版时间
	 */
	public Date getReleaseDate() {
		return releaseDate;
	}

	/**
	 * 设置出版时间
	 *
	 * @param releaseDate
	 *            出版时间
	 */
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	/**
	 * 获取全书单价
	 *
	 * @return full_price - 全书单价
	 */
	public Double getFullPrice() {
		return fullPrice;
	}

	/**
	 * 设置全书单价
	 *
	 * @param fullPrice
	 *            全书单价
	 */
	public void setFullPrice(Double fullPrice) {
		this.fullPrice = fullPrice;
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
	 * @param chapterPrice
	 *            章节单价
	 */
	public void setChapterPrice(Double chapterPrice) {
		this.chapterPrice = chapterPrice;
	}

	/**
	 * 获取是否试读 0=否 1=是
	 *
	 * @return is_try - 是否试读 0=否 1=是
	 */
	public Short getIsTry() {
		return isTry;
	}

	/**
	 * 设置是否试读 0=否 1=是
	 *
	 * @param isTry
	 *            是否试读 0=否 1=是
	 */
	public void setIsTry(Short isTry) {
		this.isTry = isTry;
	}

	/**
	 * 获取是否发布 0=未发布 1=已发布
	 *
	 * @return is_release - 是否发布 0=未发布 1=已发布
	 */
	public Short getIsRelease() {
		return isRelease;
	}

	/**
	 * 设置是否发布 0=未发布 1=已发布
	 *
	 * @param isRelease
	 *            是否发布 0=未发布 1=已发布
	 */
	public void setIsRelease(Short isRelease) {
		this.isRelease = isRelease;
	}

	/**
	 * 获取简介
	 *
	 * @return book_desc - 简介
	 */
	public String getBookDesc() {
		return bookDesc;
	}

	/**
	 * 设置简介
	 *
	 * @param bookDesc
	 *            简介
	 */
	public void setBookDesc(String bookDesc) {
		this.bookDesc = bookDesc;
	}

	/**
	 * 获取详情
	 *
	 * @return book_detail - 详情
	 */
	public String getBookDetail() {
		return bookDetail;
	}

	/**
	 * 设置详情
	 *
	 * @param bookDetail
	 *            详情
	 */
	public void setBookDetail(String bookDetail) {
		this.bookDetail = bookDetail;
	}

	/**
	 * 获取是否推荐阅读 0=否 1=是
	 * 
	 * @return
	 */
	public Short getIsRecommend() {
		return isRecommend;
	}

	/**
	 * 设置是否推荐
	 * 
	 * @param isRecommend
	 */
	public void setIsRecommend(Short isRecommend) {
		this.isRecommend = isRecommend;
	}

	/**
	 * 获取阅读关键字
	 *
	 * @return keyword - 阅读关键字
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * 设置阅读关键字
	 *
	 * @param keyword
	 *            阅读关键字
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**
	 * 获取图片
	 * 
	 * @return
	 */
	public List<BookImage> getBookImages() {
		return bookImages;
	}

	/**
	 * 设置图片
	 * 
	 * @param bookImages
	 */
	public void setBookImages(List<BookImage> bookImages) {
		this.bookImages = bookImages;
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

	public String getRealIsbn() {
		return realIsbn;
	}

	public void setRealIsbn(String realIsbn) {
		this.realIsbn = realIsbn;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	
}