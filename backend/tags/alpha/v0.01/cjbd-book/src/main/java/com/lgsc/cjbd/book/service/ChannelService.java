package com.lgsc.cjbd.book.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lgsc.cjbd.book.mapper.BookChannelMapper;
import com.lgsc.cjbd.book.mapper.ChannelMapper;
import com.lgsc.cjbd.book.model.BookChannel;
import com.lgsc.cjbd.book.model.Channel;

/**
 * 图书分类
 */
@Service
public class ChannelService {

	@Autowired
	private ChannelMapper channelMapper;

	@Autowired
	private BookChannelMapper bookChannelMapper;

	public int insert(Channel record) {
		return channelMapper.insert(record);
	}

	/**
	 * 添加分类
	 * 
	 * @param channel
	 * @return
	 */
	public int insertSelective(Channel channel) {
		channel.setParentChannelId(0L);
		return channelMapper.insertSelective(channel);
	}

	public Channel selectByPrimaryKey(long id) {
		return channelMapper.selectByPrimaryKey(id);
	}

	public List<Map<String, Object>> selectAll() {
		List<Map<String, Object>> list = channelMapper.selectAllChannel();
		return list;
	}

	public PageInfo<Channel> selectAll(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Channel> list = channelMapper.selectAll();
		PageInfo<Channel> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	public int updateByPrimaryKey(Channel record) {
		return channelMapper.updateByPrimaryKey(record);
	}

	/**
	 * 修改分类
	 * 
	 * @param channel
	 * @return
	 */
	public int updateByPrimaryKeySelective(Channel channel) {
		channel.setParentChannelId(0L);
		return channelMapper.updateByPrimaryKeySelective(channel);
	}

	/**
	 * 删除分类
	 * 
	 * @param channelId
	 * @return
	 */
	@Transactional
	public int deleteByPrimaryKey(Long channelId) {
		BookChannel bookChannel = new BookChannel();
		bookChannel.setChannelId(channelId);
		bookChannelMapper.delete(bookChannel); // 删除图书分类关系
		return channelMapper.deleteByPrimaryKey(channelId); // 删除分类
	}

}
