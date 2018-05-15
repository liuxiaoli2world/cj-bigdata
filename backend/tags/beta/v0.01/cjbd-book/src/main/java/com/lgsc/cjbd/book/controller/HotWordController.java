package com.lgsc.cjbd.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lgsc.cjbd.book.model.HotWord;
import com.lgsc.cjbd.book.service.HotWordService;
import com.lgsc.cjbd.vo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 热词Controller
 * 
 * @author Pengmeiyan
 *
 */
@Api(tags = "hotword", description = "热词")
@RestController
@RequestMapping("/book/hotword")
public class HotWordController {

	@Autowired
	private HotWordService hotWordService;

	/**
	 * 新增热词
	 * 
	 * @param hotWord
	 * @return
	 */
	@ApiOperation(value = "新增热词")
	@RequestMapping(value = "/insertHotWord", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response insertHotWord(@ApiParam(value = "热词内容", required = true) @RequestBody @Validated HotWord hotWord) {
		Response response = new Response();
		int num = hotWordService.insertHotWord(hotWord);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 根据id删除热词
	 * 
	 * @param hotWordId
	 * @return
	 */
	@ApiOperation(value = "根据id删除热词")
	@RequestMapping(value = "/deleteHotWord/{hotWordId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response deleteHotWord(@ApiParam(value = "id", required = true) @PathVariable Long hotWordId) {
		Response response = new Response();
		int num = hotWordService.deleteHotWord(hotWordId);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 修改热词
	 * 
	 * @param hotWord
	 * @return
	 */
	@ApiOperation(value = "修改热词")
	@RequestMapping(value = "/updateHotWord", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response updateHotWord(
			@ApiParam(value = "热词修改内容", required = true) @RequestBody @Validated HotWord hotWord) {
		Response response = new Response();
		int num = hotWordService.updateHotWord(hotWord);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 根据id查询热词
	 * 
	 * @param hotWordId
	 * @return
	 */
	@ApiOperation(value = "根据id查询热词")
	@RequestMapping(value = "/selectByHotWordId/{hotWordId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectByHotWordId(@ApiParam(value = "", required = true) @PathVariable Long hotWordId) {
		return new Response().success(hotWordService.selectByHotWordId(hotWordId));
	}

	/**
	 * 查询所有热词
	 * 
	 * @return
	 */
	@ApiOperation(value = "查询所有热词")
	@RequestMapping(value = "/selectAllHotWord", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectAllHotWord() {
		return new Response().success(hotWordService.selectAllHotWord());
	}

	/**
	 * 分页查询所有热词
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@ApiOperation(value = "分页查询所有热词")
	@RequestMapping(value = "/selectAll", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectAll(
			@ApiParam(value = "当前页", required = true) @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@ApiParam(value = "每页的数量", required = true) @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
		return new Response().success(hotWordService.selectAll(pageNum, pageSize));
	}

	/**
	 * 随机返回8个热词
	 * 
	 * @return
	 */
	@ApiOperation(value = "随机返回8个热词")
	@RequestMapping(value = "/selectRandom", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectRandom() {
		return new Response().success(hotWordService.selectRandom());
	}

}
