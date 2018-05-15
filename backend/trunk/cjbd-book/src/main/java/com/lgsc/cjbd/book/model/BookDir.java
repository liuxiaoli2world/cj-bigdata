package com.lgsc.cjbd.book.model;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Table(name = "book_dir")
@JsonIgnoreProperties(value = { "createdBy", "createdAt", "updatedBy", "updatedAt", "handler" })
@ApiModel(value = "目录")
public class BookDir {
	/**
	 * 所在目录
	 */
	@Id
	@Column(name = "book_dir_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty("目录编号")
	private Long bookDirId;

	/**
	 * 父目录，根节点为0
	 */
	@Column(name = "parent_book_dir_id")
	@ApiModelProperty("父目录，根节点为0")
	@Digits(integer = 20, fraction = 0)
	private Long parentBookDirId;

	/**
	 * isbn = realIsbn|bookId 
	 */
	@ApiModelProperty("isbn(realIsbn|bookId)号,最大长度50")
	@NotBlank(message = "isbn不能为空！")
	@Length(min = 1, max = 50)
	private String isbn;

	/**
	 * 目录名称
	 */
	@Column(name = "dir_name")
	@ApiModelProperty("目录名称，最大长度100")
	@Length(min = 0, max = 100)
	private String dirName;

	/**
	 * 是否拥有叶子目录 0 否 1 是
	 */
	@Column(name = "has_leaf")
	@ApiModelProperty("是否拥有叶子目录 0 否 1 是")
	@Digits(integer = 6, fraction = 0)
	private Short hasLeaf;

	/**
	 * 是否试读 0=否 1=是
	 */
	@Column(name = "is_try")
	@ApiModelProperty("是否试读 0=否  1=是")
	@Digits(integer = 6, fraction = 0)
	private Short isTry;

	/**
	 * 简介
	 */
	@Column(name = "dir_desc")
	@ApiModelProperty("简介，最大长度300")
	@Length(min = 0, max = 300)
	private String dirDesc;

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
	 * 程序解析目录关联词
	 */
	@ApiModelProperty("程序解析目录关联词")
	private String bak1;

	/**
	 * 目录代码 一级目录 001 二级目录 001002 三级目录 001002001
	 */
	@Column(name = "dir_code")
	@ApiModelProperty("目录代码 一级目录 001 二级目录 001002 三级目录 001002001")
	private String dirCode;

	public String getBak1() {
		return bak1;
	}

	public void setBak1(String bak1) {
		this.bak1 = bak1;
	}

	/**
	 * 获取所在目录
	 *
	 * @return book_dir_id3 - 所在目录
	 */
	public Long getBookDirId() {
		return bookDirId;
	}

	/**
	 * 设置所在目录
	 *
	 * @param bookDirId3
	 *            所在目录
	 */
	public void setBookDirId(Long bookDirId) {
		this.bookDirId = bookDirId;
	}

	/**
	 * 获取父目录，根节点为0
	 *
	 * @return parent_book_dir_id - 父目录，根节点为0
	 */
	public Long getParentBookDirId() {
		return parentBookDirId;
	}

	/**
	 * 设置父目录，根节点为0
	 *
	 * @param parentBookDirId
	 *            父目录，根节点为0
	 */
	public void setParentBookDirId(Long parentBookDirId) {
		this.parentBookDirId = parentBookDirId;
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
	 * @param dirName
	 *            目录名称
	 */
	public void setDirName(String dirName) {
		this.dirName = dirName;
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
	 * 获取简介
	 *
	 * @return dir_desc - 简介
	 */
	public String getDirDesc() {
		return dirDesc;
	}

	/**
	 * 设置简介
	 *
	 * @param dirDesc
	 *            简介
	 */
	public void setDirDesc(String dirDesc) {
		this.dirDesc = dirDesc;
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

	/**
	 * 获取是否拥有叶子目录
	 * 
	 * @return
	 */
	public Short getHasLeaf() {
		return hasLeaf;
	}

	/**
	 * 设置是否拥有叶子目录
	 * 
	 * @param hasLeaf
	 */
	public void setHasLeaf(Short hasLeaf) {
		this.hasLeaf = hasLeaf;
	}

	public String getDirCode() {
		return dirCode;
	}

	public void setDirCode(String dirCode) {
		this.dirCode = dirCode;
	}
	
}