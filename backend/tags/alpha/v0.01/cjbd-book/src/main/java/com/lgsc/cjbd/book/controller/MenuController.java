package com.lgsc.cjbd.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lgsc.cjbd.book.model.Menu;
import com.lgsc.cjbd.book.service.MenuService;
import com.lgsc.cjbd.vo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 菜单
 * 
 * @author
 */
@Api(tags = "menu", description = "菜单")
@RestController
@RequestMapping("/book/menu")
public class MenuController {

	@Autowired
	private MenuService menuService;

	/**
	 * 查询单个菜单
	 */
	@ApiOperation(value = "查询单个菜单")
	@RequestMapping(value = "/query/{menuId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response select(@ApiParam(value = "菜单编号", required = true) @PathVariable Long menuId) {
		return new Response().success(menuService.selectByPrimaryKey(menuId));
	}

	/**
	 * 查询所有根菜单
	 */
	@ApiOperation(value = "查询所有根菜单")
	@RequestMapping(value = "/queryRoot", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectRoot() {
		return new Response().success(menuService.selectRoot());
	}

	/**
	 * 根据菜单id查询祖先节点
	 */
	@ApiOperation(value = "根据菜单id查询祖先节点")
	@RequestMapping(value = "/queryParentList/{menuId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectParentList(@ApiParam(value = "菜单编号", required = true) @PathVariable Long menuId) {
		return new Response().success(menuService.selectParentList(menuId));
	}

	/**
	 * 根据菜单id查询后代节点
	 */
	@ApiOperation(value = "根据菜单id查询后代节点")
	@RequestMapping(value = "/queryChildList/{menuId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectChildList(@ApiParam(value = "菜单编号", required = true) @PathVariable Long menuId) {
		return new Response().success(menuService.selectChildList(menuId));
	}

	/**
	 * 根据菜单id查询子菜单
	 */
	@ApiOperation(value = "根据菜单id查询子菜单")
	@RequestMapping(value = "/queryChild/{menuId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectChild(@ApiParam(value = "菜单编号", required = true) @PathVariable Long menuId) {
		return new Response().success(menuService.selectChild(menuId));
	}

	/**
	 * 查询所有菜单
	 */
	@ApiOperation(value = "查询所有菜单")
	@RequestMapping(value = "/queryAll", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectAll() {
		return new Response().success(menuService.selectAll());
	}

	/**
	 * 新增菜单
	 */
	@ApiOperation(value = "新增菜单")
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response insertSelective(@ApiParam(value = "菜单", required = true) @RequestBody @Validated Menu menu) {
		Response response = new Response();
		int num = menuService.insertSelective(menu);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 修改菜单
	 */
	@ApiOperation(value = "修改菜单")
	@RequestMapping(value = "/modify", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response updateSelective(@ApiParam(value = "菜单", required = true) @RequestBody @Validated Menu record) {
		Response response = new Response();
		if (record.getMenuId() != null) {
			int num = menuService.updateByPrimaryKeySelective(record);
			if (num > 0) {
				response.success();
			} else {
				response.failure();
			}
		} else {
			response.failure("菜单编号不能为空！");
		}
		return response;
	}

	/**
	 * 删除菜单
	 */
	@ApiOperation(value = "删除菜单")
	@RequestMapping(value = "/remove/{menuId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response delete(@ApiParam(value = "", required = true) @PathVariable Long menuId) {
		return new Response().success(menuService.deleteByPrimaryKey(menuId));
	}
}
