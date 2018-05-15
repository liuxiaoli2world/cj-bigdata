package com.lgsc.cjbd.book.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lgsc.cjbd.book.dto.HistoryDto;
import com.lgsc.cjbd.book.mapper.ContentAccessoryMapper;
import com.lgsc.cjbd.book.mapper.ContentMapper;
import com.lgsc.cjbd.book.mapper.MenuSourceMapper;
import com.lgsc.cjbd.book.model.Content;
import com.lgsc.cjbd.book.model.ContentAccessory;
import com.lgsc.cjbd.book.model.MenuSource;
import com.lgsc.cjbd.book.util.SourceType;

/**
 * 内容管理服务层
 */
@Service
public class ContentService {

	@Autowired
	private ContentAccessoryMapper contentAccessoryMapper;

	@Autowired
	private ContentMapper contentMapper;

	@Autowired
	private MenuSourceMapper menuSourceMapper;

	public static final Logger logger = LogManager.getLogger(ContentService.class);

	public int insert(Content record) {

		return contentMapper.insert(record);
	}

	@Transactional(rollbackFor = Exception.class)
	public int insertContent(Content record, Long menuPId, Long menuId) {
		if (record.getCreatedAt() == null) { //插入时间
			Date date = new Date();
			record.setCreatedAt(date);
			record.setUpdatedAt(date);
		}else {
			record.setUpdatedAt(record.getCreatedAt());
		}
		
		record.setPv(0);
		int num = contentMapper.insertConent(record);
		if (menuId != null || menuPId != null) {
			Long menuPerId = (menuId != null ? menuId : menuPId);
			MenuSource menuSource = new MenuSource();
			menuSource.setCreatedAt(new Date());
			menuSource.setUpdatedAt(new Date());
			menuSource.setSourceType(SourceType.CONTENT);
			menuSource.setSourceId(record.getContentId());
			menuSource.setMenuId(menuPerId);
			menuSourceMapper.insertSelective(menuSource);
			logger.info("插入菜单关系成功");
		}
		Long contentId = record.getContentId();
		List<ContentAccessory> contentAccessories = record.getContentAccssoryList();
		if (contentAccessories != null && contentAccessories.size() != 0) {
			for (ContentAccessory contentAccessory : contentAccessories) {
				contentAccessory.setContentId(contentId);
				contentAccessoryMapper.insertSelective(contentAccessory);
			}
			logger.info("插入内容附件成功");
		}

		return num;
	}

	public int insertUseGeneratedKeys(Content record) {

		return contentMapper.insertUseGeneratedKeys(record);
	}

	public Content selectByPrimaryKey(long id) {
		return contentMapper.selectByPrimaryKey(id);
	}

	public List<Content> selectAll() {
		return contentMapper.selectAll();
	}

