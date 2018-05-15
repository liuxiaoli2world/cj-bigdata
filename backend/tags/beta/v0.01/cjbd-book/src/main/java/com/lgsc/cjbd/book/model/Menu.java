package com.lgsc.cjbd.book.model;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(value = { "createdBy", "createdAt", "updatedBy", "updatedAt", "handler" })
public class Menu {
	/**
	 * 菜单编号
	 */
	@Id
	@Column(name = "menu_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty("菜单编号")
	private Long menuId;

	/**
	 * 父菜单编号，根节点为0
	 */
	@ApiModelProperty("父菜单编号，根节点为0，最大长度20")
	@Digits(integer = 20, fraction = 0)
	@Column(name = "parent_menu_id")
	private Long parentMenuId;

	/**
	 * 菜单名称
	 */
	@ApiModelProperty("菜单名称，最大长度20")
	@Length(min = 0, max = 20)
	@Column(name = "menu_name")
	private String menuName;

	/**
	 * 是否显示 0 不显示 1 显示
	 */
	@ApiModelProperty("是否显示 0 不显示 1 显示")
	@Digits(integer = 6, fraction = 0)
	@Column(name = "is_display")
	private Short isDisplay;

	/**
	 * 是否作为菜单栏 0 不显示 1 显示
	 */
	@ApiModelProperty("是否作为菜单栏 0 不显示 1 显示")
	@Digits(integer = 6, fraction = 0)
	@Column(name = "is_menu")
	private Short isMenu;

	/**
	 * 图片
	 */
	@ApiModelProperty("图片url，最大长度300")
	@Length(min = 0, max = 300)
	@Column(name = "image_url")
	private String imageUrl;

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
	 * 获取父菜单编号，根节点为0
	 *
	 * @return parent_menu_id - 父菜单编号，根节点为0
	 */
	public Long getParentMenuId() {
		return parentMenuId;
	}

	/**
	 * 设置父菜单编号，根节点为0
	 *
	 * @param parentMenuId
	 *            父菜单编号，根节点为0
	 */
	public void setParentMenuId(Long parentMenuId) {
		this.parentMenuId = parentMenuId;
	}

	/**
	 * 获取菜单名称
	 *
	 * @return menu_name - 菜单名称
	 */
	public String getMenuName() {
		return menuName;
	}

	/**
	 * 设置菜单名称
	 *
	 * @param menuName
	 *            菜单名称
	 */
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	/**
	 * 获取是否显示 0 不显示 1 显示
	 *
	 * @return is_display - 是否显示 0 不显示 1 显示
	 */
	public Short getIsDisplay() {
		return isDisplay;
	}

	/**
	 * 设置是否显示 0 不显示 1 显示
	 *
	 * @param isDisplay
	 *            是否显示 0 不显示 1 显示
	 */
	public void setIsDisplay(Short isDisplay) {
		this.isDisplay = isDisplay;
	}

	/**
	 * 获取是否作为菜单栏 0 不显示 1 显示
	 *
	 * @return is_menu - 是否作为菜单栏 0 不显示 1 显示
	 */
	public Short getIsMenu() {
		return isMenu;
	}

	/**
	 * 设置是否作为菜单栏 0 不显示 1 显示
	 *
	 * @param isMenu
	 *            是否作为菜单栏 0 不显示 1 显示
	 */
	public void setIsMenu(Short isMenu) {
		this.isMenu = isMenu;
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