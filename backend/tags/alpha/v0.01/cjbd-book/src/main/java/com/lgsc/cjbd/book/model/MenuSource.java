package com.lgsc.cjbd.book.model;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(value = { "createdBy", "createdAt", "updatedBy", "updatedAt", "handler" })
@Table(name = "menu_source")
public class MenuSource {
	/**
	 * 菜单资源关系编号
	 */
	@Id
	@Column(name = "menu_source_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty("菜单资源关系编号")
	private Long menuSourceId;

	/**
	 * 菜单编号
	 */
	@ApiModelProperty("菜单编号，最大长度20")
	@NotNull(message = "菜单编号不能为空")
	@Column(name = "menu_id")
	private Long menuId;

	/**
	 * 资源类型 book=图书 content=内容 multifile=多媒体
	 */
	@ApiModelProperty("资源类型 book=图书 content=内容 multifile=多媒体")
	@Length(min = 1, max = 30)
	@NotBlank(message = "资源类型不能为空！")
	@Column(name = "source_type")
	private String sourceType;

	/**
	 * 资源编号
	 */
	@ApiModelProperty("资源编号，最大长度20")
	@NotNull( message = "资源编号不能为空！")
	@Column(name = "source_id")
	private Long sourceId;

	/**
	 * 创建人
	 */
	@ApiModelProperty("创建人")
	@Column(name = "created_by")
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
	 * 获取菜单资源关系编号
	 *
	 * @return menu_source_id - 菜单资源关系编号
	 */
	public Long getMenuSourceId() {
		return menuSourceId;
	}

	/**
	 * 设置菜单资源关系编号
	 *
	 * @param menuSourceId
	 *            菜单资源关系编号
	 */
	public void setMenuSourceId(Long menuSourceId) {
		this.menuSourceId = menuSourceId;
	}

	/**
	 * 获取菜单编号
	 *
	 * @return menu_id - 菜单编号
	 */
	public Long getMenuId() {
		return menuId;
	}

	/**
	 * 设置菜单编号
	 *
	 * @param menuId
	 *            菜单编号
	 */
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	/**
	 * 获取资源类型 book=图书 content=内容 multifile=多媒体
	 *
	 * @return source_type - 资源类型 book=图书 content=内容 multifile=多媒体
	 */
	public String getSourceType() {
		return sourceType;
	}

	/**
	 * 设置资源类型 book=图书 content=内容 multifile=多媒体
	 *
	 * @param sourceType
	 *            资源类型 book=图书 content=内容 multifile=多媒体
	 */
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	/**
	 * 获取资源编号
	 *
	 * @return source_id - 资源编号
	 */
	public Long getSourceId() {
		return sourceId;
	}

	/**
	 * 设置资源编号
	 *
	 * @param sourceId
	 *            资源编号
	 */
	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
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