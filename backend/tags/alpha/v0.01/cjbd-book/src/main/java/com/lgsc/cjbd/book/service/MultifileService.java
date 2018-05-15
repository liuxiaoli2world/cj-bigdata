package com.lgsc.cjbd.book.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lgsc.cjbd.book.mapper.MenuSourceMapper;
import com.lgsc.cjbd.book.mapper.MultifileMapper;
import com.lgsc.cjbd.book.mapper.MultiitemMapper;
import com.lgsc.cjbd.book.model.MenuSource;
import com.lgsc.cjbd.book.model.Multifile;
import com.lgsc.cjbd.book.model.Multiitem;

/**
 * 多媒体
 * 
 * @author Pengmeiyan
 *
 */
@Service
public class MultifileService {

	@Autowired
	private MultifileMapper multifileMapper;

	@Autowired
	private MenuSourceMapper menuSourceMapper;

	@Autowired
	private MultiitemMapper multiitemMapper;

	public int insert(Multifile record) {
		return multifileMapper.insert(record);
	}

	public int insertSelective(Multifile multifile) {
		return multifileMapper.insertSelective(multifile);
	}

	public Multifile selectByPrimaryKey(long id) {
		return multifileMapper.selectByPrimaryKey(id);
	}

	/**
	 * 根据id查询标题
	 * 
	 * @param id
	 * @return
	 */
	public String selectTitleById(long multifileId) {
		return multifileMapper.selectTitleById(multifileId);
	}

	public List<Multifile> selectAll() {
		return multifileMapper.selectAll();
	}

