package com.lgsc.cjbd.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lgsc.cjbd.dao.BaseMapper;
import com.lgsc.cjbd.user.model.UserBook;

public interface UserBookMapper extends BaseMapper<UserBook> {
	/**
	 * 查询用户已购买的根目录
	 * 
	 * @param isbn
	 * @param userId
	 * @return
	 */
	List<Long> selectRootDirsOfUser(@Param("isbn") String isbn, @Param("userId") Long userId);
}