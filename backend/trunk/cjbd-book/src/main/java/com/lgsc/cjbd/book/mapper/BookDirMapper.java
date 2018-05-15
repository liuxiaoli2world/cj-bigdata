package com.lgsc.cjbd.book.mapper;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.*;

import org.apache.ibatis.annotations.Param;

import com.lgsc.cjbd.book.model.BookDir;
import com.lgsc.cjbd.dao.BaseMapper;

public interface BookDirMapper extends BaseMapper<BookDir> {

	/**
	 * 根据图书目录id查询祖先节点，不包括当前目录
	 */
	default List<BookDir> selectParentList(@Param("bookDirId") Long bookDirId){
		return selectParentList0(bookDirId,getParentBookDirIds(bookDirId));
		
	}
	List<BookDir> selectParentList0(@Param("bookDirId") Long bookDirId,	
			@Param("bookDirIds") List<Long> bookDirIds);

	/**
	 * 根据图书目录id查询后代节点，不包括当前目录
	 */
	default List<BookDir> selectChildList(@Param("bookDirId") Long bookDirId){
		return selectChildList0(bookDirId,getChildBookDirIds(bookDirId));
		
	}
	List<BookDir> selectChildList0(@Param("bookDirId") Long bookDirId,
			@Param("bookDirIds") List<Long> bookDirIds);
	
	

	/**
	 * 根据图书目录id查询子目录
	 */
	List<BookDir> selectChild(@Param("bookDirId") Long bookDirId);

	/**
	 * 查询图书商品所需信息
	 * 
	 * @param bookDirId
	 * @return
	 */
	Map<String, Object> selectBookOrderInfo(@Param("bookDirId") Long bookDirId);

	/**
	 * 根据 isbn查询最后一个根目录
	 * 
	 * @param isbn
	 * @return
	 */
	BookDir selectLastOneByIsbn(@Param("isbn") String isbn);

	/**
	 * 根据parentBookDirId查询最后一个子目录
	 * 
	 * @param isbn
	 * @return
	 */
	BookDir selectLastOneByParentBookDirId(@Param("parentBookDirId") Long parentBookDirId);

	/**
	 * 根据bookDirId查询所后代节点,包括当前目录
	 * 
	 * @param bookDirId
	 * @return
	 */
	default List<Long> selectdescendantList(@Param("bookDirId") Long bookDirId){
		return selectdescendantList0(getChildBookDirIds(bookDirId));
		
	}
	List<Long> selectdescendantList0(@Param("bookDirIds") List<Long> bookDirIds);
		
	

	/**
	 * 根据isbn查询所有目录
	 * 
	 * @param isbn
	 * @return
	 */
	List<Map<String, Object>> selectAllByIsbn(@Param("isbn") String isbn);

	/**
	 * 根据parentBookDirId查询第一个叶子节点
	 * 
	 * @param parentBookDirId
	 * @return
	 */
	default Long selectFirstLeafByParentBookDirId(Long parentBookDirId) {
		return selectFirstLeafByParentBookDirId0(getChildBookDirIds(parentBookDirId));
	}
	
	Long selectFirstLeafByParentBookDirId0(@Param("bookDirIds") List<Long> bookDirIds);

	/**
	 * 根据parentBookDirId查询子目录id字符串(包括当前目录)
	 * @param parentBookDirId
	 * @return
	 */
	Map<String,Object> selectChildBookDirIds(@Param("parentBookDirId") Long parentBookDirId);

	/**
	 * 根据bookDirId查询祖先目录id字符串(包括当前目录)
	 * 
	 * @param bookDirId
	 * @return
	 */
	Map<String,Object> selectParentBookDirIds(@Param("bookDirId") Long bookDirId);

	
	default List<Long> getChildBookDirIds(Long parentBookDirId) {
		String ids = (String) selectChildBookDirIds(parentBookDirId).get("ids");
		return Arrays.stream(ids.split(",")).map(s -> Long.parseLong(s)).collect(toList());
	}
	
	
	default List<Long> getParentBookDirIds(Long bookDirId) {
		String ids = (String) selectParentBookDirIds(bookDirId).get("ids");
		return Arrays.stream(ids.split(",")).map(s -> Long.parseLong(s)).collect(toList());
	}

}