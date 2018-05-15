package com.lgsc.cjbd.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lgsc.cjbd.order.model.HisBook;
import com.lgsc.cjbd.order.service.HisBookService;
import com.lgsc.cjbd.vo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 图书商品历史信息
 * @author 
 */
@ApiIgnore
@Api(tags = "hisbook", description = "图书商品历史信息")
@RestController
@RequestMapping("/order/hisbook")
public class HisBookController {
	
	@Autowired
	private HisBookService hisBookService;
    
    /**
	 * 根据id查询图书商品历史信息
	 */
	@ApiOperation(value = "根据id查询")
	@RequestMapping(value = "/query/{id}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response select(@ApiParam(value = "图书商品历史信息id", required = true) @PathVariable Long id) {
		return new Response().success(hisBookService.selectByPrimaryKey(id));
	}
	
	/**
	 * 查询所有图书商品历史信息
	 */
	@ApiOperation(value = "查询所有图书商品历史信息")
	@RequestMapping(value = "/queryAll", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectAll(@ApiParam(value = "当前页", required = true) @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@ApiParam(value = "每页的数量", required = true) @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
		return new Response().success(hisBookService.selectAll(pageNum, pageSize));
	}
	
	/**
	 * 添加图书商品历史信息
	 */
	@ApiOperation(value = "添加图书商品历史信息")
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response insertSelective(@ApiParam(value = "图书商品历史信息", required = true) @RequestBody @Validated HisBook record) {
		Response response = new Response();
		int num = hisBookService.insertSelective(record);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}
	
	/**
	 * 修改图书商品历史信息
	 */
	@ApiOperation(value = "修改图书商品历史信息")
	@RequestMapping(value = "/modify", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response updateSelective(@ApiParam(value = "图书商品历史信息", required = true) @RequestBody @Validated HisBook record) {
		Response response = new Response();
		int num = hisBookService.updateByPrimaryKeySelective(record);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}
	
	/**
	 * 根据id删除图书商品历史信息
	 */
	@ApiOperation(value = "根据id删除图书商品历史信息")
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response delete(@ApiParam(value = "图书商品历史信息id", required = true) @PathVariable Long id) {
		Response response = new Response();
		int num = hisBookService.deleteByPrimaryKey(id);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

}
