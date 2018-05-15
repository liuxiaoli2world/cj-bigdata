package com.lgsc.cjbd.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.lgsc.cjbd.user.mapper.BookmarkMapper;
import com.lgsc.cjbd.user.model.Bookmark;

/**
 * 书签
 */
@Service
public class BookmarkService {

	@Autowired
	private BookmarkMapper bookmarkMapper;

	public int insert(Bookmark record) {
		return bookmarkMapper.insert(record);
	}

	public int insertSelective(Bookmark bookmark) {
		return bookmarkMapper.insertSelective(bookmark);
	}

	public Bookmark selectByPrimaryKey(long id) {
		return bookmarkMapper.selectByPrimaryKey(id);
	}

	public List<Bookmark> selectAll() {
		return bookmarkMapper.selectAll();
	}

	public PageInfo<Bookmark> selectAll(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Bookmark> list = bookmarkMapper.selectAll();
		PageInfo<Bookmark> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	public int updateByPrimaryKey(Bookmark record) {
		return bookmarkMapper.updateByPrimaryKey(record);
	}

	public int updateByPrimaryKeySelective(Bookmark record) {
		return bookmarkMapper.updateByPrimaryKeySelective(record);
	}

	public int deleteByPrimaryKey(long id) {
		return bookmarkMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 根据章节id删除书签
	 * 
	 * @param bookSectionId
	 *            章节id
	 * @return
	 */
	public int deleteByBookSectionId(Long bookSectionId) {
		Bookmark bookmark = new Bookmark();
		bookmark.setBookSectionId(bookSectionId);
		return bookmarkMapper.delete(bookmark);
	}

	/**
	 * 根据isbn删除书签
	 * 
	 * @param isbn
	 * @return
	 */
	public int deleteByIsbn(String isbn) {
		Bookmark bookmark = new Bookmark();
		bookmark.setIsbn(isbn);
		return bookmarkMapper.delete(bookmark);
	}

	/**
	 * 根据isbn和用户id查询书签
	 * @param isbn 图书isbn
	 * @param userId 用户id
	 * @return
	 */
	public List<Bookmark> selectBookMark(String isbn, Long userId) {
		Bookmark bookmark = new Bookmark();
		bookmark.setIsbn(isbn);
		bookmark.setUserId(userId);
		return bookmarkMapper.select(bookmark);
	}

}