	/**
	 * 根据菜单查询所有的相册
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param menuPId
	 * @param menuId
	 * @return
	 */
	public PageInfo<Map<String, Object>> selectPicByMeauId(Integer pageNum, Integer pageSize, Long menuPId,
			Long menuId) {

		PageHelper.startPage(pageNum, pageSize);
		List<Map<String, Object>> list = multifileMapper.selectPicByMeauId(menuPId, menuId);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	public PageInfo<Multifile> selectAll(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Multifile> list = multifileMapper.selectAll();
		PageInfo<Multifile> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	public int updateByPrimaryKey(Multifile record) {
		return multifileMapper.updateByPrimaryKey(record);
	}

	public int updateByPrimaryKeySelective(Multifile multifile) {
		return multifileMapper.updateByPrimaryKeySelective(multifile);
	}

	/**
	 * 根据多媒体id删除
	 * 
	 * @param id
	 *            多媒体编号
	 * @return
	 */
	public int deleteByPrimaryKey(long id) {
		MenuSource menuSource = new MenuSource();
		menuSource.setSourceId(id);
		menuSource.setSourceType("multifile");
		menuSourceMapper.delete(menuSource); // 删除菜单资源表
		Multiitem multiitem = new Multiitem();
		multiitem.setMultifileId(id);
		multiitemMapper.delete(multiitem);
		return multifileMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 条件查询多媒体
	 * 
	 * @param pageNum
	 *            页码
	 * @param pageSize
	 *            每页数量
	 * @param parentMenuId
	 *            父菜单id
	 * @param childMenuId
	 *            子菜单id
	 * @param keyword
	 *            关键字（标题）
	 * @param multiType
	 *            资源类型
	 * @return
	 */
	public PageInfo<Map<String, Object>> selectBy(Integer pageNum, Integer pageSize, Long parentMenuId,
			Long childMenuId, String keyword, String multiType) {
		PageHelper.startPage(pageNum, pageSize);
		List<Map<String, Object>> list = multifileMapper.selectBy(parentMenuId, childMenuId, keyword, multiType);
		if (list.isEmpty()) {
			return new PageInfo<>();
		}
		return new PageInfo<>(list);
	}

	/**
	 * 根据菜单查询相册中某个相册的前一相册和后一相册
	 * 
	 * @param menuPId
	 * @param menuId
	 * @param multifileId
	 * @return
	 */
	public List<Map<String, Object>> selectFirstAndLastMultifile(Long menuPId, Long menuId, long multifileId) {
		// 根据菜单查询所有的相册
		List<Map<String, Object>> list = multifileMapper.selectPicByMeauId(menuPId, menuId);
		
		if(list == null || list.size() == 1){
			list = new ArrayList<Map<String, Object>>();
			list.add(null);
			list.add(null);
			return list;
		}
		
		// 存放根据菜单查询所有的相册id
		List<Long> idList = new ArrayList<Long>();
		// 存放前一相册和后一相册
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> map : list) {
			for (String key : map.keySet()) {
				if ("multifileId".equals(key))
					idList.add(Long.parseLong(map.get(key).toString()));
			}
		}
		
		for (int i = 0; i < idList.size(); i++) {
			if (idList.get(i) == multifileId) { //当前
				if (i == 0) { //首
					result.add(null); //前一个
					result.add(multifileMapper.selectPicByMultifileId(idList.get(i + 1))); //后一个
				} else if (i == idList.size() - 1) { //尾
					result.add(multifileMapper.selectPicByMultifileId(idList.get(i - 1)));
					result.add(null);
				} else {
					result.add(multifileMapper.selectPicByMultifileId(idList.get(i - 1)));
					result.add(multifileMapper.selectPicByMultifileId(idList.get(i + 1)));
				}
			}
		}
		
		return result;
	}

	/**
	 * 查询首页视频热点
	 * 
	 * @return
	 */
	public Map<String, Object> selectIndexVideo() {
		return multifileMapper.selectIndexVideo();
	}

	/**
	 * 插入多媒体
	 * 
	 * @param multifile
	 *            多媒体
	 * @param menuId
	 *            所属菜单
	 * @param path
	 *            封面url
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@Transactional
	public int insertMultifile(Multifile multifile, Long menuId, String path) throws UnsupportedEncodingException {
		if ("1".equals(multifile.getScene())) { // 使用场景 1=首页
			multifileMapper.setOtherSceneZero();
		} else {
			multifile.setScene("0");
		}
		Date date = new Date();
		multifile.setCreatedAt(date);
		multifile.setUpdatedAt(date);
		int num = multifileMapper.insertSelective(multifile);
		String name = "";
		String size = "";
		String suffix = "";
		path = URLDecoder.decode(path,"utf-8");
		String pattern = "^.+[?]name=(.+)(\\.\\w+)&size=(.*)$";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(path);
		if(m.find()){
	         name = m.group(1)+m.group(2);
	         size = m.group(3); 
	         suffix = m.group(2);
		}
		// 插入菜单资源表
		MenuSource menuSource = new MenuSource();
		menuSource.setMenuId(menuId);
		menuSource.setSourceId(multifile.getMultifileId());
		menuSource.setSourceType("multifile");
		menuSource.setCreatedAt(date);
		menuSource.setUpdatedAt(date);
		menuSourceMapper.insertSelective(menuSource);

		// 插入封面
		Multiitem multiitem = new Multiitem();
		multiitem.setMultifileId(multifile.getMultifileId());
		multiitem.setScene("1"); // 使用场景 1=封面 2=普通资源'
		multiitem.setPath(path);
		multiitem.setName(name);
		multiitem.setSize(size);
		multiitem.setSuffix(suffix);
		multiitem.setCreatedAt(date);
		multiitem.setUpdatedAt(date);
		multiitemMapper.insertSelective(multiitem);
		return num;
	}

	/**
	 * 修改多媒体
	 * 
	 * @param multifile
	 *            多媒体
	 * @param menuId
	 *            所属菜单
	 * @param path
	 *            封面url
	 * @return
	 */
	public int updateMultifile(Multifile multifile, Long menuId, String path) {
		if("1".equals(multifile.getScene())){	// 使用场景 1=首页
			multifileMapper.setOtherSceneZero();
		}
		Date date = new Date();
		multifile.setUpdatedAt(date);
		int num = multifileMapper.updateByPrimaryKeySelective(multifile);
		String name = path.substring(path.indexOf("?name=") + 6, path.indexOf("&size="));
		String size = path.substring(path.indexOf("&size=") + 6);
		String suffix = name.substring(name.lastIndexOf("."));

		// 修改菜单资源表
		MenuSource menuSource = new MenuSource();
		menuSource.setSourceId(multifile.getMultifileId());
		menuSource.setSourceType("multifile");
		MenuSource ms = menuSourceMapper.selectOne(menuSource);
		if (ms != null) {
			ms.setMenuId(menuId);
			ms.setUpdatedAt(date);
			menuSourceMapper.updateByPrimaryKeySelective(ms);
		}

		// 修改封面
		Multiitem multiitem = new Multiitem();
		multiitem.setMultifileId(multifile.getMultifileId());
		multiitem.setScene("1"); // 使用场景 1=封面 2=普通资源'
		Multiitem one = multiitemMapper.selectOne(multiitem);

		if (one != null) {
			one.setPath(path);
			one.setName(name);
			one.setSize(size);
			one.setSuffix(suffix);
			one.setUpdatedAt(date);
			multiitemMapper.updateByPrimaryKeySelective(one);
		} else {
			multiitem.setPath(path);
			multiitem.setName(name);
			multiitem.setSize(size);
			multiitem.setSuffix(suffix);
			multiitem.setCreatedAt(date);
			multiitem.setUpdatedAt(date);
			multiitemMapper.insertSelective(multiitem);
		}
		return num;
	}

	public Map<String, Object> selectById(Long id) {
		return multifileMapper.selectById(id);
	}

}
