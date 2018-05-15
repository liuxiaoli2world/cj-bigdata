package com.lgsc.cjbd.user.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.lgsc.cjbd.user.mapper.UserBookMapper;
import com.lgsc.cjbd.user.model.UserBook;

/**
 * 用户图书关系
 */
@Service
public class UserBookService {
	
	private static Logger log = LogManager.getLogger(UserBookService.class);
	
	@Autowired
	private UserBookMapper userBookMapper;

	public int insert(UserBook record) {
		return userBookMapper.insert(record);
	}

	public int insertSelective(UserBook record) {
		return userBookMapper.insertSelective(record);
	}
	
	/**
	 * 批量添加用户图书关系
	 */
	@Transactional
	public Boolean batchInsertSelective(List<Map<String, Object>> list) {
		log.info("批量添加用户图书关系开始，参数：[list=" + JSON.toJSONString(list) + "]");
		for (Map<String, Object> map : list) {
			UserBook record = new UserBook();
			record.setUserId(Long.parseLong(map.get("userId").toString()));
			record.setIsbn(map.get("isbn").toString());
			record.setBookDirId(Long.parseLong(map.get("bookDirId").toString()));
			UserBook oldRecord = userBookMapper.selectOne(record);
			if (oldRecord != null) {// 如果用户已购买
				continue;
			}
			record.setCreatedAt(new Date());
			userBookMapper.insertSelective(record);
		}
		log.info("批量添加用户图书关系结束");
		return true;
	}

	public UserBook selectByPrimaryKey(long id) {
		return userBookMapper.selectByPrimaryKey(id);
	}
    
    public List<UserBook> selectAll() {
		return userBookMapper.selectAll();
	}
	
	public PageInfo<UserBook> selectAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<UserBook> list = userBookMapper.selectAll();
		PageInfo<UserBook> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	
	public int updateByPrimaryKey(UserBook record) {
		return userBookMapper.updateByPrimaryKey(record);
	}

	public int updateByPrimaryKeySelective(UserBook record) {
		return userBookMapper.updateByPrimaryKeySelective(record);
	}

	public int deleteByPrimaryKey(long id) {
		return userBookMapper.deleteByPrimaryKey(id);
	}
	
	/**
	 * 根据图书目录id删除关系
	 */
	public int deleteByBookDirId(Long bookDirId) {
		UserBook userBook = new UserBook();
		userBook.setBookDirId(bookDirId);
		return userBookMapper.delete(userBook);
	}

	/**
	 * 根据图书isbn删除关系
	 */
	public int deleteByIsbn(String isbn) {
		UserBook userBook = new UserBook();
		userBook.setIsbn(isbn);
		return userBookMapper.delete(userBook);
	}

	/**
	 * 根据用户id查询关系
	 * @param userId
	 * @return
	 */
	public List<UserBook> selectByUserId(Long userId) {
		UserBook userBook = new UserBook();
		userBook.setUserId(userId);
		List<UserBook> list = userBookMapper.select(userBook);
		return list;
	}
	
	/**
	 * 查询用户已购买的根目录
	 * @param isbn
	 * @param userId
	 * @return
	 */
	public List<Long> selectRootDirsOfUser(String isbn, Long userId){
		return userBookMapper.selectRootDirsOfUser(isbn, userId);
	}

}
