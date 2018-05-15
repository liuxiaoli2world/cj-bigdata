package com.lgsc.cjbd.book.model;

import java.util.Date;
import javax.persistence.*;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(value = { "createdBy", "createdAt", "updatedBy", "updatedAt", "handler" })
@Table(name = "book_accessory")
public class BookAccessory {
	/**
	 * 图书附件编号
	 */
	@Id
	@Column(name = "book_accessory_id")
	@ApiModelProperty("图书附件编号")
	private Long bookAccessoryId;

	/**
	 * isbn = realIsbn|bookId 
	 */
	@ApiModelProperty("isbn(realIsbn|bookId)号,最大长度50")
	@NotBlank(message = "isbn不能为空！")
	@Length(min = 1, max = 50)
	private String isbn;

	/**
	 * 附件地址
	 */
	@ApiModelProperty("附件地址，最大长度300")
	@Length(min = 0, max = 300)
	@Column(name = "accessory_url")
	private String accessoryUrl;

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
	 * 附件名称
	 */
	@Column(name = "accessory_name")
	private String accessoryName;

	/**
	 * 附件类型 .pdf .txt
	 */
	@Column(name = "accessory_type")
	private String accessoryType;

	/**
	 * 附件大小
	 */
	@Column(name = "accessory_size")
	private String accessorySize;

	/**
	 * 获取图书附件编号
	 *
	 * @return book_accessory_id - 图书附件编号
	 */
	public Long getBookAccessoryId() {
		return bookAccessoryId;
	}

	/**
	 * 设置图书附件编号
	 *
	 * @param bookAccessoryId
	 *            图书附件编号
	 */
	public void setBookAccessoryId(Long bookAccessoryId) {
		this.bookAccessoryId = bookAccessoryId;
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
	 * 获取附件地址
	 *
	 * @return accessory_url - 附件地址
	 */
	public String getAccessoryUrl() {
		return accessoryUrl;
	}

	/**
	 * 设置附件地址
	 *
	 * @param accessoryUrl
	 *            附件地址
	 */
	public void setAccessoryUrl(String accessoryUrl) {
		this.accessoryUrl = accessoryUrl;
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

	public String getAccessoryName() {
		return accessoryName;
	}

	public void setAccessoryName(String accessoryName) {
		this.accessoryName = accessoryName;
	}

	public String getAccessoryType() {
		return accessoryType;
	}

	public void setAccessoryType(String accessoryType) {
		this.accessoryType = accessoryType;
	}

	public String getAccessorySize() {
		return accessorySize;
	}

	public void setAccessorySize(String accessorySize) {
		this.accessorySize = accessorySize;
	}

}