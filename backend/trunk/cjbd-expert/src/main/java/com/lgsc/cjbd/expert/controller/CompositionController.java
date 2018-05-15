package com.lgsc.cjbd.expert.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.lgsc.cjbd.expert.service.CompositionService;
import com.lgsc.cjbd.vo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 著作Controller：专家的著作是网站上该专家的期刊
 * 
 * @author Pengmeiyan
 *
 */
@Api(tags = "composition", description = "著作")
@RestController
@RequestMapping("/expert/composition")
public class CompositionController {

	@Autowired
	private CompositionService compositionService;

	/**
	 * 新增/修改专家期刊
	 * 
	 * @param composition
	 * @return
	 */
	@ApiOperation(value = "新增期刊")
	@RequestMapping(value = "/insertComposition", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response insertComposition(
			@ApiParam(value = "专家id", required = true) @RequestParam("expertId") Long expertId,
			@ApiParam(value = "contentIds", required = false) @RequestBody(required = false) String ids) {
		List<Long> contentIds = new ArrayList<>();
		JSONObject JO = (JSONObject) JSONObject.parse(ids);
		if (JO.get("ids") != null) {
			ids = JO.get("ids").toString();
			String[] idss = ids.split(",");
			for (int i = 0; i < idss.length; i++) {
				contentIds.add(Long.valueOf(idss[i]));
			}
		}
		Response response = new Response();
		int num = compositionService.insertComposition(expertId, contentIds);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 根据id删除著作
	 * 
	 * @param compositionId
	 * @return
	 */
	@ApiOperation(value = "根据id删除著作")
	@RequestMapping(value = "/deleteComposition/{compositionId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response deleteComposition(@ApiParam(value = "著作id", required = true) @PathVariable Long compositionId) {
		Response response = new Response();
		int num = compositionService.deleteByPrimaryKey(compositionId);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 根据著作id查询著作
	 * 
	 * @param compositionId
	 * @return
	 */
	@ApiOperation(value = "根据著作编号查询著作")
	@RequestMapping(value = "/selectComposition/{compositionId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectComposition(@ApiParam(value = "著作编号", required = true) @PathVariable Long compositionId) {
		return new Response().success(compositionService.selectByPrimaryKey(compositionId));
	}

	/**
	 * （编辑）根据专家id查询期刊
	 * 
	 * @param expertId
	 * @return
	 */
	@ApiOperation(value = "根据专家id查询期刊")
	@RequestMapping(value = "/selectPeriodicalByExpertId/{expertId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectPeriodicalByExpertId(@ApiParam(value = "专家id", required = true) @PathVariable Long expertId) {
		return new Response().success(compositionService.selectPeriodicalByExpertId(expertId));
	}

	/**
	 * 根据专家id查询该专家展示著作
	 * 
	 * @param expertId
	 * @return
	 */
	@ApiOperation(value = "根据专家id查询该专家展示著作")
	@RequestMapping(value = "/selectCompositionByExpertId/{expertId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectCompositionByExpertId(
			@ApiParam(value = "专家编号", required = true) @PathVariable Long expertId) {
		return new Response().success(compositionService.selectByExpertId(expertId));
	}

	/**
	 * 分页查询所有著作
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@ApiOperation(value = "分页查询所有著作")
	@RequestMapping(value = "/selectAllComposition", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectAllComposition(
			@ApiParam(value = "当前页", required = true) @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@ApiParam(value = "每页的数量", required = true) @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
		return new Response().success(compositionService.selectAll(pageNum, pageSize));
	}

}
