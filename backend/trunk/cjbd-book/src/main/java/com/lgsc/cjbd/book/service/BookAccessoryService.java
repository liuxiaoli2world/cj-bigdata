package com.lgsc.cjbd.book.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.lgsc.cjbd.book.mapper.BookAccessoryMapper;
import com.lgsc.cjbd.book.model.BookAccessory;

/**
 * 
 */
@Service
public class BookAccessoryService {
	
	@Autowired
	private BookAccessoryMapper bookAccessoryMapper;

	public int insert(BookAccessory record) {
		return bookAccessoryMapper.insert(record);
	}

	public int insertSelective(BookAccessory record) {
		return bookAccessoryMapper.insertSelective(record);
	}

	public BookAccessory selectByPrimaryKey(long id) {
		return bookAccessoryMapper.selectByPrimaryKey(id);
	}
	
	/**
	 * 查询附件
	 * @param isbn
	 * @return
	 */
	public List<BookAccessory> selectByIsbn(String isbn) {
		BookAccessory bookAccessory = new BookAccessory();
		bookAccessory.setIsbn(isbn);
		return bookAccessoryMapper.select(bookAccessory);
	}
    
    public List<BookAccessory> selectAll() {
		return bookAccessoryMapper.selectAll();
	}
	
	public PageInfo<BookAccessory> selectAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<BookAccessory> list = bookAccessoryMapper.selectAll();
		PageInfo<BookAccessory> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	
	public int updateByPrimaryKey(BookAccessory record) {
		return bookAccessoryMapper.updateByPrimaryKey(record);
	}

	public int updateByPrimaryKeySelective(BookAccessory record) {
		return bookAccessoryMapper.updateByPrimaryKeySelective(record);
	}

	public int deleteByPrimaryKey(long id) {
		return bookAccessoryMapper.deleteByPrimaryKey(id);
	}
	
	/**
	 * 根据isbn删除附件
	 * @param isbn
	 * @return
	 */
	public int deleteByIsbn(String isbn) {
		BookAccessory ba = new BookAccessory();
		ba.setIsbn(isbn);
		return bookAccessoryMapper.delete(ba);
	}

}
