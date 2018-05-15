package com.lgsc.cjbd.book.dto;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;



/**
 * 人文历史DTO
 * 
 * @author 罗天宇
 *
 */
@ApiModel("人文历史DTO")
@JsonIgnoreProperties(value = {"handler"})
public class HistoryDto {

	private Long contentId;
	
	private String title;
	
	private String contentDesc;
	
    private String imageUrl;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContentDesc() {
		return contentDesc;
	}

	public void setContentDesc(String contentDesc) {
		this.contentDesc = contentDesc;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Long getContentId() {
		return contentId;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}
    
    
}
