package com.lgsc.cjbd.user.dto;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(value = {"handler"})
public class UserIndexDto {
	/**
	 * 时间
	 */
	@ApiModelProperty("时间")
	private String date;
	/**
	 * 新增的粉丝
	 */
	private Integer countUser;

	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getCountUser() {
		return countUser;
	}

	public void setCountUser(Integer countUser) {
		this.countUser = countUser;
	}
	
	
	
}
