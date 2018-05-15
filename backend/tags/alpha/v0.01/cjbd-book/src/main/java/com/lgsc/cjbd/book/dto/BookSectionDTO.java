package com.lgsc.cjbd.book.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lgsc.cjbd.book.model.BookSection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("图书章节DTO")
@JsonIgnoreProperties(value = {"handler"})
public class BookSectionDTO extends BookSection{
	
	/**
	 * 当前页
	 */
	@ApiModelProperty("当前页数")
	private int pageNum;
	
	/**
	 * 页面数量
	 */
	@ApiModelProperty("页面数量")
	private int pageSize = 1;
	
	/**
	 * 总页数
	 */
	@ApiModelProperty("总页数")
	private int total;

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	

}
