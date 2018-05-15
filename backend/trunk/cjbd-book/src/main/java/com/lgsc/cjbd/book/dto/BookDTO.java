package com.lgsc.cjbd.book.dto;

import com.lgsc.cjbd.book.model.Book;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("图书DTO")
public class BookDTO extends Book {

	@ApiModelProperty("分类id")
	private Long channelId;

	@ApiModelProperty("菜单id")
	private Long menuId;

	@ApiModelProperty("封面路径")
	private String imageUrl;

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}
