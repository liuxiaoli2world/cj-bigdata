package com.lgsc.cjbd.book.controller;

import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.lgsc.cjbd.book.dto.BookDTO;
import com.lgsc.cjbd.book.model.Book;
import com.lgsc.cjbd.book.service.BookAccessoryService;
import com.lgsc.cjbd.book.service.BookChannelService;
import com.lgsc.cjbd.book.service.BookDirService;
import com.lgsc.cjbd.book.service.BookImageService;
import com.lgsc.cjbd.book.service.BookSectionService;
import com.lgsc.cjbd.book.service.BookService;
import com.lgsc.cjbd.vo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 图书
 * 
 * @author
 */
@Api(tags = "book", description = "图书")
@RestController
@RequestMapping("/book/book")
public class BookController {

	@Autowired
	private BookService bookService;

	@Autowired
	private BookSectionService bookSectionService;

	@Autowired
	private BookDirService bookDirService;

	@Autowired
	private BookAccessoryService bookAccessoryService;

	@Autowired
	private BookImageService bookImageService;

	@Autowired
	private BookChannelService bookChannelService;

	/**
	 * 查询一本图书
	 */
	@ApiOperation(value = "查询一本图书")
	@RequestMapping(value = "/query/{bookId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response select(@ApiParam(value = "图书编号", required = true) @PathVariable Long bookId) {
		return new Response().success(bookService.selectById(bookId));
	}
	
	/**
	 * 根据isbn号查询一本图书
	 */
	@ApiOperation(value = " 根据isbn号查询一本图书")
	@RequestMapping(value = "/queryByIsbn/{isbn}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response select(@ApiParam(value = "图书isbn号", required = true) @PathVariable String isbn) {
		return new Response().success(bookService.selectByIsbn(isbn));
	}

