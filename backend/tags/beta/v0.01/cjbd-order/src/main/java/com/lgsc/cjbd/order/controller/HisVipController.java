package com.lgsc.cjbd.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lgsc.cjbd.order.model.HisVip;
import com.lgsc.cjbd.order.service.HisVipService;
import com.lgsc.cjbd.vo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 会员商品历史信息
 * @author 
 */
@ApiIgnore
@Api(tags = "hisvip", description = "会员商品历史信息")
@RestController
@RequestMapping("/order/hisvip")
public class HisVipController {
	
	@Autowired
	private HisVipService hisVipService;
    
    /**
	 * 
	 */
	@ApiOperation(value = "")
	@RequestMapping(value = "/query/{id}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response select(@ApiParam(value = "", required = true) @PathVariable Long id) {
		return new Response().success(hisVipService.selectByPrimaryKey(id));
	}
	
	/**
	 * 
	 */
	@ApiOperation(value = "")
	@RequestMapping(value = "/queryAll", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectAll(@ApiParam(value = "当前页", required = true) @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@ApiParam(value = "每页的数量", required = true) @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
		return new Response().success(hisVipService.selectAll(pageNum, pageSize));
	}
	
	/**
	 * 
	 */
	@ApiOperation(value = "")
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response insertSelective(@ApiParam(value = "", required = true) @RequestBody @Validated HisVip record) {
		Response response = new Response();
		int num = hisVipService.insertSelective(record);
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
	@ApiOperation(value = "")
	@RequestMapping(value = "/modify", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response updateSelective(@ApiParam(value = "", required = true) @RequestBody @Validated HisVip record) {
		Response response = new Response();
		int num = hisVipService.updateByPrimaryKeySelective(record);
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
	@ApiOperation(value = "")
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response delete(@ApiParam(value = "", required = true) @PathVariable Long id) {
		Response response = new Response();
		int num = hisVipService.deleteByPrimaryKey(id);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

}
