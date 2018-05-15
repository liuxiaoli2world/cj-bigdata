package com.lgsc.cjbd.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lgsc.cjbd.book.model.BookChannel;
import com.lgsc.cjbd.book.service.BookChannelService;
import com.lgsc.cjbd.book.service.BookService;
import com.lgsc.cjbd.vo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 图书分类关系
 * 
 * @author
 */
@Api(tags = "bookchannel", description = "图书分类关系")
@RestController
@RequestMapping("/book/bookchannel")
public class BookChannelController {

	@Autowired
	private BookChannelService bookChannelService;
	@Autowired
	private BookService bookService;

	/**
	 * 根据关系id查询
	 */
	@ApiOperation(value = "根据分类id查询")
	@RequestMapping(value = "/queryById/{bookChannelId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectById(
			@ApiParam(value = "图书分类关系编号", required = true) @PathVariable Long bookChannelId) {
		return new Response().success(bookChannelService.selectByPrimaryKey(bookChannelId));
	}

	/**
	 * 根据图书isbn查询
	 */
	@ApiOperation(value = "根据图书isbn查询")
	@RequestMapping(value = "/queryByIsbn/{isbn}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectByIsbn(@ApiParam(value = "图书isbn", required = true) @PathVariable String isbn) {
		return new Response().success(bookChannelService.selectByIsbn(isbn));
	}

	/**
	 * 查询所有图书分类关系
	 */
	@ApiOperation(value = "查询所有")
	@RequestMapping(value = "/queryAll", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectAll() {
		return new Response().success(bookChannelService.selectAll());
	}

	/**
	 * 添加图书分类关系
	 */
	@ApiOperation(value = "添加图书分类关系")
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response insertSelective(
			@ApiParam(value = "分类", required = true) @RequestBody @Validated BookChannel bookChannel) {
		Response response = new Response();
		if (!bookService.checkIsbn(bookChannel.getIsbn())) {
			return response.failure();
		}
		int num = bookChannelService.insertSelective(bookChannel);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 修改图书分类关系
	 */
	@ApiOperation(value = "修改图书分类关系")
	@RequestMapping(value = "/modify", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response updateSelective(
			@ApiParam(value = "图书分类关系", required = true) @RequestBody @Validated BookChannel bookChannel) {
		Response response = new Response();
		if (!bookService.checkIsbn(bookChannel.getIsbn())) {
			return response.failure();
		}
		int num = bookChannelService.updateByPrimaryKeySelective(bookChannel);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 删除图书分类关系
	 */
	@ApiOperation(value = "删除图书分类关系")
	@RequestMapping(value = "/remove/{bookChannelId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response delete(@ApiParam(value = "图书分类关系", required = true) @PathVariable Long bookChannelId) {
		Response response = new Response();
		int num = bookChannelService.deleteByPrimaryKey(bookChannelId);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

}
