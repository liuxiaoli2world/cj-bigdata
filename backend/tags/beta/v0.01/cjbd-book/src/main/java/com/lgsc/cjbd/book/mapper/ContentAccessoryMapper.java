package com.lgsc.cjbd.book.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lgsc.cjbd.book.model.ContentAccessory;
import com.lgsc.cjbd.dao.BaseMapper;

public interface ContentAccessoryMapper extends BaseMapper<ContentAccessory> {

	List<ContentAccessory> selectDownloadUrlById(@Param("id")Long id);
}