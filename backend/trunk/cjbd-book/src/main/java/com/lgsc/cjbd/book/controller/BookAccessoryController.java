package com.lgsc.cjbd.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lgsc.cjbd.book.model.BookAccessory;
import com.lgsc.cjbd.book.service.BookAccessoryService;
import com.lgsc.cjbd.vo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 图书附件
 * @author 
 */
@Api(tags = "bookaccessory", description = "图书附件")
@RestController
@RequestMapping("/book/bookaccessory")
public class BookAccessoryController {
	
	@Autowired
	private BookAccessoryService bookAccessoryService;
    
    /**
	 * 查询单个附件
	 */
	@ApiOperation(value = "查询单个附件")
	@RequestMapping(value = "/queryById/{bookAccessoryId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectById(@ApiParam(value = "图书附件编号", required = true) @PathVariable Long bookAccessoryId) {
		return new Response().success(bookAccessoryService.selectByPrimaryKey(bookAccessoryId));
	}
	
	/**
	 * 查询附件
	 */
	@ApiOperation(value = "根据isbn查询附件")
	@RequestMapping(value = "/queryByIsbn/{isbn}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectByIsbn(@ApiParam(value = "图书isbn", required = true) @PathVariable String isbn) {
		return new Response().success(bookAccessoryService.selectByIsbn(isbn));
	}
	
	/**
	 * 查询所有附件
	 */
	@ApiOperation(value = "查询所有附件")
	@RequestMapping(value = "/queryAll", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectAll(@ApiParam(value = "当前页", required = true) @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@ApiParam(value = "每页的数量", required = true) @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
		return new Response().success(bookAccessoryService.selectAll(pageNum, pageSize));
	}
	
	/**
	 * 添加附件
	 */
	@ApiOperation(value = "")
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response insertSelective(@ApiParam(value = "", required = true) @RequestBody @Validated BookAccessory record) {
		Response response = new Response();
		int num = bookAccessoryService.insertSelective(record);
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
	public Response updateSelective(@ApiParam(value = "", required = true) @RequestBody @Validated BookAccessory record) {
		Response response = new Response();
		int num = bookAccessoryService.updateByPrimaryKeySelective(record);
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
		int num = bookAccessoryService.deleteByPrimaryKey(id);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

}
