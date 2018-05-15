package com.lgsc.cjbd.book.model;

import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="内容")
@JsonIgnoreProperties(value = {"createdBy", "updatedBy", "handler"})
public class Content {
    /**
     * 内容编号
     */
    @Id
    @Column(name = "content_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("内容编号")
    private Long contentId;

    /**
     * 内容类型 notice=通知公告 headline=长江要闻 periodical=期刊 history=人文历史
     */
    @Column(name = "content_type")
    @Length(min=1,max=20)
    @ApiModelProperty("内容类型 notice=通知公告 headline=长江要闻 periodical=期刊 history=人文历史,最大长度为20")
    private String contentType;

    /**
     * 作者
     */
    @Length(min=1,max=30)
    @ApiModelProperty("作者 长度1-30")
    private String author;

    /**
     * 标题
     */
    @ApiModelProperty("标题,最大长度为30")
    @NotBlank(message="标题不能为空,最大长度为30")
    @Length(min=1,max=30)
    private String title;

    /**
     * 排序值
     */
    @ApiModelProperty("排序值")
    private Integer rank;

    /**
     * 简介
     */
    @Column(name = "content_desc")
    @ApiModelProperty("简介,最大长度为300")
    @Length(min=1,max=300)
    private String contentDesc;

    /**
     * 是否原创 0=否 1=是
     */
    @ApiModelProperty("是否原创 0=否 1=是")
    @Digits(integer = 6, fraction = 0)
    @Column(name = "is_original")
    private Short isOriginal;

    /**
     * 封面图片
     */
    @Column(name = "image_url")
    @Length(min=1,max=300)
    @ApiModelProperty("封面图片,最大长度为300")
    private String imageUrl;

    /**
     * 浏览量
     */
    @Digits(integer=11,fraction=0)
    @ApiModelProperty("浏览量")
    private Integer pv;

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
     * 正文
     */
    @ApiModelProperty("正文")
    @Length(min=1,max=65535)
    @NotBlank(message="正文不能为空,最大长度为65535")
    private String body;
    /**
     * 关键词
     */
    @ApiModelProperty("关键词,最大长度为50")
    @Length(min=1,max=50)
    private String keyword;
    /**
     * 出版日期
     */
    @ApiModelProperty("出版日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publicationDate;
    /**
     * 来源
     */
    @ApiModelProperty("来源,最大长度为30")
    @Length(min=1,max=30)
    private String source;
    /**
     * 资源的附件
     */
    @ApiModelProperty("资源的附件")
    @Transient
    private List<ContentAccessory> contentAccssoryList; 

    /**
     * 获取内容编号
     *
     * @return content_id - 内容编号
     */
    public Long getContentId() {
        return contentId;
    }

    /**
     * 设置内容编号
     *
     * @param contentId 内容编号
     */
    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    /**
     * 获取内容类型 notice=通知公告 headline=长江要闻 periodical=期刊 history=人文历史
     *
     * @return content_type - 内容类型 notice=通知公告 headline=长江要闻 periodical=期刊 history=人文历史
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * 设置内容类型 notice=通知公告 headline=长江要闻 periodical=期刊 history=人文历史
     *
     * @param contentType 内容类型 notice=通知公告 headline=长江要闻 periodical=期刊 history=人文历史
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * 获取作者
     *
     * @return author - 作者
     */
    public String getAuthor() {
        return author;
    }

    /**
     * 设置作者
     *
     * @param author 作者
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * 获取标题
     *
     * @return title - 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取排序值
     *
     * @return rank - 排序值
     */
    public Integer getRank() {
        return rank;
    }

    /**
     * 设置排序值
     *
     * @param rank 排序值
     */
    public void setRank(Integer rank) {
        this.rank = rank;
    }

    /**
     * 获取简介
     *
     * @return content_desc - 简介
     */
    public String getContentDesc() {
        return contentDesc;
    }

    /**
     * 设置简介
     *
     * @param contentDesc 简介
     */
    public void setContentDesc(String contentDesc) {
        this.contentDesc = contentDesc;
    }

    /**
     * 获取是否原创 0=否 1=是
     *
     * @return is_original - 是否原创 0=否 1=是
     */
    public Short getIsOriginal() {
        return isOriginal;
    }

    /**
     * 设置是否原创 0=否 1=是
     *
     * @param isOriginal 是否原创 0=否 1=是
     */
    public void setIsOriginal(Short isOriginal) {
        this.isOriginal = isOriginal;
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
     * @param imageUrl 图片
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * 获取浏览量
     *
     * @return pv - 浏览量
     */
    public Integer getPv() {
        return pv;
    }

    /**
     * 设置浏览量
     *
     * @param pv 浏览量
     */
    public void setPv(Integer pv) {
        this.pv = pv;
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
     * @param createdBy 创建人
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
     * @param createdAt 创建时间
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
     * @param updatedBy 最后修改人
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
     * @param updatedAt 最后修改时间
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * 获取正文
     *
     * @return body - 正文
     */
    public String getBody() {
        return body;
    }

    /**
     * 设置正文
     *
     * @param body 正文
     */
    public void setBody(String body) {
        this.body = body;
    }

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public List<ContentAccessory> getContentAccssoryList() {
		return contentAccssoryList;
	}

	public void setContentAccssoryList(List<ContentAccessory> contentAccssoryList) {
		this.contentAccssoryList = contentAccssoryList;
	}

	
	
    
}