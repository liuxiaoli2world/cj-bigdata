package com.lgsc.cjbd.user.model;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

@ApiModel("会员商品")
@JsonIgnoreProperties(value = {"createdBy", "createdAt", "updatedBy", "updatedAt", "handler"})
public class Vip {
    /**
     * 会员商品编号
     */
    @Id
    @Column(name = "vip_id")
    @ApiParam("会员商品编号")
    private Long vipId;

    /**
     * 商品名
     */
    @ApiParam("商品名称 长度50")
    @NotBlank(message="商品名称不能为空")
    @Length(min=1,max=50)
    private String name;

    /**
     * 商品图
     */
    @Column(name = "image_url")
    @ApiParam("图片url")
    @NotBlank(message="商品图片不能为空")
    @Length(min=1,max=300)
    private String imageUrl;

    /**
     * 购买时长
     */
    @ApiParam("会员时长 存入数字即可(1一个月，12代表一年)")
    @NotBlank(message="购买时长不能为空")
    @Length(min=1,max=5)
    private String duration;

    /**
     * 单价
     */
    @ApiParam("单价 长度为10")
    @NotNull
    @Digits(integer=10,fraction=4)
    private Double price;

    /**
     * 创建人
     */
    @Column(name = "created_by")
    @Length(min = 1, max = 30)
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
    @Column(name = "updated_by")
    @Length(min = 1, max = 30)
    private String updatedBy;

    /**
     * 最后修改时间
     */
    @ApiModelProperty("最近登录时间")
    @Column(name = "updated_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;

    /**
     * 获取会员商品编号
     *
     * @return vip_id - 会员商品编号
     */
    public Long getVipId() {
        return vipId;
    }

    /**
     * 设置会员商品编号
     *
     * @param vipId 会员商品编号
     */
    public void setVipId(Long vipId) {
        this.vipId = vipId;
    }

    /**
     * 获取商品名
     *
     * @return name - 商品名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置商品名
     *
     * @param name 商品名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取商品图
     *
     * @return image_url - 商品图
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * 设置商品图
     *
     * @param imageUrl 商品图
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * 获取购买时长
     *
     * @return duration - 购买时长
     */
    public String getDuration() {
        return duration;
    }

    /**
     * 设置购买时长
     *
     * @param duration 购买时长
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }

    /**
     * 获取单价
     *
     * @return price - 单价
     */
    public Double getPrice() {
        return price;
    }

    /**
     * 设置单价
     *
     * @param price 单价
     */
    public void setPrice(Double price) {
        this.price = price;
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
}