package com.lgsc.cjbd.remote.client;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * 失败回调
 * 
 * @author yeqj
 */
@Component
public class BookClientFallback implements BookClient {
	private static Logger log = LogManager.getLogger(UserClientFallback.class);

	@Override
	public List<Map<String, Object>> selectPeriodicalByAuthor(String author) {
		log.error("远程调用失败，根据专家学者查询期刊失败，参数：[author=" + author + "]");
		return null;
	}

	@Override
	public String selectBookDirDetail(Long bookDirId) {
		log.error("远程调用失败，根据图书目录id查询目录详情失败，参数：[bookDirId=" + bookDirId + "]");
		return null;
	}

}
