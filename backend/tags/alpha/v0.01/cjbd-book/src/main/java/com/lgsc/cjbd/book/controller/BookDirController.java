package com.lgsc.cjbd.book.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lgsc.cjbd.book.model.BookDir;
import com.lgsc.cjbd.book.service.BookDirService;
import com.lgsc.cjbd.book.service.BookSectionService;
import com.lgsc.cjbd.vo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 图书目录
 * 
 * @author
 */
@Api(tags = "bookdir", description = "图书目录")
@RestController
@RequestMapping("/book/bookdir")
public class BookDirController {

	@Autowired
	private BookDirService bookDirService;
	
	@Autowired
	private BookSectionService bookSectionService;

	/**
	 * 查询单个目录
	 */
	@ApiOperation(value = "查询单个图书目录")
	@RequestMapping(value = "/query/{bookDirId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response select(@ApiParam(value = "图书目录编号", required = true) @PathVariable Long bookDirId) {
		return new Response().success(bookDirService.selectByPrimaryKey(bookDirId));
	}
	
	/**
	 * 查询单个目录For Client
	 */
	@ApiOperation(value = "查询单个目录For Client")
	@RequestMapping(value = "/queryDetail", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public String selectBookDirDetail(@RequestParam("bookDirId") Long bookDirId) {
		return bookDirService.selectBookDirDetail(bookDirId);
	}

	/**
	 * 查询用户购买的图书根目录
	 * 
	 * @param isbn
	 * @param userId
	 * @return
	 */
	@ApiOperation(value = "根据用户id与图书isbn号查询图书根目录")
	@RequestMapping(value = "/queryRoot", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectRoot(
		@ApiParam(value = "图书isbn", required = true) @RequestParam(value = "isbn", required = true) String isbn,
		@ApiParam(value = "用户id", required = true) @RequestParam(value = "userId", required = true) Long userId) {
		return new Response().success(bookDirService.selectRoot(isbn,userId));
	}
	
	/**
	 * 查询用户购买的图书根目录
	 * 
	 * @param isbn
	 * @return
	 */
	@ApiOperation(value = "根据isbn号查询图书根目录")
	@RequestMapping(value = "/queryRootByIsbn/{isbn}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response queryRootByIsbn(
		@ApiParam(value = "图书isbn", required = true)  @PathVariable(value = "isbn", required = true) String isbn) {
		return new Response().success(bookDirService.selectRootByIsbn(isbn));
	}
	
	
	/**
	 * 根据isbn查询图书所有目录
	 */
	@ApiOperation(value = "根据isbn查询图书所有目录")
	@RequestMapping(value = "/queryAllByIsbn/{isbn}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectAllByIsbn(@ApiParam(value = "图书isbn", required = true) @PathVariable String isbn) {
		return new Response().success(bookDirService.selectAllByIsbn(isbn));
	}

	/**
	 * 根据图书目录id查询祖先节点
	 */
	@ApiOperation(value = "根据图书目录id查询祖先节点")
	@RequestMapping(value = "/queryParentList/{bookDirId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectParentList(@ApiParam(value = "菜单编号", required = true) @PathVariable Long bookDirId) {
		return new Response().success(bookDirService.selectParentList(bookDirId));
	}

	/**
	 * 根据图书目录id查询后代节点
	 */
	@ApiOperation(value = "根据图书目录id查询后代节点")
	@RequestMapping(value = "/queryChildList/{bookDirId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectChildList(@ApiParam(value = "菜单编号", required = true) @PathVariable Long bookDirId) {
		return new Response().success(bookDirService.selectChildList(bookDirId));
	}

	/**
	 * 根据图书目录id查询子目录
	 */
	@ApiOperation(value = "根据图书目录id查询子目录")
	@RequestMapping(value = "/queryChild/{bookDirId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectChild(@ApiParam(value = "菜单编号", required = true) @PathVariable Long bookDirId) {
		return new Response().success(bookDirService.selectChild(bookDirId));
	}

	/**
	 * 查询所有图书目录
	 */
	@ApiOperation(value = "查询所有图书目录")
	@RequestMapping(value = "/queryAll", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectAll() {
		return new Response().success(bookDirService.selectAll());
	}

	/**
	 * 新增图书目录
	 * @throws Exception 
	 */
	@ApiOperation(value = "新增图书目录")
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response insertSelective(
			@ApiParam(value = "图书目录", required = true) @RequestBody @Validated BookDir bookDir) throws Exception {
		Response response = new Response();
		int num = bookDirService.insertSelective(bookDir);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 修改图书目录
	 */
	@ApiOperation(value = "修改图书目录")
	@RequestMapping(value = "/modify", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response updateSelective(
			@ApiParam(value = "图书目录", required = true) @RequestBody @Validated BookDir bookDir) {
		Response response = new Response();
		if (bookDir.getBookDirId() != null) {
			int num = bookDirService.updateByPrimaryKeySelective(bookDir);
			if (num > 0) {
				response.success();
			} else {
				response.failure();
			}
		} else {
			response.failure("图书目录编号不能为空！");
		}
		return response;
	}

	/**
	 * 删除图书目录
	 */
	@ApiOperation(value = "删除图书目录")
	@RequestMapping(value = "/remove/{bookDirId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response delete(@ApiParam(value = "图书目录编号", required = true) @PathVariable Long bookDirId) {
		Response response = new Response();
		int num = 0;
		List<BookDir> childList = bookDirService.selectChildList(bookDirId); // 所有后代节点
		for (BookDir bookDir : childList) {
			bookSectionService.deleteByBookDirId(bookDir.getBookDirId()); // 删除章节和书签
			bookDirService.deleteByPrimaryKey(bookDir.getBookDirId()); // 删除目录
			num ++;
		}
		bookSectionService.deleteByBookDirId(bookDirId); // 删除当前章节和书签
		num = num + bookDirService.deleteByPrimaryKey(bookDirId); // 删除当前目录
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 删除图书目录
	 */
	@ApiOperation(value = "删除图书目录")
	@RequestMapping(value = "/removeByIsbn/{isbn}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response deleteByIsbn(@ApiParam(value = "图书isbn", required = true) @PathVariable String isbn) {
		Response response = new Response();
		bookSectionService.deleteByIsbn(isbn); // 删除章节和书签
		int num = bookDirService.deleteByIsbn(isbn); // 删除目录
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

}
