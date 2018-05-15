package com.lgsc.cjbd.user.model;

import java.util.Date;
import javax.persistence.*;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(value = {"createdBy", "createdAt", "updatedBy", "updatedAt", "handler"})
@Table(name = "user_book")
public class UserBook {
    /**
     * 用户图书目录关系编号
     */
    @Id
    @Column(name = "user_book_dir_id")
    private Long userBookDirId;

    /**
     * 用户编号
     */
    @Column(name = "user_id")
    private Long userId;

    /**
	 * isbn = realIsbn|bookId 
	 */
	@ApiModelProperty("isbn(realIsbn|bookId)号,最大长度50")
	@NotBlank(message = "isbn不能为空！")
	@Length(min = 1, max = 50)
	private String isbn;

    /**
     * 图书目录编号
     */
    @Column(name = "book_dir_id")
    private Long bookDirId;

    /**
     * 创建人
     */
    @Column(name = "created_by")
    private String createdBy;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private Date createdAt;

    /**
     * 最后修改人
     */
    @Column(name = "updated_by")
    private String updatedBy;

    /**
     * 最后修改时间
     */
    @Column(name = "updated_at")
    private Date updatedAt;

    /**
     * 获取用户图书目录关系编号
     *
     * @return user_book_dir_id - 用户图书目录关系编号
     */
    public Long getUserBookDirId() {
        return userBookDirId;
    }

    /**
     * 设置用户图书目录关系编号
     *
     * @param userBookDirId 用户图书目录关系编号
     */
    public void setUserBookDirId(Long userBookDirId) {
        this.userBookDirId = userBookDirId;
    }

    /**
     * 获取用户编号
     *
     * @return user_id - 用户编号
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户编号
     *
     * @param userId 用户编号
     */
    public void setUserId(Long userId) {
        this.userId = userId;
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
     * @param isbn isbn
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * 获取图书目录编号
     *
     * @return book_dir_id - 图书目录编号
     */
    public Long getBookDirId() {
        return bookDirId;
    }

    /**
     * 设置图书目录编号
     *
     * @param bookDirId 图书目录编号
     */
    public void setBookDirId(Long bookDirId) {
        this.bookDirId = bookDirId;
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