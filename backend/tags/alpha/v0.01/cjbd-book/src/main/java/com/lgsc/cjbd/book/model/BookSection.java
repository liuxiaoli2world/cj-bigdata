package com.lgsc.cjbd.book.model;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(value = { "createdBy", "createdAt", "updatedBy", "updatedAt", "handler" })
@Table(name = "book_section")
@ApiModel("图书章节")
public class BookSection {
	/**
	 * 图书章节编号
	 */
	@Id
	@Column(name = "book_section_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty("图书章节编号")
	private Long bookSectionId;

	/**
	 * 所在目录
	 */
	@Column(name = "book_dir_id")
	@ApiModelProperty("所在目录，最大长度20")
	@NotNull(message = "所在目录不能为空！")
	@Digits(integer = 20, fraction = 0)
	private Long bookDirId;

	/**
	 * isbn
	 */
	@ApiModelProperty("isbn号，最大长度13")
	@NotBlank(message = "isbn不能为空！")
	@Length(min = 1, max = 13)
	private String isbn;

	/**
	 * 是否试读 0=否 1=是
	 */
	@Column(name = "is_try")
	@ApiModelProperty("是否试读 0=否 1=是")
	@Digits(integer = 6, fraction = 0)
	private Short isTry;

	/**
	 * 标题
	 */
	@ApiModelProperty("标题，最大长度30")
	@Length(min = 0, max = 30)
	private String title;

	/**
	 * 文章字数 虚拟列
	 */
	@Column(name = "content_length")
	@ApiModelProperty("文章字数 虚拟列，最大长度11")
	@Digits(integer = 11, fraction = 0)
	private Integer contentLength;

	/**
	 * 试读字数
	 */
	@Column(name = "try_length")
	@ApiModelProperty("试读字数，最大长度11")
	@Digits(integer = 11, fraction = 0)
	private Integer tryLength;

	/**
	 * 试读文章 虚拟列
	 */
	@Column(name = "try_content")
	@ApiModelProperty("试读文章 虚拟列，最大长度11")
	@Length(min = 0, max = 3000)
	private String tryContent;

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
	 * 文章
	 */
	@ApiModelProperty("文章")
	private String content;

	/**
	 * 章节资源地址
	 */
	@ApiModelProperty("章节资源地址")
	@Length(min = 0, max = 300)
	private String link;

	/**
	 * 目录代码 一级目录 001 二级目录 001002 三级目录 001002001
	 */
	@Column(name = "dir_code")
	@NotBlank(message = "目录代码不能为空")
	@ApiModelProperty("目录代码 一级目录 001 二级目录 001002 三级目录 001002001")
	private String dirCode;
	
	
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
	 * @param bookSectionId
	 *            图书章节编号
	 */
	public void setBookSectionId(Long bookSectionId) {
		this.bookSectionId = bookSectionId;
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
	 * @param bookDirId
	 *            所在目录
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
	 * @param isbn
	 *            isbn
	 */
	public void setIsbn(String isbn) {
		this.isbn = isbn;
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
	 * 获取标题
	 *
	 * @return title - 标题
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置标题
	 *
	 * @param title
	 *            标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 获取文章字数 虚拟列
	 *
	 * @return content_length - 文章字数 虚拟列
	 */
	public Integer getContentLength() {
		return contentLength;
	}

	/**
	 * 设置文章字数 虚拟列
	 *
	 * @param contentLength
	 *            文章字数 虚拟列
	 */
	public void setContentLength(Integer contentLength) {
		this.contentLength = contentLength;
	}

	/**
	 * 获取试读字数
	 *
	 * @return try_length - 试读字数
	 */
	public Integer getTryLength() {
		return tryLength;
	}

	/**
	 * 设置试读字数
	 *
	 * @param tryLength
	 *            试读字数
	 */
	public void setTryLength(Integer tryLength) {
		this.tryLength = tryLength;
	}

	/**
	 * 获取试读文章 虚拟列
	 *
	 * @return try_content - 试读文章 虚拟列
	 */
	public String getTryContent() {
		return tryContent;
	}

	/**
	 * 设置试读文章 虚拟列
	 *
	 * @param tryContent
	 *            试读文章 虚拟列
	 */
	public void setTryContent(String tryContent) {
		this.tryContent = tryContent;
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

	/**
	 * 获取文章
	 *
	 * @return content - 文章
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 设置文章
	 *
	 * @param content
	 *            文章
	 */
	public void setContent(String content) {
		this.content = content;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDirCode() {
		return dirCode;
	}

	public void setDirCode(String dirCode) {
		this.dirCode = dirCode;
	}
	

}