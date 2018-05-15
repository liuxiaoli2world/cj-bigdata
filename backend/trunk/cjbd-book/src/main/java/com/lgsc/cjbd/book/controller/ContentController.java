package com.lgsc.cjbd.book.controller;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.lgsc.cjbd.book.model.Content;
import com.lgsc.cjbd.book.service.ContentService;
import com.lgsc.cjbd.vo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 内容管理
 * @author
 */
@Api(tags = "content", description = "内容管理")
@RestController
@RequestMapping("/book/content")
public class ContentController {

	@Autowired
	private ContentService contentService;

	public static final Logger logger = LogManager.getLogger(ContentController.class);

	/**
	 * 查询内容详细
	 */
	@ApiOperation(value = "查询内容详细")
	@RequestMapping(value = "/query/{id}/{from}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response select(
			@ApiParam(value = "内容编号", required = true) @PathVariable(name="id") Long contentId,
			@ApiParam(value = "访问标记 index==门户 admin==后台", required = true)@PathVariable(required=true,value="from") String from) {
		return new Response().success(contentService.selectById(contentId, from));
	}

	/**
	 * 前台分页查询相关内容
	 */
	@ApiOperation(value = "前台分页查询相关内容")
	@RequestMapping(value = "/queryAll", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectAll(
			@ApiParam(value = "当前页", required = true) @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@ApiParam(value = "每页的数量", required = true) @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
			@ApiParam(value = "查询的标记  notice=通知公告 headline=长江要闻 periodical=期刊 history=人文历史", required = true) @RequestParam(name = "flag") String flag,
			@ApiParam(value = "关键词", required = false) @RequestParam(name = "keyword",required=false) String keyword
			){

		return new Response().success(contentService.selectAllForIndex(pageNum, pageSize, flag,keyword));
	}

	/**
	 * 新增内容
	 */
	@ApiOperation(value = "新增内容 notice=通知公告 headline=长江要闻 periodical=期刊 history=人文历史")
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response insertSelective(@ApiParam(value = "内容", required = true) @RequestBody @Validated Content record,
			@ApiParam(value = "一级菜单(期刊传)", required = false) @RequestParam(name = "menuPId", required = false) Long menuPId,
			@ApiParam(value = "二级菜单(期刊传)", required = false) @RequestParam(name = "menuId", required = false) Long menuId) {
		Response response = new Response();
		int num = contentService.insertContent(record, menuPId, menuId);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 修改内容
	 */
	@ApiOperation(value = "修改内容")
	@RequestMapping(value = "/modify", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response updateSelective(@ApiParam(value = "", required = true) @RequestBody @Validated Content record,
			@ApiParam(value = "一级菜单(期刊传)", required = false) @RequestParam(name = "menuPId") Long menuPId,
			@ApiParam(value = "二级菜单(期刊传)", required = false) @RequestParam(name = "menuId") Long menuId) {
		Response response = new Response();
		int num = contentService.updateByPrimaryKeySelective(record, menuPId, menuId);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 删除相关的内容
	 */
	@ApiOperation(value = "删除内容")
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response delete(@ApiParam(value = "删除内容的编号", required = true) @PathVariable Long id) {
		Response response = new Response();
		int num = contentService.deleteByPrimaryKey(id);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 查询点击排行榜前8位
	 * 
	 * @return
	 */
	@ApiOperation(value = "查询点击排行榜前8位 notice=通知公告 headline=长江要闻 ")
	@RequestMapping(value = "/selectByPvRank", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectByPvRank(
			@ApiParam(value = "内容的类型", required = true) @RequestParam(name = "contentType") String contentType) {
		Response response = new Response();
		List<Content> list = contentService.selectByPvRank(contentType);
		if (list != null && list.size() != 0) {
			response.success(list);
		} else {
			response.failure();
		}
		return response;
	}

	@ApiOperation(value = "条件查询咨询")
	@RequestMapping(value = "/selectNewsByCondition", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectNewsByCondition(
			@ApiParam(value = "当前页", required = true) @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@ApiParam(value = "每页的数量", required = true) @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
			@ApiParam(value = "查询的标记 notice=通知公告 headline=长江要闻", required = true) @RequestParam(name = "contentType",required=true) String contentType,
			@ApiParam(value = "标题或者是正文，不传就是查询全部", required = false) @RequestParam(name = "condition",required=false) String condition) {
		Response response = new Response();
		PageInfo<Content> pageInfo = contentService.selectNewsByCondition(pageNum, pageSize, contentType, condition);
		return response.success(pageInfo);
	}

	@ApiOperation(value = "根据菜单id查询期刊")
	@RequestMapping(value = "/selectPeriodicalByMeauId", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectPeriodicalByMeauId(
			@ApiParam(value = "当前页", required = true) @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@ApiParam(value = "每页的数量", required = true) @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
			@ApiParam(value = "一级菜单(期刊传)", required = false) @RequestParam(name = "menuPId", required = true) Long menuPId,
			@ApiParam(value = "二级菜单(期刊传)", required = false) @RequestParam(name = "menuId", required = false) Long menuId,
			@ApiParam(value = "关键词(查询全部的时候不用传)", required = false) @RequestParam(name = "keyword", required = false) String keyword) {
		Response response = new Response();
		PageInfo<Content> pageInfo = contentService.selectPeriodicalByMeauId(pageNum, pageSize, menuPId, menuId,
				keyword);
		return response.success(pageInfo);
	}

	@ApiOperation(value = "查询期刊的下载链接")
	@RequestMapping(value = "/selectDownloadUrl/{id}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectDownloadUrl(@ApiParam(value = "期刊id", required = true) @PathVariable Long id) {
		Response response = new Response();
		return response.success(contentService.selectDownloadUrl(id));
	}

	@ApiOperation(value = "查询所有人文历史")
	@RequestMapping(value = "/selectAllHistory", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectAllHistory() {
		Response response = new Response();
		return response.success(contentService.selectAllHistory());
	}

	/**
	 * 根据作者查询期刊
	 * 
	 * @param author
	 * @return
	 */
	@ApiOperation(value = "根据作者查询期刊")
	@RequestMapping(value = "/selectPeriodicalByAuthor", method = RequestMethod.POST)
	public List<Map<String, Object>> selectPeriodicalByAuthor(@RequestParam("author") String author) {
		return contentService.selectPeriodicalByAuthor(author);
	}

	/**
	 * 更新点击量
	 * 
	 * @return
	 */
	@ApiOperation(value = "更新点击量")
	@RequestMapping(value = "/updatepv/{id}", method = RequestMethod.POST)
	public Response updatePv(@ApiParam(value = "内容编号", required = true) @PathVariable Long id) {
		int num = contentService.updatePv(id);
		Response response = new Response();
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 获取实时时间浏览量
	 * 
	 * @return
	 */
	@ApiOperation(value = "获取实时总浏览量")
	@RequestMapping(value = "/getTotalPv", method = RequestMethod.POST)
	public Response getTotalPv() {
		Integer total = contentService.getTotalPv();
		return new Response().success(total);
	}

	/**
	 * 全文索引根据(全文作者篇名)
	 * @return
	 */
	@ApiOperation(value = "全文索引")
	@RequestMapping(value = "/selectFullText", method = RequestMethod.POST)
	public Response selectFullText(@ApiParam(value = "当前页", required = true) @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@ApiParam(value = "每页的数量", required = true) @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
			@ApiParam(value = "关键词", required = false) @RequestParam(name = "keyword", required = false) String keyword){
		PageInfo<Content> pageInfo = contentService.selectFullText(pageNum,pageSize,keyword);
		return new Response().success(pageInfo);
	}
	/**
	 * 热词中根据标题,作者,关键词查询
	 * @return
	 */
	@ApiOperation(value = "热词中根据标题,作者,关键词查询")
	@RequestMapping(value = "/selectForHotWords", method = RequestMethod.POST)
	public Response selectForHotWords(
			@ApiParam(value = "标题", required = false) @RequestParam(name = "title", required = false) String title,
			@ApiParam(value = "作者", required = false) @RequestParam(name = "author", required = false) String author,
			@ApiParam(value = "关键词", required = false) @RequestParam(name = "keyword", required = false) String keyword,
			@ApiParam(value = "当前页", required = true) @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@ApiParam(value = "每页的数量", required = true) @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
			@ApiParam(value = "排序的顺序(0按照更新时间,1为按照出版时间排序)",required = false) @RequestParam(name="orderBy",defaultValue="0") Integer orderBy){
		
		PageInfo<Content> pageInfo = contentService.selectForHotWords(title,author,keyword,pageNum,pageSize,orderBy);
	    return new Response().success(pageInfo);    
	
	}
	/**
	 * 实时文章的数量
	 * @return
	 */
	@ApiOperation(value = "实时文章的数量")
	@RequestMapping(value = "/getTotalContent", method = RequestMethod.POST)
	public Response getTotalContent(){
		return new Response().success(contentService.getTotalContent());
	}
	
	@ApiOperation(value = "后台条件查询内容")
	@RequestMapping(value = "/selectContentByCondition", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectContentByCondition(
			@ApiParam(value = "当前页", required = true) @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@ApiParam(value = "每页的数量", required = true) @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
			@ApiParam(value = "查询的标记  notice=通知公告 headline=长江要闻 periodical=期刊 history=人文历史", required = true) @RequestParam(name = "contentType") String contentType,
			@ApiParam(value = "标题或者是正文 不传查询所有", required = false) @RequestParam(name = "condition",required =false) String condition) {
		Response response = new Response();
		PageInfo<Content> pageInfo = contentService.selectContentByCondition(pageNum, pageSize, contentType, condition);
		return response.success(pageInfo);
	}

}
