package com.lgsc.cjbd.book.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lgsc.cjbd.book.dto.BookSectionDTO;
import com.lgsc.cjbd.book.mapper.BookDirMapper;
import com.lgsc.cjbd.book.mapper.BookMapper;
import com.lgsc.cjbd.book.mapper.BookSectionMapper;
import com.lgsc.cjbd.book.model.Book;
import com.lgsc.cjbd.book.model.BookDir;
import com.lgsc.cjbd.book.model.BookSection;
import com.lgsc.cjbd.remote.client.UserClient;
import com.lgsc.cjbd.book.util.Base64Util;
import com.lgsc.cjbd.book.util.SubHTML;

/**
 * 图书章节服务
 */
@Service
public class BookSectionService {

	@Autowired
	private BookSectionMapper bookSectionMapper;

	@Autowired
	private BookDirMapper bookDirMapper;

	@Autowired
	private UserClient userClient;

	@Autowired
	private BookMapper bookMapper;

	@Value("${try.length}")
	private String tryLength;

	@Value("${is.try}")
	private String isTry;

	public int insertSelective(BookSection record) {
		return bookSectionMapper.insertSelective(record);
	}

	public List<BookSection> selectAll() {
		return bookSectionMapper.selectAll();
	}

	public int deleteByPrimaryKey(long id) {
		return bookSectionMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 根据图书目录id删除章节
	 * 
	 * @param bookDirId
	 * @return
	 */
	@Transactional
	public int deleteByBookDirId(Long bookDirId) {
		BookSection bookSection = new BookSection();
		bookSection.setBookDirId(bookDirId);
		List<BookSection> sectionIds = bookSectionMapper.select(bookSection);
		for (BookSection bs : sectionIds) {
			userClient.deleteBookMarkByBookSectionId(bs.getBookSectionId()); // 删除书签
		}
		return bookSectionMapper.delete(bookSection); // 删除章节
	}

	/**
	 * 根据图书isbn删除章节
	 * 
	 * @param isbn
	 * @return
	 */
	@Transactional
	public int deleteByIsbn(String isbn) {
		BookSection bookSection = new BookSection();
		bookSection.setIsbn(isbn);
		userClient.deleteBookMarkByIsbn(isbn); // 删除书签
		return bookSectionMapper.delete(bookSection); // 删除章节
	}

	/**
	 * 更新bookSection的试读内容
	 * 
	 * @param bookSectionId
	 * @param tryLength
	 * @return
	 */
	public String updateTry(Long bookSectionId, Integer tryLength) {
		String i = "";
		BookSection bookSection = bookSectionMapper.selectByPrimaryKey(bookSectionId);
		if (bookSection != null) {
			bookSection.setTryLength(tryLength);
			i = SubHTML.getSubHTML(bookSection.getContent(), tryLength);
			bookSection.setTryContent(i);
			bookSectionMapper.updateByPrimaryKeySelective(bookSection);
		}
		return i;
	}

	/**
	 * 根据isbn与bookDirId查询章节
	 * 
	 * @param isbn
	 * @param bookDirId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public BookSectionDTO selectByBookDirId(String isbn, Long bookDirId, Short hasLeaf, Long userId) {
		boolean isVip = false;
		boolean isInspector = false;
		Double price = getPrice(isbn);
		BookSectionDTO bookSectionDTO = null;
		if (price == null || (price != null && price == 0.0)) { // 免费书
			bookSectionDTO = selectBookSectionDTO(isbn, bookDirId, hasLeaf, true);
		} else { // 非免费书
			if (userId == null) { // 非用户
				bookSectionDTO = selectBookSectionDTO(isbn, bookDirId, hasLeaf, false);
			} else { // 用户
				String vipStatus = userClient.selectVipStatus(userId); 
				if (vipStatus.equals("会员")) {
					isVip = true;
				}
				
				List<String> roleStatus = userClient.selectRoleStatus(userId); // "ROLE_INSPECTOR" 质检员
				if (roleStatus != null && !roleStatus.isEmpty()) {
					for (String roleStatu : roleStatus) {
						if ("ROLE_INSPECTOR".equals(roleStatu)) {
							isInspector = true ;
							break;
						}
					}
				}
				
				if (isVip || isInspector) { // 会员用户,质检员,所有的书都可以看
					bookSectionDTO = selectBookSectionDTO(isbn, bookDirId, hasLeaf, true);
				} else { //普通用户或过期会员,非质检员
					List<Object> rootDirs = (List<Object>) userClient.selectRootDirsOfUser(isbn, userId).getData(); // 用户购买的根目录
					List<Long> leafs = new ArrayList<Long>(); // 存储所后代节点
					if (rootDirs != null && !rootDirs.isEmpty()) { // 用户购买了的一部分或全部根目录
						for (Object object : rootDirs)
							leafs.addAll(bookDirMapper.selectdescendantList(Long.parseLong(object.toString())));
						
							if (contains(leafs,bookDirId)) { // 用户购买的章节
								bookSectionDTO = selectBookSectionDTO(isbn, bookDirId, hasLeaf, true);
							} else { // 非购买章节
								bookSectionDTO = selectBookSectionDTO(isbn, bookDirId, hasLeaf, false);
							}
					} else { // 用户没有购买本书
						bookSectionDTO = selectBookSectionDTO(isbn, bookDirId, hasLeaf, false);
					}
				}
			}
		}
		return bookSectionDTO;

	}

	/**
	 * 分页查询用户购买章节
	 * 
	 * @param pageNum
	 *            当前页
	 * @param pageSize
	 *            每页数量 1
	 * @param isbn
	 *            图书isbn号
	 * @param userId
	 *            用户id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public PageInfo<BookSection> selectByIsbn(Integer pageNum, Integer pageSize, String isbn, Long userId) {
		boolean isVip = false;
		boolean isInspector = false;
		Double price = getPrice(isbn);
		PageInfo<BookSection> pageInfo = null;
		PageHelper.startPage(pageNum, pageSize);
		List<BookSection> list = bookSectionMapper.selectByIsbn(isbn);
		if (price == null || price == 0.0) { // 免费书
			if (list != null && !list.isEmpty()) {
				for (BookSection bookSection : list) {
					bookSection.setContent(Base64Util.encodeData(bookSection.getContent()));
					bookSection.setTryContent(null);
				}
				pageInfo = new PageInfo<>(list);
			}
		} else { // 非免费书

			if (userId == null) { // 非用户
				if (list != null && !list.isEmpty()) {
					for (BookSection bookSection : list) {
						isTry(bookSection);
					}
					pageInfo = new PageInfo<>(list);
				}
			} else { // 用户
				String vipStatus = userClient.selectVipStatus(userId); 
				if (vipStatus.equals("会员")) {
					isVip = true;
				}
				
				List<String> roleStatus = userClient.selectRoleStatus(userId);
				if (roleStatus != null && !roleStatus.isEmpty()) {
					for (String roleStatu : roleStatus) {
						if ("ROLE_INSPECTOR".equals(roleStatu)) { // "ROLE_INSPECTOR" 质检员
							isInspector = true ;
							break;
						}
					}
				}
				if (isVip || isInspector) { // 会员用户,所有的书都可以看
					if (list != null && !list.isEmpty()) {
						for (BookSection bookSection : list) {
							bookSection.setContent(Base64Util.encodeData(bookSection.getContent()));
							bookSection.setTryContent(null);
						}
						pageInfo = new PageInfo<>(list);
					}
				} else { // 普通用户或过期会员
					List<Object> rootDirs = (List<Object>) userClient.selectRootDirsOfUser(isbn, userId).getData(); // 用户购买的根目录
					List<Long> leafs = new ArrayList<Long>(); // 存储所有叶子节点
					if (rootDirs != null && !rootDirs.isEmpty()) { // 用户购买了一部分或全部根目录
						for (Object object : rootDirs)
							leafs.addAll(bookDirMapper.selectdescendantList(Long.parseLong(object.toString())));

						if (list != null && !list.isEmpty()) {
							for (BookSection bookSection : list) {
								if (contains(leafs, bookSection.getBookDirId())) { // 购买的章节
									bookSection.setContent(Base64Util.encodeData(bookSection.getContent()));
									bookSection.setTryContent(null);
								} else { // 非购买的章节
									isTry(bookSection);
								}
							}
							pageInfo = new PageInfo<>(list);
						}
					} else { // 用户没有购买目录
						if (list != null && !list.isEmpty()) {
							for (BookSection bookSection : list) {
								isTry(bookSection);
							}
							pageInfo = new PageInfo<>(list);
						}
					}
				}
			}
		}
		return pageInfo;
	}

	/**
	 * 获取bookSectionDTO
	 * @param isbn
	 * @param bookDirId
	 * @param hasLeaf
	 * @return
	 */
	private BookSectionDTO selectBookSectionDTO(String isbn, Long bookDirId, Short hasLeaf, boolean contentOrTry) {
		BookSectionDTO bookSectionDTO = null;
		int total = bookSectionMapper.selectCountByIsbn(isbn); //查询所有章节
		if (hasLeaf == 0) // 叶子节点目录
			bookSectionDTO = bookSectionMapper.selectByBookDirId(isbn, bookDirId);
		else // 非叶子节点目录
			bookSectionDTO = bookSectionMapper.selectByBookDirId(isbn,
					bookDirMapper.selectFirstLeafByParentBookDirId(bookDirId));
		bookSectionDTO.setTotal(total);
		return contentOrTry?readContent(bookSectionDTO):readTry(bookSectionDTO);
	}

	/**
	 * 读取试读章节内容
	 * 
	 * @param bookSectionDTO
	 */
	private BookSectionDTO readTry(BookSectionDTO bookSectionDTO) {
		if (bookSectionDTO != null) {
			if (bookSectionDTO.getIsTry() == 1) { // 可以试读章节
				bookSectionDTO.setContent(null);
				bookSectionDTO.setTryContent(Base64Util.encodeData(bookSectionDTO.getTryContent()));
			} else { // 不可以试读章节
				bookSectionDTO.setContent(null);
				bookSectionDTO.setTryContent(null);
			}
		}
		return bookSectionDTO;
	}

	/**
	 * 读取章节内容
	 * @param bookSectionDTO
	 */
	private BookSectionDTO readContent(BookSectionDTO bookSectionDTO) {
		if (bookSectionDTO != null) {
			bookSectionDTO.setContent(Base64Util.encodeData(bookSectionDTO.getContent()));
			bookSectionDTO.setTryContent(null);
		}
		return bookSectionDTO;
	}

	/**
	 * 是否可试读
	 * @param bookSection
	 */
	private void isTry(BookSection bookSection) {
		if (bookSection.getIsTry() == 1) { // 试读章节
			bookSection.setContent(null);
			bookSection.setTryContent(Base64Util.encodeData(bookSection.getTryContent()));
		} else {// 不可以试读章节
			bookSection.setContent(null);
			bookSection.setTryContent(null);
		}
	}

	private Double getPrice(String isbn) {
		Book book = new Book();
		book.setIsbn(isbn);
		book = bookMapper.selectOne(book);
		Double price = null;
		if (book != null) {
			price = book.getFullPrice();
		}
		return price;
	}

	/**
	 * 查询bookDirId是否属于rootDirs
	 * 
	 * @param rootDirs
	 * @param bookDirId
	 * @return
	 */
	private boolean contains(List<Long> rootDirs, Long bookDirId) {
		for (Long long1 : rootDirs) {
			if (long1.longValue() == bookDirId.longValue()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 根据bookDirId查询章节
	 * 
	 * @param bookDirId
	 * @return
	 */
	public BookSection modifyByBookDirId(Long bookDirId) {
		BookDir bookDir = bookDirMapper.selectByPrimaryKey(bookDirId);
		BookSection bookSection = null;
		if (bookDir != null) {
			bookSection = new BookSection();
			bookSection.setBookDirId(bookDirId);
			bookSection = bookSectionMapper.selectOne(bookSection);
			if (bookDir.getParentBookDirId() == 0) {// 根目录设置试读内容
				bookSection.setTryContent(Base64Util.encodeData(bookSection.getTryContent()));
				bookSection.setContent(Base64Util.encodeData(bookSection.getContent()));
				
			} else {// 最后的子目录修改章节 即hasLeaf = 0
				bookSection.setTryContent(null);
				bookSection.setContent(Base64Util.encodeData(bookSection.getContent()));
			}
		}
		return bookSection;
	}

	/**
	 * 更新章节及其父章节
	 * 
	 * @param record
	 * @return
	 */
	@Transactional
	public int updateSection(BookSection record) {
		int i = 0;
		BookDir bookDir = bookDirMapper.selectByPrimaryKey(record.getBookDirId());
		Long parentId = null;
		if (bookDir != null)
			parentId = bookDir.getParentBookDirId();
		
		if (parentId == null) {
			return 0; 
		} else if (parentId == 0) { // 根目录
			i = updateBookSectionContent(record, bookDir); // 纯根目录,可更新内容,可修改试读 + 根目录(一级目录),不可更新内容,可修改试读
			//更新子目录试读状态
			List<BookDir> bookDirs = bookDirMapper.selectChildList(record.getBookDirId()); //查询后代目录，不包括当前目录
			BookSection bookSection = null;
			if (bookDirs != null) {
				for (BookDir bookDir2 : bookDirs) {
					//更新目录
					bookDir2.setIsTry(record.getIsTry());
					bookDirMapper.updateByPrimaryKeySelective(bookDir2);
					//更新章节
					bookSection = bookSectionMapper.selectIsTry(bookDir2.getBookDirId()); //查询是否试读
					bookSection.setIsTry(record.getIsTry());
					i = bookSectionMapper.updateByPrimaryKeySelective(bookSection);
				}
			}
			
		} else if (parentId != 0 && bookDir.getHasLeaf() == 0) { // 叶子目录,可更新内容,不可修改试读  如:三级目录
			i = updateBookSectionContent(record, bookDir); 
			saveSection(record, bookDir);
		} else { // 子目录(不含叶子目录) 二级目录  不能修改内容
			return 0;
		}
		return i;
	}

	private void saveSection(BookSection record, BookDir bookDir) {
		// 递归更新父章节
		BookSection parentBookSection = null;
		Long parentId = bookDir.getParentBookDirId();
		if (parentId != 0) {
			List<BookSection> list = bookSectionMapper.selectSiblingList(record.getBookDirId()); // 获取同级章节列表
			StringBuilder sb = new StringBuilder(""); 
			for (BookSection bookSection : list) {
				sb.append(bookSection.getContent());
			}
			parentBookSection = new BookSection();
			parentBookSection.setBookDirId(parentId);
			parentBookSection = bookSectionMapper.selectOne(parentBookSection);
			parentBookSection.setContent(sb.toString());
			bookDir = bookDirMapper.selectByPrimaryKey(parentBookSection.getBookDirId());
			updateBookSectionContent(parentBookSection, bookDir);	//更新内容
			saveSection(parentBookSection, bookDir);
		}
	}

	/**
	 * 更新bookSection的内容
	 * 
	 * @param record
	 * @return
	 */
	private int updateBookSectionContent(BookSection record, BookDir bookDir) {
		int i = 0;
		String content = record.getContent().replace("&nbsp;", " ").replaceAll("\n", ""); //替换空格
		if (StringUtils.isNotBlank(content)) {
			record.setContentLength(SubHTML.getCount(content)); // 更新内容字数
			record.setContent(content);
			
			//更新试读内容
			BookSection bookSection = bookSectionMapper.selectByPrimaryKey(record.getBookSectionId()); //查询图书章节
			Short isTry = record.getIsTry() != null ? record.getIsTry() : bookSection.getIsTry();
			record.setIsTry(isTry); //设置是否试读
			record.setTryContent(SubHTML.getSubHTML(content, bookSection.getTryLength()));
			
			record.setUpdatedAt(new Date());	//更新时间
			bookSectionMapper.updateByPrimaryKeySelective(record); // 根据主键更新属性不为null的值
			
			// 更新目录试读
			bookDir.setIsTry(isTry);
			i = bookDirMapper.updateByPrimaryKeySelective(bookDir);
		}
		return i;
	}
}
