package com.lgsc.cjbd.book.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.lgsc.cjbd.book.mapper.BookChannelMapper;
import com.lgsc.cjbd.book.mapper.ChannelMapper;
import com.lgsc.cjbd.book.model.BookChannel;
import com.lgsc.cjbd.book.model.Channel;

/**
 * 
 */
@Service
public class BookChannelService {

	@Autowired
	private BookChannelMapper bookChannelMapper;

	@Autowired
	private ChannelMapper channelMapper;

	public int insert(BookChannel record) {
		return bookChannelMapper.insert(record);
	}

	/**
	 * 添加图书分类关系
	 * 
	 * @param bookChannel
	 * @return
	 */
	public int insertSelective(BookChannel bookChannel) {
		int num = 0;
		Channel channel = new Channel();
		channel.setChannelId(bookChannel.getChannelId());
		if (channelMapper.selectOne(channel) != null) {
			num = bookChannelMapper.insertSelective(bookChannel);
		}
		return num;
	}

	public BookChannel selectByPrimaryKey(long id) {
		return bookChannelMapper.selectByPrimaryKey(id);
	}

	public List<BookChannel> selectAll() {
		return bookChannelMapper.selectAll();
	}

	public PageInfo<BookChannel> selectAll(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<BookChannel> list = bookChannelMapper.selectAll();
		PageInfo<BookChannel> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	public int updateByPrimaryKey(BookChannel record) {
		return bookChannelMapper.updateByPrimaryKey(record);
	}

	/**
	 * 修改图书分类关系
	 * 
	 * @param bookChannel
	 * @return
	 */
	public int updateByPrimaryKeySelective(BookChannel bookChannel) {
		int num = 0;
		Channel channel = new Channel();
		channel.setChannelId(bookChannel.getChannelId());
		if (channelMapper.selectOne(channel) != null) {
			num = bookChannelMapper.updateByPrimaryKeySelective(bookChannel);
		}
		return num;
	}

	/**
	 * 删除图书分类关系
	 * 
	 * @param bookChannelId
	 * @return
	 */
	public int deleteByPrimaryKey(Long bookChannelId) {
		return bookChannelMapper.deleteByPrimaryKey(bookChannelId);
	}

	/**
	 * 删除图书分类关系
	 * 
	 * @param isbn
	 * @return
	 */
	public int deleteByIsbn(String isbn) {
		BookChannel bookChannel = new BookChannel();
		bookChannel.setIsbn(isbn);
		return bookChannelMapper.delete(bookChannel);
	}

	public Channel selectByIsbn(String isbn) {
		return bookChannelMapper.selectByIsbn(isbn);
	}

}
