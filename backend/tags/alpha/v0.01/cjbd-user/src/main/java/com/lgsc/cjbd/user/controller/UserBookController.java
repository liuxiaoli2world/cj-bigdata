package com.lgsc.cjbd.user.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lgsc.cjbd.user.model.UserBook;
import com.lgsc.cjbd.user.service.UserBookService;
import com.lgsc.cjbd.vo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 用户图书商品关系
 * 
 * @author
 */
@Api(tags = "userbook", description = "用户图书商品关系")
@RestController
@RequestMapping("/user/userbook")
public class UserBookController {

	@Autowired
	private UserBookService userBookService;

	/**
	 * 根据id查询
	 */
	@ApiOperation(value = "根据id查询")
	@RequestMapping(value = "/queryById/{id}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectById(@ApiParam(value = "用户图书商品关系编号", required = true) @PathVariable Long id) {
		return new Response().success(userBookService.selectByPrimaryKey(id));
	}

	/**
	 * 查询用户已购买的根目录
	 */
	@ApiOperation(value = "查询用户已购买的根目录")
	@RequestMapping(value = "/queryRootDirsOfUser", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectRootDirsOfUser(
			@ApiParam(value = "图书isbn", required = true) @RequestParam(value = "isbn", required = true) String isbn,
			@ApiParam(value = "用户id", required = true) @RequestParam(value = "userId", required = true) Long userId) {
		return new Response().success(userBookService.selectRootDirsOfUser(isbn, userId));
	}

	/**
	 * 根据用户id查询
	 */
	@ApiOperation(value = "根据用户id查询")
	@RequestMapping(value = "/queryByUserId/{UserId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectByUserId(@ApiParam(value = "用户编号", required = true) @PathVariable Long UserId) {
		Response response = new Response();
		List<UserBook> list = userBookService.selectByUserId(UserId);
		if (list == null) {
			return response.failure();
		}
		return response.success(list);
	}

	/**
	 * 查询所有关系
	 */
	@ApiOperation(value = "查询所有关系")
	@RequestMapping(value = "/queryAll", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectAll(
			@ApiParam(value = "当前页", required = true) @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@ApiParam(value = "每页的数量", required = true) @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
		return new Response().success(userBookService.selectAll(pageNum, pageSize));
	}

	/**
	 * 添加用户图书商品关系
	 */
//	@ApiOperation(value = "添加用户图书商品关系")
//	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
//	public Response insertSelective(
//			@ApiParam(value = "用户图书商品关系", required = true) @RequestBody @Validated UserBook record) {
//		Response response = new Response();
//		int num = userBookService.insertSelective(record);
//		if (num > 0) {
//			response.success();
//		} else {
//			response.failure();
//		}
//		return response;
//	}

	/**
	 * 添加用户图书商品关系
	 */
	@ApiIgnore
	@ApiOperation(value = "添加用户图书商品关系")
	@RequestMapping(value = "/addUserBook", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Boolean insertUserBook(@ApiParam(value = "用户id", required = true) @RequestParam Long userId,
			@ApiParam(value = "isbn", required = true) @RequestParam String isbn,
			@ApiParam(value = "图书目录id", required = true) @RequestParam Long bookDirId) {
		UserBook record = new UserBook();
		record.setUserId(userId);
		record.setIsbn(isbn);
		record.setBookDirId(bookDirId);
		int num = userBookService.insertSelective(record);
		return num > 0 ? true : false;
	}

	/**
	 * 批量添加用户图书商品关系
	 */
	@ApiIgnore
	@ApiOperation(value = "批量添加用户图书商品关系")
	@RequestMapping(value = "/batchAddUserBook", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Boolean batchInsertUserBook(
			@ApiParam(value = "UserBook集合", required = true) @RequestBody List<Map<String, Object>> list) {
		return userBookService.batchInsertSelective(list);
	}

	/**
	 * 修改用户图书商品关系
	 */
	@ApiOperation(value = "修改用户图书商品关系")
	@RequestMapping(value = "/modify", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response updateSelective(
			@ApiParam(value = "用户图书商品关系", required = true) @RequestBody @Validated UserBook record) {
		Response response = new Response();
		int num = userBookService.updateByPrimaryKeySelective(record);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 根据图书目录id删除关系
	 */
	@ApiOperation(value = "根据图书目录id删除关系")
	@RequestMapping(value = "/removeByBookDirId", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response deleteUserBookByBookDirId(
			@ApiParam(value = "图书目录编号", required = true) @RequestParam("bookDirId") Long bookDirId) {
		Response response = new Response();
		int num = userBookService.deleteByBookDirId(bookDirId);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 根据图书isbn删除关系
	 */
	@ApiOperation(value = "根据图书isbn删除关系")
	@RequestMapping(value = "/removeByIsbn", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Integer deleteUserBookByIsbn(
			@ApiParam(value = "图书isbn", required = true) @RequestParam("isbn") String isbn) {
		return userBookService.deleteByIsbn(isbn);
	}

	/**
	 * 根据关系id删除关系
	 */
	@ApiOperation(value = "根据关系id删除关系")
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response delete(@ApiParam(value = "用户图书商品关系编号", required = true) @PathVariable Long id) {
		Response response = new Response();
		int num = userBookService.deleteByPrimaryKey(id);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

}