	public PageInfo<Content> selectAllForIndex(int pageNum, int pageSize, String flag, String keyword) {
		PageHelper.startPage(pageNum, pageSize);
		List<Content> list = contentMapper.selectAllForIndex(flag, keyword);
		PageInfo<Content> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	public int updateByPrimaryKey(Content record) {
		return contentMapper.updateByPrimaryKey(record);
	}

	/**
	 * 更新内容
	 * 
	 * @param record
	 * @param menuPId
	 * @param menuId
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public int updateByPrimaryKeySelective(Content record, Long menuPId, Long menuId) {
		record.setUpdatedAt(new Date());
		int num = contentMapper.updateByPrimaryKeySelective(record);
		if (menuId != null) {
			// 查询关系menuSource
			MenuSource temp = new MenuSource();
			temp.setSourceId(record.getContentId());
			temp.setSourceType(SourceType.CONTENT);
			MenuSource menuSource = menuSourceMapper.selectOne(temp);
			if (menuSource != null) {//更新关系
				menuSource.setMenuId(menuId);
				menuSource.setUpdatedAt(new Date());
				menuSourceMapper.updateByPrimaryKeySelective(menuSource);
			} else {//插入关系
				MenuSource menuSource1 = new MenuSource();
				Date date = new Date();
				menuSource1.setMenuId(menuId);
				menuSource1.setSourceType(SourceType.CONTENT);
				menuSource1.setSourceId(record.getContentId());
				menuSource1.setCreatedAt(date);
				menuSource1.setUpdatedAt(date);
				menuSourceMapper.insertSelective(menuSource1);
			}
		} else {// menuId == null 删除关系
			MenuSource temp = new MenuSource();
			temp.setSourceId(record.getContentId());
			temp.setSourceType(SourceType.CONTENT);
			menuSourceMapper.delete(temp);
		}

		// 更新附件
		List<ContentAccessory> contentAccessories = contentAccessoryMapper.selectDownloadUrlById(record.getContentId());
		if (contentAccessories != null && contentAccessories.size() != 0) {
			for (ContentAccessory contentAccessory : contentAccessories) {
				contentAccessoryMapper.delete(contentAccessory);
			}
		}

		List<ContentAccessory> list = record.getContentAccssoryList();
		if (list != null && list.size() != 0) {
			for (ContentAccessory contentAccessory : list) {
				contentAccessory.setContentId(record.getContentId());
				contentAccessoryMapper.insert(contentAccessory);
			}
		}
		logger.info("更新资源附件成功");
		logger.info("更新菜单关系成功");

		return num;
	}

	/**
	 * 通过主键删除内容
	 * 
	 * @param id
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public int deleteByPrimaryKey(long id) {
		MenuSource menuSource = new MenuSource();
		menuSource.setSourceId(id);
		menuSource.setSourceType(SourceType.CONTENT);
		menuSourceMapper.deleteBySourceId(menuSource);
		ContentAccessory contentAccessory = new ContentAccessory();
		contentAccessory.setContentId(id);
		contentAccessoryMapper.delete(contentAccessory);
		logger.info("删除菜单关系成功");
		logger.info("删除附件成功");
		int num = contentMapper.deleteByPrimaryKey(id);
		return num;
	}

	/**
	 * 返回点击排行榜前八
	 * 
	 * @param contentType
	 * @return
	 */
	public List<Content> selectByPvRank(String contentType) {
		List<Content> list = contentMapper.selectByPvRank(contentType);
		return list;

	}

	/**
	 * 条件查询
	 * 
	 * @param contentType
	 * @param condition
	 * @return
	 */
	public PageInfo<Content> selectNewsByCondition(Integer pageNum, Integer pageSize, String contentType,
			String condition) {
		PageHelper.startPage(pageNum, pageSize);
		List<Content> list = contentMapper.selectNewsByConditon(contentType, condition);
		PageInfo<Content> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	/**
	 * 根据菜单查询所有期刊
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param menuPId
	 * @param menuId
	 * @return
	 */
	public PageInfo<Content> selectPeriodicalByMeauId(Integer pageNum, Integer pageSize, Long menuPId, Long menuId,
			String keyword) {
		PageHelper.startPage(pageNum, pageSize);
		List<Content> list = contentMapper.selectPeriodicalByMeauId(menuPId, menuId, keyword);
		PageInfo<Content> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	/**
	 * 查询期刊的下载链接
	 * 
	 * @return
	 */
	public List<ContentAccessory> selectDownloadUrl(Long id) {

		return contentAccessoryMapper.selectDownloadUrlById(id);
	}

	/**
	 * 查询所有人文历史
	 * 
	 * @return
	 */
	public List<HistoryDto> selectAllHistory() {
		return contentMapper.selectAllHistory();
	}

	/**
	 * 根据作者查询期刊
	 * 
	 * @param author
	 * @return
	 */
	public List<Map<String, Object>> selectPeriodicalByAuthor(String author) {
		return contentMapper.selectPeriodicalByAuthor(author);
	}

	/**
	 * 更新点击量
	 * 
	 * @param id
	 * @return
	 */
	public Integer updatePv(Long id) {
		Content content = contentMapper.selectByPrimaryKey(id);
		Integer pv = content.getPv();
		if (pv == null) {
			pv = 0;
		}
		pv++;
		content.setPv(pv);
		return contentMapper.updateByPrimaryKey(content);
	}

	/**
	 * 实时获取浏览量
	 * 
	 * @return
	 */
	public Integer getTotalPv() {
		Integer totalPv = contentMapper.getTotalPv();
		return totalPv;
	}

	public PageInfo<Content> selectFullText(Integer pageNum, Integer pageSize, String keyword) {
		PageHelper.startPage(pageNum, pageSize);
		List<Content> list = contentMapper.selectFullText(keyword);
		PageInfo<Content> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	public PageInfo<Content> selectForHotWords(String title, String author, String keyword, Integer pageNum,
			Integer pageSize, Integer orderBy) {
		PageHelper.startPage(pageNum, pageSize);
		List<Content> list = contentMapper.selectForHotWords(title, author, keyword, orderBy);
		PageInfo<Content> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	public Integer getTotalContent() {

		return contentMapper.getTotalContent();
	}

	/**
	 * 根据id查询内容详细，带菜单
	 * 
	 * @param contentId
	 * @return
	 */
	public Map<String, Object> selectById(Long contentId, String from) {
		if (from.equals("index")) {
			updatePv(contentId); // 前台,更新点击量
		}

		MenuSource menuSource = new MenuSource();
		menuSource.setSourceId(contentId);
		menuSource.setSourceType(SourceType.CONTENT);
		menuSource = menuSourceMapper.selectOne(menuSource);
		Map<String, Object> map = null;
		if (menuSource != null) {
			map = contentMapper.selectById(contentId);
		} else {
			map = contentMapper.selectByIdWithoutMenu(contentId);
			map.put("parentMenuId", null);
			map.put("parentMenuName", null);
			map.put("menuId", null);
			map.put("menuName", null);
		}

		return map;
	}

	/**
	 * 条件查询内容
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param contentType
	 * @param condition
	 * @return
	 */
	public PageInfo<Content> selectContentByCondition(Integer pageNum, Integer pageSize, String contentType,
			String condition) {
		PageHelper.startPage(pageNum, pageSize);
		List<Content> list = contentMapper.selectContentByCondition(contentType, condition);
		PageInfo<Content> pageInfo = new PageInfo<>(list);
		return pageInfo;

	}

}
