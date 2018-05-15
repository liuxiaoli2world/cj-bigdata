package com.lgsc.cjbd.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lgsc.cjbd.user.model.Allow;
import com.lgsc.cjbd.user.service.AllowService;
import com.lgsc.cjbd.vo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 权限管理
 * 
 * @author
 */
@Api(tags = "allow", description = "权限管理")
@RestController
@RequestMapping("/user/allow")
public class AllowController {

	@Autowired
	private AllowService allowService;

	/**
	 * 查询权限详细
	 */
	@ApiOperation(value = "查询权限详细")
	@RequestMapping(value = "/query/{id}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response select(@ApiParam(value = "", required = true) @PathVariable Long id) {
		return new Response().success(allowService.selectByPrimaryKey(id));
	}

	/**
	 * 
	 */
	@ApiOperation(value = "查询所有权限")
	@RequestMapping(value = "/queryAll", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectAll() {
		return new Response().success(allowService.selectAll());
	}

	/**
	 * 
	 */
	@ApiOperation(value = "新增权限")
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response insertSelective(@ApiParam(value = "权限", required = true) @RequestBody @Validated Allow record) {
		Response response = new Response();
		int num = allowService.insertSelective(record);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 
	 */
	@ApiOperation(value = "修改权限")
	@RequestMapping(value = "/modify", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response updateSelective(@ApiParam(value = "权限", required = true) @RequestBody @Validated Allow record) {
		Response response = new Response();
		int num = allowService.updateByPrimaryKeySelective(record);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 
	 */
	@ApiOperation(value = "删除权限")
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response delete(@ApiParam(value = "权限编号", required = true) @PathVariable Long id) {
		Response response = new Response();
		int num = allowService.deleteByPrimaryKey(id);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 通过角色id查询权限
	 */
	@ApiOperation(value = "通过角色id查询权限 ")
	@RequestMapping(value = "/findByRoleId/{id}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response findByRoleId(@ApiParam(value = "角色id", required = true) @PathVariable Long id) {
		Response response = new Response();
		return response.success(allowService.findByRoleId(id));
	}

	/**
	 * 根据用户id查询对应的权限的值
	 */
	@ApiOperation(value = "通过用户id查询权限 ")
	@RequestMapping(value = "/findByUserId/{userId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response findByUserId(@ApiParam(value = "用户id", required = true) @PathVariable("userId") Long id) {
		Response response = new Response();
		return response.success(allowService.findByUserId(id));
	}

	/**
	 * 配置权限
	 */
	@ApiOperation(value = "配置权限")
	@RequestMapping(value = "/updateAllow", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response updateAllow(@ApiParam(value = "角色id", required = true) @RequestParam("id") Long id,
			@ApiParam(value = "权限编号", required = true) @RequestParam("allows") String allows) {
		Response response = new Response();
		boolean result = allowService.updateAllow(id,allows);
	    return result ? response.success() : response.failure();
	}

}
