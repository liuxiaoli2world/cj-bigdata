package com.lgsc.cjbd.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lgsc.cjbd.user.model.Bookmark;
import com.lgsc.cjbd.user.service.BookmarkService;
import com.lgsc.cjbd.vo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 书签
 * 
 * @author
 */
@Api(tags = "bookmark", description = "书签")
@RestController
@RequestMapping("/user/bookmark")
public class BookmarkController {

	@Autowired
	private BookmarkService bookmarkService;

	/**
	 * 查询一个书签
	 */
	@ApiOperation(value = "书签编号")
	@RequestMapping(value = "/query/{id}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response select(@ApiParam(value = "书签编号", required = true) @PathVariable Long id) {
		Bookmark bookmark = bookmarkService.selectByPrimaryKey(id);
		Response response = new Response();
		if (bookmark == null) {
			response.failure();
		} else {
			response.success();
		}
		return response;
	}

	/**
	 * 根据isbn和用户id查询书签
	 */
	@ApiOperation(value = "根据isbn和用户id查询书签")
	@RequestMapping(value = "/queryBookMark", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectBookMark(
			@ApiParam(value = "isbn", required = true) @RequestParam(value = "isbn", required = true) String isbn,
			@ApiParam(value = "用户id", required = true) @RequestParam(value = "userId", required = true) Long userId) {
		return new Response().success(bookmarkService.selectBookMark(isbn, userId));
	}

	/**
	 * 添加书签
	 */
	@ApiOperation(value = "添加书签")
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response insertSelective(@ApiParam(value = "书签", required = true) @RequestBody @Validated Bookmark bookmark) {
		Response response = new Response();
		int num = bookmarkService.insertSelective(bookmark);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 修改书签
	 */
	@ApiOperation(value = "修改书签")
	@RequestMapping(value = "/modify", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response updateSelective(@ApiParam(value = "书签", required = true) @RequestBody @Validated Bookmark record) {
		Response response = new Response();
		int num = bookmarkService.updateByPrimaryKeySelective(record);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 删除书签
	 */
	@ApiOperation(value = "删除书签")
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response delete(@ApiParam(value = "书签id", required = true) @PathVariable Long id) {
		Response response = new Response();
		int num = bookmarkService.deleteByPrimaryKey(id);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 根据章节id删除书签
	 */
	@ApiOperation(value = "根据图书目录id删除书签")
	@RequestMapping(value = "/removeBySectionId", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response deleteBookMarkByBookSectionId(
			@ApiParam(value = "章节id", required = true) @RequestParam("bookSectionId") Long bookSectionId) {
		Response response = new Response();
		int num = bookmarkService.deleteByBookSectionId(bookSectionId);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 根据isbn删除书签
	 */
	@ApiOperation(value = "根据isbn删除书签")
	@RequestMapping(value = "/removeByIsbn", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Integer deleteBookMarkByBookSectionId(
			@ApiParam(value = "isbn", required = true) @RequestParam("isbn") String isbn) {
		return bookmarkService.deleteByIsbn(isbn);
	}

}
