package com.lgsc.cjbd.expert.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lgsc.cjbd.expert.model.Domain;
import com.lgsc.cjbd.expert.service.DomainService;
import com.lgsc.cjbd.vo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 专家分类Controller
 * 
 * @author Pengmeiyan
 *
 */
@Api(tags = "domain", description = "专家分类")
@RestController
@RequestMapping("/expert/domain")
public class DomainController {

	@Autowired
	private DomainService domainService;

	/**
	 * 新增专家分类
	 * 
	 * @param domain
	 * @return
	 */
	@ApiOperation(value = "新增专家分类")
	@RequestMapping(value = "/insertDomain", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response insertDomain(@ApiParam(value = "", required = true) @RequestBody @Validated Domain domain) {
		Response response = new Response();
		int num = domainService.insertSelective(domain);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 根据专家分类编号删除专家分类
	 * 
	 * @param domainId
	 * @return
	 */
	@ApiOperation(value = "根据专家分类编号删除专家分类")
	@RequestMapping(value = "/deleteDomain/{domainId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response deleteDomain(@ApiParam(value = "专家分类编号", required = true) @PathVariable Long domainId) {
		
		return new Response().success(domainService.deleteByPrimaryKey(domainId));
	}

	/**
	 * 修改专家分类
	 * 
	 * @param domain
	 * @return
	 */
	@ApiOperation(value = "修改专家分类")
	@RequestMapping(value = "/updateDomain", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response updateDomain(@ApiParam(value = "", required = true) @RequestBody @Validated Domain domain) {
		Response response = new Response();
		int num = domainService.updateByPrimaryKeySelective(domain);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 根据专家分类id查询专家分类
	 * 
	 * @param domainId
	 * @return
	 */
	@ApiOperation(value = "根据专家分类编号查询专家分类")
	@RequestMapping(value = "/selectDomainByDomainId/{domainId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectDomainByDomainId(@ApiParam(value = "专家分类编号", required = true) @PathVariable Long domainId) {
		return new Response().success(domainService.selectByPrimaryKey(domainId));
	}

	/**
	 * 根据分类名称分页查询所有专家分类
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@ApiOperation(value = "根据分类名称分页查询所有专家分类")
	@RequestMapping(value = "/selectAllDomain", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectAllDomain(
			@ApiParam(value = "当前页", required = true) @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@ApiParam(value = "每页的数量", required = true) @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
			@ApiParam(value = "专家分类名称", required = false) @RequestParam(name = "name", required = false) String name) {
		return new Response().success(domainService.selectAll(pageNum, pageSize,name));
	}


}
