package com.lgsc.cjbd.book.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import static java.util.stream.Collectors.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lgsc.cjbd.book.mapper.BookChannelMapper;
import com.lgsc.cjbd.book.mapper.BookImageMapper;
import com.lgsc.cjbd.book.mapper.BookMapper;
import com.lgsc.cjbd.book.mapper.MenuSourceMapper;
import com.lgsc.cjbd.book.model.Book;
import com.lgsc.cjbd.book.model.BookChannel;
import com.lgsc.cjbd.book.model.BookImage;
import com.lgsc.cjbd.book.model.MenuSource;

/**
 * 图书服务
 */
@Service
public class BookService {

	@Autowired
	private BookMapper bookMapper;

	@Autowired
	private BookImageMapper bookImageMapper;

	@Autowired
	private BookChannelMapper bookChannelMapper;

	@Autowired
	private MenuSourceMapper menuSourceMapper;

	public int insert(Book record) {
		return bookMapper.insert(record);
	}

	public int insertSelective(Book book) {
		return bookMapper.insertSelective(book);
	}

	public Book selectByPrimaryKey(long id) {
		return bookMapper.selectByPrimaryKey(id);
	}

	public List<Book> selectAll() {
		return bookMapper.selectAll();
	}

	public PageInfo<Book> selectAll(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Book> list = bookMapper.selectAll();
		PageInfo<Book> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	public int updateByPrimaryKey(Book record) {
		return bookMapper.updateByPrimaryKey(record);
	}

	public int updateByPrimaryKeySelective(Book book) {
		return bookMapper.updateByPrimaryKeySelective(book);
	}

	/**
	 * 根据isbn查询图书
	 * 
	 * @param isbn
	 * @return
	 */
	public Book selectByIsbn(String isbn) {
		Book record = new Book();
		record.setIsbn(isbn);
		return bookMapper.selectOne(record);
	}

	/**
	 * 根据id查询图书
	 * 
	 * @param isbn
	 * @return
	 */
	public Book selectByBookId(Long bookId) {
		return bookMapper.selectByPrimaryKey(bookId);
	}

	/**
	 * 条件查询图书
	 * 
	 * @param pageNum
	 *            页码
	 * @param pageSize
	 *            每页数量
	 * @param realname
	 *            作者姓名
	 * @param channelId
	 *            图书分类id
	 * @param parentMenuId
	 *            父菜单id
	 * @param childMenuId
	 *            子菜单id
	 * @param keyword
	 *            关键词
	 * @param isRelease
	 *            是否发布 0=未发布 1=已发布
	 * @return
	 */
	public PageInfo<Map<String, Object>> selectBy(Integer pageNum, Integer pageSize, String realname, Long channelId,
			Long parentMenuId, Long childMenuId, String keyword, String isRelease) {
		PageHelper.startPage(pageNum, pageSize);
		List<Map<String, Object>> list = bookMapper.selectBy(realname, channelId, parentMenuId, childMenuId, keyword,
				isRelease);
		if (list.isEmpty()) {
			return new PageInfo<>();
		}
		return new PageInfo<>(list);

	}

	/**
	 * 校验ISBN
	 * 
	 * @param isbn
	 * @return
	 */
	public boolean checkIsbn(String isbn) {
		Book book = new Book();
		book.setIsbn(isbn);
		return Pattern.matches("^(\\d[- ]*){9}[\\dxX]$", isbn) && (bookMapper.selectOne(book) != null);
	}

	/**
	 * 根据图书的isbn删除
	 * 
	 * @param isbn
	 * @return
	 */
	@Transactional
	public int deleteByIsbn(String isbn) {
		Book book = new Book();
		book.setIsbn(isbn);
		Book one = bookMapper.selectOne(book);
		if (one == null) {
			return 0;
		}
		MenuSource menuSource = new MenuSource();
		menuSource.setSourceId(one.getBookId());
		menuSource.setSourceType("book");
		menuSourceMapper.delete(menuSource); // 删除菜单资源
		return bookMapper.delete(book); // 删除图书

	}

	public List<Book> selectIsRec() {
		Book book = new Book();
		book.setIsRecommend(Short.parseShort("1"));
		List<Book> books = bookMapper.select(book); // 查询所有推荐的图书
		if (books != null && !books.isEmpty()) {
			books = books.stream().filter(b -> b.getIsRelease() == 1).collect(toList());
		}
		return books;
	}

	/**
	 * 查询一本图书
	 * 
	 * @param bookId
	 * @return
	 */
	public Map<String, Object> selectById(Long bookId) {
		Map<String, Object> map = bookMapper.selectById(bookId);
		return map;
	}

	/**
	 * 插入图书
	 * 
	 * @param book
	 *            图书
	 * @param channelId
	 *            分类
	 * @param menuId
	 *            菜单
	 * @param imageUrl
	 *            封面url
	 * @return
	 */
	@Transactional
	public int insertBook(Book book, Long channelId, Long menuId, String imageUrl) {
		if (book.getChapterPrice() == null) { // 单章价格默认0
			book.setChapterPrice(0d);
		}
		if (book.getFullPrice() == null) { // 全本价格默认0
			book.setFullPrice(0d);
		}
		if (book.getIsRelease() == null) { // 默认未发布
			book.setIsRelease((short) 0);
		}
		Date date = new Date();
		// 插入图书
		book.setCreatedAt(date);
		book.setUpdatedAt(date);
		int num = bookMapper.insertSelective(book);
		if (num <= 0) {
			return 0;
		}

		// 插入图书封面
		BookImage bimage = new BookImage();
		bimage.setIsbn(book.getIsbn());
		bimage.setImageUrl(imageUrl);
		bimage.setImageScene("1"); // 图片使用场景 1 封面
		bimage.setCreatedAt(date);
		bimage.setUpdatedAt(date);
		bookImageMapper.insertSelective(bimage);

		// 插入菜单资源
		MenuSource ms = new MenuSource();
		ms.setSourceType("book");
		ms.setSourceId(book.getBookId());
		ms.setMenuId(menuId);
		ms.setCreatedAt(date);
		ms.setUpdatedAt(date);
		menuSourceMapper.insertSelective(ms);

		// 插入分类
		BookChannel bc = new BookChannel();
		bc.setIsbn(book.getIsbn());
		bc.setChannelId(channelId);
		bc.setCreatedAt(date);
		bc.setUpdatedAt(date);
		bookChannelMapper.insert(bc);

		// 插入图书
		return num;
	}

	/**
	 * 修改图书
	 * 
	 * @param book
	 *            图书
	 * @param channelId
	 *            分类id
	 * @param menuId
	 *            菜单
	 * @param imageUrl
	 *            封面
	 * @return
	 */
	@Transactional
	public int updateBook(Book book, Long channelId, Long menuId, String imageUrl) {
		if (book.getChapterPrice() == null) { // 单章价格默认0
			book.setChapterPrice(0d);
		}
		if (book.getFullPrice() == null) { // 全本价格默认0
			book.setFullPrice(0d);
		}
		if (book.getIsRelease() == null) { // 默认未发布
			book.setIsRelease((short) 0);
		}
		Date date = new Date();
		// 修改图书
		book.setUpdatedAt(date);
		int num = bookMapper.updateByPrimaryKeySelective(book);
		if (num <= 0) {
			return 0;
		}

		// 修改图书封面
		BookImage bimage = new BookImage();
		bimage.setIsbn(book.getIsbn());
		bimage.setImageScene("1"); // 图片使用场景 1 封面
		BookImage bookImage = bookImageMapper.selectOne(bimage);
		if (bookImage != null) {
			bookImage.setImageUrl(imageUrl);
			bookImage.setUpdatedAt(date);
			bookImageMapper.updateByPrimaryKeySelective(bookImage);
		}

		// 修改菜单资源
		MenuSource ms = new MenuSource();
		ms.setSourceId(book.getBookId());
		ms.setSourceType("book");
		MenuSource menuSource = menuSourceMapper.selectOne(ms);
		if (menuSource != null) {
			menuSource.setMenuId(menuId);
			menuSource.setUpdatedAt(date);
			menuSourceMapper.updateByPrimaryKeySelective(menuSource);
		}

		// 修改分类
		BookChannel bc = new BookChannel();
		bc.setIsbn(book.getIsbn());
		BookChannel bookChannel = bookChannelMapper.selectOne(bc);
		if (bookChannel != null) {
			bookChannel.setChannelId(channelId);
			bookChannel.setUpdatedAt(date);
			bookChannelMapper.updateByPrimaryKeySelective(bookChannel);
		}
		return num;
	}

	/**
	 * 查询实时书籍数量
	 * 
	 * @return
	 */
	public Integer selectBookSum() {
		return bookMapper.selectCount(null);
	}

	/**
	 * 修改图书发布,下架状态
	 */
	public int updateRelease(Long bookId, Short isRelease) {
		Book book = bookMapper.selectByPrimaryKey(bookId);
		book.setIsRelease(isRelease);
		if (isRelease == 1) { // 发布
			book.setReleaseDate(new Date()); // 发布时间
			book.setUpdatedAt(new Date());
		} else { // 下架
			book.setUpdatedAt(new Date());
			book.setIsRecommend((short) 0);
		}
		return bookMapper.updateByPrimaryKeySelective(book);
	}
	
	/**
	 * 根据isbn查询图书是否存在
	 * @param isbn
	 * @return
	 */
	public boolean bookIsExist(String isbn) {
		Book record = new Book();
		record.setIsbn(isbn);
		return bookMapper.selectOne(record) == null ? false : true;
	}

}
