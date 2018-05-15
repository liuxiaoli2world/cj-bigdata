package com.lgsc.cjbd.book.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lgsc.cjbd.book.mapper.BookDirMapper;
import com.lgsc.cjbd.book.mapper.BookSectionMapper;
import com.lgsc.cjbd.book.model.BookDir;
import com.lgsc.cjbd.book.model.BookSection;
import com.lgsc.cjbd.book.util.CommonUtil;
import com.lgsc.cjbd.remote.client.UserClient;


/**
 * 
 */
@Service
public class BookDirService {

	@Autowired
	private BookDirMapper bookDirMapper;
	
	@Autowired
	private BookSectionMapper bookSectionMapper;

	@Autowired
	private UserClient userClient;
	
	@Value("${try.length}")
	private String tryLength;
	
	@Value("${is.try}")
	private String isTry;

	/**
	 * 添加图书目录
	 * 
	 * @param bookDir
	 * @return
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	@Transactional
	public int insertSelective(BookDir bookDir) throws NumberFormatException, Exception {

		Long parentBookDirId = bookDir.getParentBookDirId(); // 得到插入目录的parentBookDirId
		if (parentBookDirId == null || parentBookDirId < 0) {// 错误的parentBookDirId
			return 0;
		} else if (parentBookDirId == 0) {// 根目录
			// 根据isbn获取数据库中最后一个根目录
			BookDir lastParentBooKDir = bookDirMapper.selectLastOneByIsbn(bookDir.getIsbn());
			String dirCode = "";
			if (lastParentBooKDir == null) { // 初始化第一个父目录
				dirCode = "000";
			} else {
				dirCode = lastParentBooKDir.getDirCode();
			}
			dirCode = CommonUtil.generateDirCode(Integer.parseInt(dirCode) + 1);
			bookDir.setDirCode(dirCode);
			bookDir.setHasLeaf((short) 0); // 初始化默认无叶子节点
			bookDir.setIsTry(Short.parseShort(isTry.trim())); //设置默认试读
			bookDir.setCreatedAt(new Date()); //创建时间
		} else {// 子目录
			// 更新父目录的叶子节点
			BookDir bookParentDir = bookDirMapper.selectByPrimaryKey(parentBookDirId);
			if (bookParentDir != null && bookParentDir.getHasLeaf() == 0) {
				bookParentDir.setHasLeaf((short) 1);
				bookDirMapper.updateByPrimaryKeySelective(bookParentDir);// null值不会更新
			} 
			
			// 根据parentBookDirId获取数据库中最后一个子目录
			BookDir lastChildBooKDir = bookDirMapper.selectLastOneByParentBookDirId(parentBookDirId);
			String parentDirCode = bookParentDir.getDirCode();
			String childDirCode = "";
			String dirCode = "";
			if (lastChildBooKDir == null) { // 初始化第一个子目录
				childDirCode = "000";
			} else {
				dirCode = lastChildBooKDir.getDirCode();
				childDirCode = dirCode.substring(dirCode.length() - 3);
			}
			dirCode = parentDirCode + CommonUtil.generateDirCode(Integer.parseInt(childDirCode) + 1);
			bookDir.setDirCode(dirCode);
			bookDir.setHasLeaf((short) 0); // 初始化默认无叶子节点
			bookDir.setIsTry(Short.parseShort(isTry.trim())); //设置默认试读
			bookDir.setCreatedAt(new Date()); //创建时间
		}
		// 插入目录
		bookDirMapper.insertSelective(bookDir);
		
		bookDir = bookDirMapper.selectOne(bookDir); //取回来
		return insertBookSection(bookDir);
	}
	
	/**
	 * 插入章节
	 * @param bookSection
	 * @param bookDir
	 * @return
	 */
	private int insertBookSection(BookDir bookDir){
		BookSection bookSection = new BookSection();
		bookSection.setBookDirId(bookDir.getBookDirId());
		bookSection.setTitle(bookDir.getDirName());
		bookSection.setIsbn(bookDir.getIsbn());
		bookSection.setIsTry(Short.parseShort(isTry.trim()));
		bookSection.setDirCode(bookDir.getDirCode());
		bookSection.setContent("");
		bookSection.setTryLength(Integer.parseInt(tryLength.trim()));
		bookSection.setCreatedAt(new Date());	//创建时间
		return 	bookSectionMapper.insertSelective(bookSection);
	}
	
	public BookDir selectByPrimaryKey(long id) {
		BookDir bookDir = bookDirMapper.selectByPrimaryKey(id);
		if (bookDir != null) {
			BookDir bookDirParent = bookDirMapper.selectByPrimaryKey(bookDir.getParentBookDirId());
			if (bookDirParent != null) {
				bookDir.setBak1(bookDirParent.getDirName());
			} else {
				bookDir.setBak1(bookDir.getDirName());
			}
		}
		return bookDir;
	}

	/**
	 * 查询所有图书目录
	 */
	public List<BookDir> selectAll() {
		return bookDirMapper.selectAll();
	}

