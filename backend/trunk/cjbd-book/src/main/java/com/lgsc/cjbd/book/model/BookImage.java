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
@Table(name = "book_image")
public class BookImage {
	/**
	 * 图书图片编号
	 */
	@Id
	@Column(name = "book_image_id")
	@ApiModelProperty("图书图片编号")
	private Long bookImageId;

	/**
	 * isbn = realIsbn|bookId 
	 */
	@ApiModelProperty("isbn(realIsbn|bookId)号,最大长度50")
	@NotBlank(message = "isbn不能为空！")
	@Length(min = 1, max = 50)
	private String isbn;

	/**
	 * 图片
	 */
	@ApiModelProperty("图片url，最大长度300")
	@Length(min = 0, max = 300)
	@Column(name = "image_url")
	private String imageUrl;

	/**
	 * 图片使用场景 1 封面
	 */
	@ApiModelProperty("图片url，最大长度5")
	@Length(min = 0, max = 5)
	@Column(name = "image_scene")
	private String imageScene;

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
	 * 获取图书图片编号
	 *
	 * @return book_image_id - 图书图片编号
	 */
	public Long getBookImageId() {
		return bookImageId;
	}

	/**
	 * 设置图书图片编号
	 *
	 * @param bookImageId
	 *            图书图片编号
	 */
	public void setBookImageId(Long bookImageId) {
		this.bookImageId = bookImageId;
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
	 * 获取图片
	 *
	 * @return image_url - 图片
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * 设置图片
	 *
	 * @param imageUrl
	 *            图片
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	/**
	 * 获取图片使用场景 1 封面
	 *
	 * @return image_scene - 图片使用场景 1 封面
	 */
	public String getImageScene() {
		return imageScene;
	}

	/**
	 * 设置图片使用场景 1 封面
	 *
	 * @param imageScene
	 *            图片使用场景 1 封面
	 */
	public void setImageScene(String imageScene) {
		this.imageScene = imageScene;
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
}