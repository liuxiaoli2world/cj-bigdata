package com.lgsc.cjbd.user.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lgsc.cjbd.user.model.Role;
import com.lgsc.cjbd.user.model.RoleAllow;
import com.lgsc.cjbd.user.service.RoleAllowService;
import com.lgsc.cjbd.user.service.RoleService;
import com.lgsc.cjbd.vo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 角色管理
 * @author 
 */
@Api(tags = "role", description = "角色管理")
@RestController
@RequestMapping("/user/role")
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private RoleAllowService roleAllowService;
    
    /**
	 * 
	 */
	@ApiOperation(value = "查询角色详细")
	@RequestMapping(value = "/query/{id}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response select(@ApiParam(value = "查询用户角色详细", required = true) @PathVariable Long id) {
		return new Response().success(roleService.selectByPrimaryKey(id));
	}
	
	/**
	 * 
	 */
	@ApiOperation(value = "查询所有角色")
	@RequestMapping(value = "/queryAll", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectAll(@ApiParam(value = "当前页", required = true) @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@ApiParam(value = "每页的数量", required = true) @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
		return new Response().success(roleService.selectAll(pageNum, pageSize));
	}
	
	/**
	 * 
	 */
	@ApiOperation(value = "新增角色 新增的时候填show_name")
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response insertSelective(@ApiParam(value = "角色", required = true) @RequestBody @Validated Role record) {
		Response response = new Response();
		int num = roleService.insertSelective(record);
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
	@ApiOperation(value = "修改角色(修改showName)")
	@RequestMapping(value = "/modify", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response updateSelective(@ApiParam(value = "角色", required = true) @RequestBody @Validated Role record) {
		Response response = new Response();
		int num = roleService.updateByPrimaryKeySelective(record);
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
	@ApiOperation(value = "删除角色(管理员,质检员，作者不能删除！！！！！)")
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response delete(@ApiParam(value = "角色id", required = true) @PathVariable Long id) {
		Response response = new Response();
		int num = roleService.deleteByPrimaryKey(id);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}
	/**
	 * 判断角色名是否存在
	 */
	@ApiOperation(value = "判断角色名是否存在")
	@RequestMapping(value = "/getUserByShowName", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response getUserByRoleName(@ApiParam(value = "展示角色名", required = true) @RequestParam(name="showName",required=true)String showName) {
		Response response = new Response();
		Role role  = roleService.getUserByShowName(showName);
		if (role!=null) {
			response.success("存在");
		} else {
			response.failure("不存在");
		}
		return response;
	}
	/**
	 * 设置角色权限
	 * @return
	 */
	@ApiOperation(value = "设置角色权限")
	@RequestMapping(value = "/updateRoleAllows", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    public Response updateRoleAllows(@ApiParam(value = "角色id", required = true) @RequestParam(name="roleId",required=true)Long roleId,@ApiParam(value = "权限id用,分开(1,2,3)", required = true) @RequestParam(name="allows",required=true)String allows){
		Response response = new Response();
		String [] allowId = allows.split(",");
		List<Long> ids =new ArrayList<>();
		if(allowId.length!=0){
			 for (String id : allowId) {
				ids.add(Long.parseLong(id));
			}
		}
		roleAllowService.deleteByRoleId(roleId);
		RoleAllow roleAllow = new RoleAllow();
		roleAllow.setRoleId(roleId);
		for (Long long1 : ids) {
			roleAllow.setAllowId(long1);
		    int num =roleAllowService.insertSelective(roleAllow);
		    if(num<0){
		    	return response.failure();
		    }
		}
		return response.success();
		
    }
	
	
	
	
	

}
