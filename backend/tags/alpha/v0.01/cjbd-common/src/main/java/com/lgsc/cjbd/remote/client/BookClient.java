package com.lgsc.cjbd.remote.client;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cjbd-book", fallback = BookClientFallback.class)
public interface BookClient {
	/**
	 * 根据作者查询期刊
	 * 
	 * @param author
	 * @return
	 */
	@RequestMapping(value = "/book/content/selectPeriodicalByAuthor", method = RequestMethod.POST)
	public List<Map<String, Object>> selectPeriodicalByAuthor(@RequestParam("author") String author);
	
	/**
	 * 根据图书目录id查询目录详情
	 * @param bookDirId
	 * @return
	 */
	@RequestMapping(value = "/book/bookdir/queryDetail", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public String selectBookDirDetail(@RequestParam("bookDirId") Long bookDirId);
}
