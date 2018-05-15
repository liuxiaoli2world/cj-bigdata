package com.lgsc.cjbd.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lgsc.cjbd.user.model.UserRole;
import com.lgsc.cjbd.user.service.UserRoleService;
import com.lgsc.cjbd.vo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @author
 */
@Api(tags = "userrole", description = "角色关系管理")
@RestController
@RequestMapping("/user/userrole")
public class UserRoleController {

	@Autowired
	private UserRoleService userRoleService;

	/**
	 * 
	 */
	@ApiOperation(value = "查询角色关系详细")
	@RequestMapping(value = "/query/{id}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response select(@ApiParam(value = "角色id", required = true) @PathVariable Long id) {
		return new Response().success(userRoleService.selectByPrimaryKey(id));
	}

	/**
	 * 
	 */
	@ApiOperation(value = "查询全部角色关系")
	@RequestMapping(value = "/queryAll", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectAll(
			@ApiParam(value = "当前页", required = true) @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@ApiParam(value = "每页的数量", required = true) @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
		return new Response().success(userRoleService.selectAll(pageNum, pageSize));
	}

	/**
	 * 
	 */
	@ApiOperation(value = "新增角色关系")
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response insertSelective(@ApiParam(value = "角色", required = true) @RequestBody @Validated UserRole record) {
		Response response = new Response();
		int num = userRoleService.insertSelective(record);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 新增专家学者角色关系
	 * 
	 * @param userId
	 * @return
	 */
	@ApiOperation(value = "新增专家学者角色关系")
	@RequestMapping(value = "/insertExpertRole", method = RequestMethod.POST)
	public Response insertExpertRole(@RequestParam Long userId) {
		Response response = new Response();
		int num = userRoleService.insertExpertRole(userId);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 修改角色
	 */
	@ApiOperation(value = "修改角色关系")
	@RequestMapping(value = "/modify", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response updateSelective(@ApiParam(value = "角色", required = true) @RequestBody @Validated UserRole record) {
		Response response = new Response();
		int num = userRoleService.updateByPrimaryKeySelective(record);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 删除角色
	 */
	@ApiOperation(value = "删除角色")
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response delete(@ApiParam(value = "角色", required = true) @PathVariable Long id) {
		Response response = new Response();
		int num = userRoleService.deleteByPrimaryKey(id);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 根据userId删除用户角色关系(专家只有一个角色)
	 * 
	 * @param userId
	 * @return
	 */
	@ApiOperation(value = "根据userId查询用户角色关系")
	@RequestMapping(value = "/selectRoleByuserId", method = RequestMethod.POST)
	public Response deleteRoleByuserId(@RequestParam("userId") Long userId) {
		return new Response().success(userRoleService.deleteRoleByuserId(userId));
	}
	
	/**
	 * 根据用户id查询用户角色状态
	 * @param userId
	 * @return
	 */
	@ApiOperation(value = "根据用户id查询用户角色状态")
	@RequestMapping(value = "/selectRoleStatus" ,method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public List<String> selectRoleStatus(@ApiParam(value = "用户id", required = true) @RequestParam("userId") Long userId) {
		return userRoleService.selectRoleStatus(userId);
	}
}
