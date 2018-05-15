package com.lgsc.cjbd.book.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.lgsc.cjbd.book.model.BookSection;
import com.lgsc.cjbd.book.service.BookSectionService;
import com.lgsc.cjbd.book.util.SubHTML;
import com.lgsc.cjbd.vo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 图书章节
 * 
 * @author
 */
@Api(tags = "booksection", description = "图书章节")
@RestController
@RequestMapping("/book/booksection")
public class BookSectionController {

	@Autowired
	private BookSectionService bookSectionService;

	/**
	 * 根据图书isbn和bookDirId查询单一章节
	 * 
	 * @param isbn
	 * @param bookDirId
	 * @return
	 */
	@ApiOperation(value = "根据图书isbn和bookDirId查询单一章节")
	@RequestMapping(value = "/queryByBookDirId", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectByBookDirId(
		    @ApiParam(value = "图书章节中的isbn号", required = true)  @RequestParam(name = "isbn", required = true) String isbn ,
			@ApiParam(value = "图书章节中的bookDirId", required = true) @RequestParam(name = "bookDirId", required = true) Long bookDirId,
			@ApiParam(value = "图书章节中的hasLeaf", required = true) @RequestParam(name = "hasLeaf", required = true) Short hasLeaf,
			@ApiParam(value = "用户id", required = false)  @RequestParam(name = "userId", required = false) Long userId)  {
		return new Response().success(bookSectionService.selectByBookDirId(isbn,bookDirId,hasLeaf,userId));
	}

	/**
	 * 查询单章试读内容
	 */
	//@ApiOperation(value = "查询本章试读")
	//@RequestMapping(value = "/queryTry/{bookSectionId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	//public Response selectTry(@ApiParam(value = "图书章节编号", required = true) @PathVariable Long bookSectionId) {
		//return new Response().success(bookSectionService.selectTry(bookSectionId));
	//}
	
	/**
	 * 截取试读内容
	 */
	@ApiOperation(value = "截取试读内容")
	@RequestMapping(value = "/subTry", method = RequestMethod.POST, produces = "application/json; charset=UTF-8", consumes = "application/json")
	public Response subTry(@ApiParam(value = "内容", required = true) @RequestBody Map<String, Object> map) {
		Response response = new Response();
		String subHTML = "";
		Object contentObject = map.get("content");
		Object numObject = map.get("num");
		if (contentObject != null && numObject != null) {
			String content = contentObject.toString();
			Integer num = Integer.parseInt(numObject.toString());
			subHTML = SubHTML.getSubHTML(content, num);
		}
		if ("".equals(subHTML)) {
			response.failure();
		}
		return response.success(subHTML);
	}
	
	/**
	 * 
	 * @param bookSectionId
	 * @param tryLength
	 * @return
	 */
	@ApiOperation(value = "更新试读内容")
	@RequestMapping(value = "/updateTry", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response updateTryLength(
			@ApiParam(value = "图书章节编号",required = true) @RequestParam(value = "bookSectionId",required = true) Long bookSectionId,
			@ApiParam(value = "试读长度",required = true) @RequestParam(value = "tryLength",required = true) Integer tryLength){
		return new Response().success(bookSectionService.updateTry(bookSectionId, tryLength));	
	}
	
	
	/**
	 * 根据图书isbn分页查询章节
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param isbn
	 * @param userId
	 * @return
	 */
	@ApiOperation(value = "根据图书isbn分页查询章节")
	@RequestMapping(value = "/queryByIsbn", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectByIsbn(
			@ApiParam(value = "当前页", required = true) @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@ApiParam(value = "每页的数量", required = true) @RequestParam(name = "pageSize", defaultValue = "1") Integer pageSize,
			@ApiParam(value = "图书章节中的isbn号", required = true)  @RequestParam(name = "isbn", required = true) String isbn,
			@ApiParam(value = "用户id", required = false)  @RequestParam(name = "userId", required = false) Long userId)  {
		PageInfo<BookSection> bs = bookSectionService.selectByIsbn(pageNum,pageSize,isbn,userId);
		if (bs != null) {
			return new Response().success(bs);
		}
		return new Response().failure("查询失败");
	}

	/**
	 * 添加章节
	 */
	@ApiOperation(value = "添加章节")
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response insertSelective(
			@ApiParam(value = "章节", required = true) @RequestBody @Validated BookSection record) {
		Response response = new Response();
		int num = bookSectionService.insertSelective(record);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}


	/**
	 * 根据章节id删除章节
	 */
	@ApiOperation(value = "根据章节id删除章节")
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response delete(@ApiParam(value = "", required = true) @PathVariable Long id) {
		Response response = new Response();
		int num = bookSectionService.deleteByPrimaryKey(id);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 根据图书目录id删除章节
	 */
	@ApiOperation(value = "根据图书目录id删除章节")
	@RequestMapping(value = "/remove", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response deleteByBookDirId(
			@ApiParam(value = "图书目录id", required = true) @RequestParam("BookDirId") Long BookDirId) {
		Response response = new Response();
		int num = bookSectionService.deleteByBookDirId(BookDirId);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}
	
	
	/**
	 * 根据bookDirId修改章节
	 * 
	 * @param bookDirId
	 * @return
	 */
	@ApiOperation(value = "根据bookDirId查询要修改的章节")
	@RequestMapping(value = "/modifyByBookDirId", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response modifyByBookDirId(
		@ApiParam(value = "图书目录id", required = true) @RequestParam("BookDirId") Long bookDirId) {
		BookSection bookSection = bookSectionService.modifyByBookDirId(bookDirId);
		if (bookSection != null) {
			return new Response().success(bookSection);
		}
		return new Response().failure();
	}
	
	/**
	 * 修改章节
	 */
	@ApiOperation(value = "修改章节")
	@RequestMapping(value = "/updateSelective", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response updateSelective(
			@ApiParam(value = "章节", required = true) @RequestBody @Validated BookSection record) {
		Response response = new Response();
		int num = bookSectionService.updateSection(record);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

}
