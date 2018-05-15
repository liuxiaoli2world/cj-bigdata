package com.lgsc.cjbd.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lgsc.cjbd.book.model.MenuSource;
import com.lgsc.cjbd.book.service.MenuSourceService;
import com.lgsc.cjbd.vo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 菜单资源
 * 
 * @author
 */
@Api(tags = "menusource", description = "菜单资源")
@RestController
@RequestMapping("/book/menusource")
public class MenuSourceController {

	@Autowired
	private MenuSourceService menuSourceService;

	/**
	 * 根据id查询
	 */
	@ApiOperation(value = "根据id查询")
	@RequestMapping(value = "/query/{menuSourceId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response select(@ApiParam(value = "菜单资源id", required = true) @PathVariable Long menuSourceId) {
		return new Response().success(menuSourceService.selectByPrimaryKey(menuSourceId));
	}
	
	/**
	 * 查询菜单及子菜单下是否有资源
	 */
	@ApiOperation(value = "查询菜单及子菜单下是否有资源")
	@RequestMapping(value = "/queryHasContent/{menuId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectHasContent(@ApiParam(value = "菜单id", required = true) @PathVariable Long menuId) {
		return new Response().success(menuSourceService.selectHasContent(menuId));
	}

	/**
	 * 查询所有菜单资源
	 */
	@ApiOperation(value = "查询所有菜单资源")
	@RequestMapping(value = "/queryAll", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectAll(
			@ApiParam(value = "当前页", required = true) @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@ApiParam(value = "每页的数量", required = true) @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
		return new Response().success(menuSourceService.selectAll(pageNum, pageSize));
	}

	/**
	 * 添加菜单资源
	 */
	@ApiOperation(value = "添加菜单资源")
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response insertSelective(
			@ApiParam(value = "菜单资源", required = true) @RequestBody @Validated MenuSource menuSource) {
		Response response = new Response();
		int num = menuSourceService.insertSelective(menuSource);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 修改菜单资源
	 */
	@ApiOperation(value = "修改菜单资源")
	@RequestMapping(value = "/modify", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response updateSelective(
			@ApiParam(value = "菜单资源", required = true) @RequestBody @Validated MenuSource menuSource) {
		Response response = new Response();
		int num = menuSourceService.updateByPrimaryKeySelective(menuSource);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 删除菜单资源
	 */
	@ApiOperation(value = "删除菜单资源")
	@RequestMapping(value = "/remove/{menuSourceId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response delete(@ApiParam(value = "", required = true) @PathVariable Long menuSourceId) {
		Response response = new Response();
		int num = menuSourceService.deleteByPrimaryKey(menuSourceId);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 根据关键词搜索资源
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@ApiOperation(value = "根据关键词搜索资源")
	@RequestMapping(value = "/selectSearch", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectSearch(
			@ApiParam(value = "当前页", required = true) @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@ApiParam(value = "每页的数量", required = true) @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
			@ApiParam(value = "关键词", required = false) @RequestParam(name = "keyword", required = false) String keyword,
			@ApiParam(value = "搜索范围  author title desc", required = true) @RequestParam(name = "scope", required = true) String scope) {
		return new Response().success(menuSourceService.selectSearch(pageNum, pageSize, keyword, scope));
	}

}
