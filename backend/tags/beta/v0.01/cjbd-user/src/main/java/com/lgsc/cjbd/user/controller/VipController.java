package com.lgsc.cjbd.user.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lgsc.cjbd.user.model.Vip;
import com.lgsc.cjbd.user.service.VipService;
import com.lgsc.cjbd.vo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 会员商品控制层
 * @author 
 */
@Api(tags = "vip", description = "会员商品")
@RestController
@RequestMapping("/user/vip")
public class VipController {
	
	@Autowired
	private VipService vipService;
    
    /**
	 * 查询会员商品详细
	 */
	@ApiOperation(value = "查询会员商品详细")
	@RequestMapping(value = "/query/{id}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response select(@ApiParam(value = "会员商品编号", required = true) @PathVariable Long id) {
		return new Response().success(vipService.selectByPrimaryKey(id));
	}
	
	/**
	 * 查询会员商品详细 For Client
	 */
	@RequestMapping(value = "/queryDetail", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Map<String , Object> selectDetail(@RequestParam("vipId") Long vipId) {
		return vipService.selectDetail(vipId);
	}
	
	/**
	 * 分页查询所有会员商品
	 */
	@ApiOperation(value = "分页查询所有会员商品")
	@RequestMapping(value = "/queryAll", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectAll(@ApiParam(value = "当前页", required = true) @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@ApiParam(value = "每页的数量", required = true) @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
		return new Response().success(vipService.selectAll(pageNum, pageSize));
	}
	
	/**
	 * 新增会员商品
	 */
	@ApiOperation(value = "新增会员商品")
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response insertSelective(@ApiParam(value = "会员商品", required = true) @RequestBody @Validated Vip record) {
		Response response = new Response();
		int num = vipService.insertSelective(record);
		if (num == 1) {
			response.success();
		} else if(num == 1062){
			response.failure("时长已存在");
		}else{
			response.failure();
		}
		return response;
	}
	
	/**
	 * 修改会员商品
	 */
	@ApiOperation(value = "修改会员商品")
	@RequestMapping(value = "/modify", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response updateSelective(@ApiParam(value = "会员商品", required = true) @RequestBody @Validated Vip record) {
		Response response = new Response();
		int num = vipService.updateByPrimaryKeySelective(record);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}
	
	/**
	 * 删除会员商品
	 */
	@ApiOperation(value = "删除会员商品")
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response delete(@ApiParam(value = "删除会员商品", required = true) @PathVariable Long id) {
		Response response = new Response();
		int num = vipService.deleteByPrimaryKey(id);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

}
