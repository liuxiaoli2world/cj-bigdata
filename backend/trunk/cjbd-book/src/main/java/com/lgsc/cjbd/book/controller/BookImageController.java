package com.lgsc.cjbd.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lgsc.cjbd.book.model.BookImage;
import com.lgsc.cjbd.book.service.BookImageService;
import com.lgsc.cjbd.book.service.BookService;
import com.lgsc.cjbd.vo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 图书图片
 * 
 * @author
 */
@Api(tags = "bookimage", description = "图书图片")
@RestController
@RequestMapping("/book/bookimage")
public class BookImageController {

	@Autowired
	private BookImageService bookImageService;

	@Autowired
	private BookService bookService;

	/**
	 * 查询一张图片
	 */
	@ApiOperation(value = "查询一张图片")
	@RequestMapping(value = "/queryById/{bookImageId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response select(@ApiParam(value = "图书图片编号", required = true) @PathVariable Long bookImageId) {
		return new Response().success(bookImageService.selectByPrimaryKey(bookImageId));
	}

	/**
	 * 查询图书图片
	 */
	@ApiOperation(value = "查询图书图片")
	@RequestMapping(value = "/queryByIsbn/{isbn}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response select(@ApiParam(value = "图书isbn", required = true) @PathVariable String isbn) {
		return new Response().success(bookImageService.selectByIsbn(isbn));
	}

	/**
	 * 查询所有图片
	 */
	@ApiOperation(value = "查询所有图片")
	@RequestMapping(value = "/queryAll", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectAll(
			@ApiParam(value = "当前页", required = true) @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@ApiParam(value = "每页的数量", required = true) @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
		return new Response().success(bookImageService.selectAll(pageNum, pageSize));
	}

	/**
	 * 添加图片
	 */
	@ApiOperation(value = "添加图片")
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response insertSelective(
			@ApiParam(value = "图书图片", required = true) @RequestBody @Validated BookImage bookImage) {
		Response response = new Response();
		if (!bookService.checkIsbn(bookImage.getIsbn())) {
			return response.failure();
		}
		int num = bookImageService.insertSelective(bookImage);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 修改图片
	 */
	@ApiOperation(value = "修改图片")
	@RequestMapping(value = "/modify", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response updateSelective(@ApiParam(value = "", required = true) @RequestBody @Validated BookImage bookImage) {
		Response response = new Response();
		if (!bookService.checkIsbn(bookImage.getIsbn())) {
			return response.failure();
		}
		int num = bookImageService.updateByPrimaryKeySelective(bookImage);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 删除图片
	 */
	@ApiOperation(value = "删除图片")
	@RequestMapping(value = "/removeById/{bookImageId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response delete(@ApiParam(value = "", required = true) @PathVariable Long bookImageId) {
		Response response = new Response();
		int num = bookImageService.deleteByPrimaryKey(bookImageId);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}
	
	/**
	 * 删除图片
	 */
	@ApiOperation(value = "删除图片")
	@RequestMapping(value = "/removeByIsbn/{isbn}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response delete(@ApiParam(value = "", required = true) @PathVariable String isbn) {
		Response response = new Response();
		int num = bookImageService.deleteByIsbn(isbn);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

}