	public PageInfo<BookDir> selectAll(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<BookDir> list = bookDirMapper.selectAll();
		PageInfo<BookDir> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	
	/**
	 * 修改目录名
	 * @param record
	 * @return
	 */
	@Transactional
	public int updateByPrimaryKey(BookDir record) {
		BookSection bookSection = new BookSection();
		bookSection.setBookDirId(record.getBookDirId());
		bookSection = bookSectionMapper.selectOne(bookSection);
		bookSection.setTitle(record.getDirName());
		bookSectionMapper.updateByPrimaryKey(bookSection);
		return bookDirMapper.updateByPrimaryKey(record);
	}

	/**
	 * 修改图书目录 -- 目录名称
	 * @param bookDir
	 * @return
	 */
	public int updateByPrimaryKeySelective(BookDir bookDir) {
		BookSection bookSection = new BookSection();
		bookSection.setBookDirId(bookDir.getBookDirId());
		bookSection = bookSectionMapper.selectOne(bookSection);
		bookSection.setTitle(bookDir.getDirName());
		bookSectionMapper.updateByPrimaryKeySelective(bookSection);
		return bookDirMapper.updateByPrimaryKeySelective(bookDir);
	}

	/**
	 * 删除图书目录
	 * 
	 * @param bookDirId
	 * @return
	 */
	@Transactional
	public int deleteByPrimaryKey(long bookDirId) {
		userClient.deleteUserBookByBookDirId(bookDirId); // 删除用户图书关系表
		return bookDirMapper.deleteByPrimaryKey(bookDirId); // 删除图书目录
	}

	/**
	 * 删除图书目录
	 * 
	 * @param isbn
	 * @return
	 */
	@Transactional
	public int deleteByIsbn(String isbn) {
		BookDir bookDir = new BookDir();
		bookDir.setIsbn(isbn);
		userClient.deleteUserBookByIsbn(isbn); // 删除用户图书关系表
		return bookDirMapper.delete(bookDir); // 删除图书目录
	}

	/**
	 * 查询所有根目录(含用户购买的目录)
	 * @param isbn
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<BookDir> selectRoot(String isbn, Long userId) {
		List<Object> rootDirs = (List<Object>) userClient.selectRootDirsOfUser(isbn, userId).getData();
		BookDir bookDir = new BookDir();
		bookDir.setParentBookDirId(0L);
		bookDir.setIsbn(isbn);
		List<BookDir> bookDirs = bookDirMapper.select(bookDir);
		if (rootDirs != null && !rootDirs.isEmpty()) {
			List<Long> rootDirs1 = rootDirs.stream().map(e -> Long.parseLong(e.toString()))
					.collect(Collectors.toList());
			for (BookDir bookDir2 : bookDirs) {
				if (contains(rootDirs1, bookDir2.getBookDirId())) {
					bookDir2.setBak1("1"); //购买的目录1,非购买的0
				}
			}
		}
		return bookDirs;
	}

	/**
	 * 查询bookDirId是否包含在rootDirs中
	 * @param rootDirs
	 * @param bookDirId
	 * @return
	 */
	private boolean contains(List<Long> rootDirs,Long bookDirId) {
		for (Long long1 : rootDirs) {
			if (long1.longValue()==bookDirId.longValue()) {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * 根据isbn查询根目录
	 * @param isbn
	 * @return
	 */
	public List<BookDir> selectRootByIsbn(String isbn){
		BookDir bookDir = new BookDir();
		bookDir.setParentBookDirId(0L);
		bookDir.setIsbn(isbn);
		return bookDirMapper.select(bookDir);
	}
	
	/**
	 * 根据图书目录id查询祖先节点，不包括当前目录
	 */
	public List<BookDir> selectParentList(Long bookDirId) {
		return bookDirMapper.selectParentList(bookDirId);
	}

	/**
	 * 根据图书目录id查询后代节点，不包括当前目录
	 */
	public List<BookDir> selectChildList(Long bookDirId) {
		return bookDirMapper.selectChildList(bookDirId);
	}

	/**
	 * 根据图书目录id查询子目录
	 */
	public List<BookDir> selectChild(Long bookDirId) {
		return bookDirMapper.selectChild(bookDirId);
	}

	/**
	 * 根据图书isbn查询所有目录
	 * 
	 * @param isbn
	 * @return
	 */
	public List<Map<String, Object>> selectAllByIsbn(String isbn) {
		List<Map<String, Object>> list = bookDirMapper.selectAllByIsbn(isbn);
		if(list == null)
			list = new ArrayList<Map<String, Object>>();
		return list;
	}

	/**
	 * 查询单个目录For Client
	 * 
	 * @param bookDirId
	 * @return
	 */
	public String selectBookDirDetail(Long bookDirId) {
		Map<String, Object> map = bookDirMapper.selectBookOrderInfo(bookDirId);
		String string = JSONObject.toJSON(map).toString();
		return string;
	}

}
