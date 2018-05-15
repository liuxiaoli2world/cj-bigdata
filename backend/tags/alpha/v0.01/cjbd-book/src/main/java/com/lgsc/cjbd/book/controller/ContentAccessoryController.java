package com.lgsc.cjbd.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lgsc.cjbd.book.model.ContentAccessory;
import com.lgsc.cjbd.book.service.ContentAccessoryService;
import com.lgsc.cjbd.vo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @author 
 */
@Api(tags = "contentaccessory", description = "内容附件")
@RestController
@RequestMapping("/book/contentaccessory")
public class ContentAccessoryController {
	
	@Autowired
	private ContentAccessoryService contentAccessoryService;
    
    /**
	 * 
	 */
	@ApiOperation(value = "查询附件详细")
	@RequestMapping(value = "/query/{id}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response select(@ApiParam(value = "附件的编号", required = true) @PathVariable Long id) {
		return new Response().success(contentAccessoryService.selectByPrimaryKey(id));
	}
	
	/**
	 * 
	 */
	@ApiOperation(value = "分页查询所有附件")
	@RequestMapping(value = "/queryAll", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectAll(@ApiParam(value = "当前页", required = true) @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@ApiParam(value = "每页的数量", required = true) @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
		return new Response().success(contentAccessoryService.selectAll(pageNum, pageSize));
	}
	
	/**
	 * 新增内容附件
	 */
	@ApiOperation(value = "新增内容附件")
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response insertSelective(@ApiParam(value = "咨询附件", required = true) @RequestBody @Validated ContentAccessory record) {
		Response response = new Response();
		int num = contentAccessoryService.insertSelective(record);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}
	
	/**
	 * 修改
	 */
	@ApiOperation(value = "修改内容附件")
	@RequestMapping(value = "/modify", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response updateSelective(@ApiParam(value = "附件", required = true) @RequestBody @Validated ContentAccessory record) {
		Response response = new Response();
		int num = contentAccessoryService.updateByPrimaryKeySelective(record);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}
	
	/**
	 * 删除内容附件
	 */
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response delete(@ApiParam(value = "附件编号", required = true) @PathVariable Long id) {
		Response response = new Response();
		int num = contentAccessoryService.deleteByPrimaryKey(id);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

}
