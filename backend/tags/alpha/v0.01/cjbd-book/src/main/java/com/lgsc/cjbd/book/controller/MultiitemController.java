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
import com.lgsc.cjbd.book.model.Multiitem;
import com.lgsc.cjbd.book.service.MultiitemService;
import com.lgsc.cjbd.vo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 多媒体项目
 * 
 * @author Pengmeiyan
 *
 */
@Api(tags = "multiitem", description = "多媒体项目")
@RestController
@RequestMapping("/book/multiitem")
public class MultiitemController {

	@Autowired
	private MultiitemService multiitemService;

	/**
	 * 根据id查询多媒体项目
	 */
	@ApiOperation(value = "根据id查询多媒体项目")
	@RequestMapping(value = "/query/{id}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response select(@ApiParam(value = "多媒体项目id", required = true) @PathVariable Long id) {
		return new Response().success(multiitemService.selectByPrimaryKey(id));
	}
	
	/**
	 * 根据多媒体id查询多媒体项目
	 */
	@ApiOperation(value = "根据多媒体id查询多媒体项目")
	@RequestMapping(value = "/queryItems/{id}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectItems(@ApiParam(value = "多媒体id", required = true) @PathVariable Long id) {
		return new Response().success(multiitemService.selectByMultifileId(id));
	}

	/**
	 * 查询所有多媒体项目
	 */
	@ApiOperation(value = "查询所有多媒体项目")
	@RequestMapping(value = "/queryAll", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectAll(
			@ApiParam(value = "当前页", required = true) @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@ApiParam(value = "每页的数量", required = true) @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
		return new Response().success(multiitemService.selectAll(pageNum, pageSize));
	}
	
	/**
	 * 查询图片、音频、视频数量
	 */
	@ApiOperation(value = "查询所有多媒体项目")
	@RequestMapping(value = "/querySum", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectSum() {
		return new Response().success(multiitemService.selectSum());
	}

	/**
	 * 查询视频看点列表
	 */
	@ApiOperation(value = "查询视频看点列表")
	@RequestMapping(value = "/queryIndexVideoList", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectIndexVideoList(@ApiParam(value = "multifileId", required = true) @RequestParam(name = "multifileId", required = true) Long multifileId) {
		List<Multiitem> list = multiitemService.selectIndexVideoList(multifileId);
		if (list == null) {
			return new Response().failure();
		}
		return new Response().success(list);
	}

	/**
	 * 新增多媒体项目
	 */
	@ApiOperation(value = "新增多媒体项目")
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response insertSelective(@ApiParam(value = "多媒体项目", required = true) @RequestBody @Validated Multiitem record) {
		Response response = new Response();
		int num = multiitemService.insertSelective(record);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 修改多媒体项目
	 */
	@ApiOperation(value = "修改多媒体项目")
	@RequestMapping(value = "/modify", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response updateSelective(@ApiParam(value = "多媒体项目", required = true) @RequestBody @Validated Multiitem record) {
		Response response = new Response();
		int num = multiitemService.updateByPrimaryKeySelective(record);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 根据id删除多媒体项目
	 */
	@ApiOperation(value = "根据id删除多媒体项目")
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response delete(@ApiParam(value = "多媒体项目id", required = true) @PathVariable Long id) {
		Response response = new Response();
		int num = multiitemService.deleteByPrimaryKey(id);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 根据相册id查询图片
	 * 
	 * @param multifileId
	 * @return
	 */
	@ApiOperation(value = "根据相册id查询图片")
	@RequestMapping(value = "/selectPicsById/{multifileId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectPicsById(@ApiParam(value = "multifileId", required = true) @PathVariable Long multifileId) {
		return new Response().success(multiitemService.selectPicsById(multifileId));
	}
	
	/**
	 * 修改多媒体项目排序
	 */
	@ApiOperation(value = "修改多媒体项目排序")
	@RequestMapping(value = "/modifyRank", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response updateRank(@ApiParam(value = "多媒体项目id及排序值", required = true) @RequestBody Multiitem[] mis) {
		Response response = new Response();
		int num = multiitemService.updateRank(mis);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 查询相册内某张图片的前一张和后一张
	 * 
	 * @param multifileId
	 * @param rank
	 * @return
	 */
	/*
	 * @ApiOperation(value = "查询相册内某张图片的前一张和后一张")
	 * 
	 * @RequestMapping(value = "/selectFirstAndLastPic", method =
	 * RequestMethod.GET, produces = "application/json; charset=UTF-8") public
	 * Response selectFirstAndLastPic(
	 * 
	 * @ApiParam(value = "相册id", required = true) @RequestParam(name =
	 * "multifileId", required = true) Long multifileId,
	 * 
	 * @ApiParam(value = "图片排序值", required = true) @RequestParam(name = "rank",
	 * required = true) Integer rank) { return new
	 * Response().success(multiitemService.selectFirstAndLastPic(multifileId,
	 * rank)); }
	 */
}
