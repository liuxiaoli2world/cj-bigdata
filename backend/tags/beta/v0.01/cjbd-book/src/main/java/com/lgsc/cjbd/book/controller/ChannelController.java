package com.lgsc.cjbd.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lgsc.cjbd.book.model.Channel;
import com.lgsc.cjbd.book.service.ChannelService;
import com.lgsc.cjbd.vo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 图书分类
 * @author 
 */
@Api(tags = "channel", description = "图书分类")
@RestController
@RequestMapping("/book/channel")
public class ChannelController {
	
	@Autowired
	private ChannelService channelService;
    
    /**
	 * 查询单个分类
	 */
	@ApiOperation(value = "查询单个分类")
	@RequestMapping(value = "/query/{channelId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response select(@ApiParam(value = "分类编号", required = true) @PathVariable Long channelId) {
		return new Response().success(channelService.selectByPrimaryKey(channelId));
	}
	
	/**
	 * 查询所有分类
	 */
	@ApiOperation(value = "查询所有分类")
	@RequestMapping(value = "/queryAll", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectAll() {
		return new Response().success(channelService.selectAll());
	}
	
	/**
	 * 添加分类
	 */
	@ApiOperation(value = "添加分类")
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response insertSelective(@ApiParam(value = "分类", required = true) @RequestBody @Validated Channel channel) {
		Response response = new Response();
		int num = channelService.insertSelective(channel);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}
	
	/**
	 * 修改分类
	 */
	@ApiOperation(value = "修改分类")
	@RequestMapping(value = "/modify", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response updateSelective(@ApiParam(value = "分类", required = true) @RequestBody @Validated Channel channel) {
		Response response = new Response();
		int num = channelService.updateByPrimaryKeySelective(channel);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}
	
	/**
	 * 删除分类
	 */
	@ApiOperation(value = "删除分类")
	@RequestMapping(value = "/remove/{channelId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response delete(@ApiParam(value = "分类编号", required = true) @PathVariable Long channelId) {
		return new Response().success(channelService.deleteByPrimaryKey(channelId));
	}

}
