package com.lgsc.cjbd.book.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.lgsc.cjbd.book.mapper.BookImageMapper;
import com.lgsc.cjbd.book.model.BookImage;

/**
 * 
 */
@Service
public class BookImageService {

	@Autowired
	private BookImageMapper bookImageMapper;

	public int insert(BookImage record) {
		return bookImageMapper.insert(record);
	}

	public int insertSelective(BookImage bookImage) {
		return bookImageMapper.insertSelective(bookImage);
	}

	public BookImage selectByPrimaryKey(long id) {
		return bookImageMapper.selectByPrimaryKey(id);
	}

	public List<BookImage> selectAll() {
		return bookImageMapper.selectAll();
	}

	public PageInfo<BookImage> selectAll(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<BookImage> list = bookImageMapper.selectAll();
		PageInfo<BookImage> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	public int updateByPrimaryKey(BookImage record) {
		return bookImageMapper.updateByPrimaryKey(record);
	}

	public int updateByPrimaryKeySelective(BookImage record) {
		return bookImageMapper.updateByPrimaryKeySelective(record);
	}

	public int deleteByPrimaryKey(long id) {
		return bookImageMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 根据isbn查询图书图片
	 * @param isbn
	 * @return
	 */
	public List<BookImage> selectByIsbn(String isbn) {
		BookImage bookImage = new BookImage();
		bookImage.setIsbn(isbn);
		return bookImageMapper.select(bookImage);
	}

	/**
	 * 根据isbn删除图片
	 * @param isbn
	 * @return
	 */
	public int deleteByIsbn(String isbn) {
		BookImage bookImage = new BookImage();
		bookImage.setIsbn(isbn);
		return bookImageMapper.delete(bookImage);
	}

}
