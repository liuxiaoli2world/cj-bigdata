package com.lgsc.cjbd.book.dto;

import com.lgsc.cjbd.book.model.Multifile;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("多媒体DTO")
public class MultifileDTO extends Multifile {

	@ApiModelProperty("封面路径")
	private String path;

	@ApiModelProperty("菜单id")
	private Long menuId;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

}