	/**
	 * 查询实时书籍数量
	 */
	@ApiOperation(value = "查询实时书籍数量")
	@RequestMapping(value = "/queryBookSum", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectBookSum() {
		return new Response().success(bookService.selectBookSum());
	}

	/**
	 * 查询推荐阅读
	 */
	@ApiOperation(value = "查询推荐阅读")
	@RequestMapping(value = "/queryRec", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectRec() {
		return new Response().success(bookService.selectIsRec());
	}

	/**
	 * 按条件查询图书
	 */
	@ApiOperation(value = "按条件查询图书")
	@RequestMapping(value = "/queryBy", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectBy(
			@ApiParam(value = "当前页", required = true) @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@ApiParam(value = "每页的数量", required = true) @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
			@ApiParam(value = "作者姓名", required = false) @RequestParam(name = "realname", required = false) String realname,
			@ApiParam(value = "图书分类编号", required = false) @RequestParam(name = "channelId", required = false) Long channelId,
			@ApiParam(value = "父级菜单编号", required = false) @RequestParam(name = "parentMenuId", required = false) Long parentMenuId,
			@ApiParam(value = "子级菜单编号", required = false) @RequestParam(name = "childMenuId", required = false) Long childMenuId,
			@ApiParam(value = "关键词", required = false) @RequestParam(name = "keyword", required = false) String keyword,
			@ApiParam(value = "是否发布 0=未发布 1=已发布", required = false) @RequestParam(name = "isRelease", required = false) String isRelease) {
		PageInfo<Map<String, Object>> pageInfo = bookService.selectBy(pageNum, pageSize, realname, channelId, parentMenuId,
				childMenuId, keyword, isRelease);
		if (pageInfo == null) {
			return new Response().failure();
		}
		return new Response().success(pageInfo);
	}

	/**
	 * 添加图书
	 */
	@ApiOperation(value = "添加图书")
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response insertBook(@ApiParam(value = "图书", required = true) @RequestBody @Validated BookDTO bookDTO) {
		Response response = new Response();
		Book book = new Book();
		BeanUtils.copyProperties(bookDTO, book, "channelId", "menuId", "imageUrl");
		int num = bookService.insertBook(book, bookDTO.getChannelId(), bookDTO.getMenuId(), bookDTO.getImageUrl());
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 修改图书
	 */
	@ApiOperation(value = "修改图书")
	@RequestMapping(value = "/modify", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response updateSelective(@ApiParam(value = "图书", required = true) @RequestBody @Validated BookDTO bookDTO) {
		Response response = new Response();
		Book book = new Book();
		BeanUtils.copyProperties(bookDTO, book, "channelId", "menuId", "imageUrl");
		int num = bookService.updateBook(book, bookDTO.getChannelId(), bookDTO.getMenuId(), bookDTO.getImageUrl());
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 删除图书
	 */
	@ApiOperation(value = "删除图书")
	@RequestMapping(value = "/remove/{isbn}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response delete(@ApiParam(value = "图书编号", required = true) @PathVariable String isbn) {
		Response response = new Response();
		bookSectionService.deleteByIsbn(isbn); // 删除章节和书签
		bookDirService.deleteByIsbn(isbn); // 删除目录和用户图书商品关系
		bookAccessoryService.deleteByIsbn(isbn);// 删除附件
		bookImageService.deleteByIsbn(isbn);// 删除图片
		bookChannelService.deleteByIsbn(isbn);// 删除图书分类关系
		int num = bookService.deleteByIsbn(isbn);// 删除菜单资源和图书
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}
	
	/**
	 * 根据isbn查询图书是否下架
	 * @param isbn
	 * @return
	 */
	@ApiOperation(value = "根据isbn查询图书是否下架")
	@RequestMapping(value = "/isRelease/{isbn}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response isRelease(@ApiParam(value = "图书编号", required = true) @PathVariable String isbn) {
		Book book = bookService.selectByIsbn(isbn);
		if (book != null) {
			if (book.getIsRelease() == 1) 
				return new Response().success("success");
			else
				return new Response().success("failure");
		}
		return new Response().failure("该图书不存在");
	}
	
	/**
	 * 根图书id查询图书是否下架
	 * @param bookId
	 * @return
	 */
	@ApiOperation(value = "根图书id查询图书是否下架")
	@RequestMapping(value = "/isReleaseById/{bookId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response isReleaseById(@ApiParam(value = "图书id", required = true) @PathVariable Long bookId) {
		Book book = bookService.selectByBookId(bookId);
		if (book != null) {
			if (book.getIsRelease() == 1) 
				return new Response().success("success");
			else
				return new Response().success("failure");
		}
		return new Response().failure("该图书不存在");
	}
	
	/**
	 * 根图书id更新图书发布状态
	 * @param bookId
	 * @return
	 */
	@ApiOperation(value = "根图书id更新图书发布状态")
	@RequestMapping(value = "/updateRelease", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response updateRelease(
			@ApiParam(value = "图书id", required = true) @RequestParam(name = "bookId", required = true) Long bookId ,
			@ApiParam(value = "图书是否发布", required = true) @RequestParam(name = "isRelease", required = true) Short isRelease){
		Response response = new Response();
		int num = bookService.updateRelease(bookId, isRelease);
		if (num > 0 ) {
			if (isRelease == 0)
				response.success("下架成功!");
			else
				response.success("发布成功!");
		}else {
			if (isRelease == 0)
				response.success("下架失败!");
			else
				response.success("发布失败!");
		}
		return response;
	}
	
	/**
	 * 根据isbn查询图书是否存在
	 * @param isbn
	 * @return
	 */
	@ApiOperation(value="根据isbn查询图书是否存在, ture:存在 , false:不存在")
	@RequestMapping(value="/isExist/{isbn}",method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response bookIsExist(
			@ApiParam(value = "图书isbn", required = true) @PathVariable(name = "isbn", required = true) String isbn){
		return new Response().success(bookService.bookIsExist(isbn));
	}
}
