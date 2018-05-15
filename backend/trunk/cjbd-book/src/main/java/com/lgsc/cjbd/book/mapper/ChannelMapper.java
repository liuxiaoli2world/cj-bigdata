package com.lgsc.cjbd.book.mapper;

import java.util.List;
import java.util.Map;

import com.lgsc.cjbd.book.model.Channel;
import com.lgsc.cjbd.dao.BaseMapper;

public interface ChannelMapper extends BaseMapper<Channel> {

	List<Map<String, Object>> selectAllChannel();
}