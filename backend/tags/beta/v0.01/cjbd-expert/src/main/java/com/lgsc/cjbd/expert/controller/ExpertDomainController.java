package com.lgsc.cjbd.expert.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lgsc.cjbd.expert.model.ExpertDomain;
import com.lgsc.cjbd.expert.service.ExpertDomainService;
import com.lgsc.cjbd.vo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 专家分类关系
 * 
 * @author Pengmeiyan
 *
 */
@Api(tags = "expertdomain", description = "专家分类关系")
@RestController
@RequestMapping("/expert/expertdomain")
public class ExpertDomainController {

	@Autowired
	private ExpertDomainService expertDomainService;

	/**
	 * 新增专家分类关系
	 * 
	 * @param expertDomain
	 * @return
	 */
	@ApiOperation(value = "新增专家分类关系")
	@RequestMapping(value = "/insertExpertDomain", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response insertExpertDomain(
			@ApiParam(value = "专家分类关系", required = true) @RequestBody @Validated ExpertDomain expertDomain) {
		Response response = new Response();
		int num = expertDomainService.insertSelective(expertDomain);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 根据id删除专家分类关系
	 * 
	 * @param expertDomainId
	 * @return
	 */
	@ApiOperation(value = "根据id删除专家分类关系")
	@RequestMapping(value = "/deleteExpertDomain/{expertDomainId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response deleteExpertDomain(@ApiParam(value = "id", required = true) @PathVariable Long expertDomainId) {
		Response response = new Response();
		int num = expertDomainService.deleteByPrimaryKey(expertDomainId);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 修改专家分类关系
	 * 
	 * @param expertDomain
	 * @return
	 */
	@ApiOperation(value = "修改专家分类关系")
	@RequestMapping(value = "/updateExpertDomain", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response updateExpertDomain(
			@ApiParam(value = "修改的专家分类关系", required = true) @RequestBody @Validated ExpertDomain expertDomain) {
		Response response = new Response();
		int num = expertDomainService.updateByPrimaryKeySelective(expertDomain);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 根据id查询专家分类关系
	 * 
	 * @param expertDomainId
	 * @return
	 */
	@ApiOperation(value = "根据id查询专家分类关系")
	@RequestMapping(value = "/selectExpertDomain/{expertDomainId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectExpertDomain(@ApiParam(value = "id", required = true) @PathVariable Long expertDomainId) {
		return new Response().success(expertDomainService.selectByPrimaryKey(expertDomainId));
	}

	/**
	 * 分页查询所有专家分类关系
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@ApiOperation(value = "分页查询所有专家分类关系")
	@RequestMapping(value = "/selectAllExpertDomain", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectAllExpertDomain(
			@ApiParam(value = "当前页", required = true) @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@ApiParam(value = "每页的数量", required = true) @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
		return new Response().success(expertDomainService.selectAll(pageNum, pageSize));
	}
}
