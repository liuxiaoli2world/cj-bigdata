package com.lgsc.cjbd.user.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lgsc.cjbd.user.model.Organization;
import com.lgsc.cjbd.user.service.OrganizationService;
import com.lgsc.cjbd.vo.Response;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 机构管理控制层
 * 
 * @author
 */
@Api(tags = "organization", description = "机构管理")
@RestController
@RequestMapping("/user/organization")
public class OrganizationController {

	@Autowired
	private OrganizationService organizationService;

	
	/**
	 * 查询机构
	 */
	@ApiOperation(value = "查询机构")
	@RequestMapping(value = "/query/{id}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response select(@ApiParam(value = "机构id", required = true) @PathVariable Long id) {
		return new Response().success(organizationService.selectByPrimaryKey(id));
	}

	/**
	 * 
	 */
	@ApiOperation(value = "分页查询所有机构")
	@RequestMapping(value = "/queryAll", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectAll(
			@ApiParam(value = "当前页", required = true) @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@ApiParam(value = "每页的数量", required = true) @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
			@ApiParam(value = "机构名称不传查询所有", required = false) @RequestParam(value = "name",required = false) String name) {
		return new Response().success(organizationService.selectAll(pageNum, pageSize, name));
	}

	/**
	 * 通过名称查询机构
	 */
	@ApiOperation(value = "通过名称查询结构")
	@RequestMapping(value = "/query", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectByName(@ApiParam(value = "机构名称", required = true) @RequestParam(value = "name") String name) {
		Response response = new Response();
		Organization organization = organizationService.selectByName(name);
		if (organization != null) {
			response.success(organization);
		}
		response.failure();
		return response;
	}

	/**
	 * 新增机构
	 */
	@ApiOperation(value = "新增一个机构")
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response insertSelective(
			@ApiParam(value = "机构", required = true) @RequestBody Organization organization) {
		Response response = new Response();
		int num = organizationService.insertSelective(organization);
		if (num > 0) {
			response.success(num);
		} else {
			response.failure("新增失败");
		}
		return response;
	}

	/**
	 * 修改机构(只修改机构名称)
	 */
	@ApiOperation(value = "修改机构(只修改机构名称)")
	@RequestMapping(value = "/modify", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response updateSelective(@ApiParam(value = "机构id", required = true) @RequestParam(value = "id") Long id,
			@ApiParam(value = "机构名称", required = true) @RequestParam(value = "name") String name) {
		Response response = new Response();
		Organization organization = new Organization();
		organization.setName(name);
		organization.setOrganizationId(id);
		int num = organizationService.updateByPrimaryKeySelective(organization);
		if (num > 0) {
			response.success("修改成功");
		} else {
			response.failure("修改失败");
		}
		return response;
	}

	/**
	 * 删除机构
	 */
	@ApiOperation(value = "删除机构")
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response delete(@ApiParam(value = "机构id", required = true) @PathVariable Long id) {
		Response response = new Response();
		int num = organizationService.deleteByPrimaryKey(id);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}
    /**
     * 批量新增账号
     * @param preName
     * @param num
     * @param id
     * @return
     */
	@ApiOperation(value = "批量新增账号")
	@RequestMapping(value = "/insertbatch", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response insertBatch(@ApiParam(value = "账号前缀", required = true) @RequestParam(value = "preName") String preName,
			@ApiParam(value = "账号数量", required = true) @RequestParam(value = "num") Integer num,@ApiParam(value = "机构id", required = true) @RequestParam(value = "id") Long id) {
		Response response = new Response();
		int insertSuccess = organizationService.insertBatch(preName, num, id);
		if (insertSuccess > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}
	/**
	 * 判断机构编号是否存在
	 * @param SeqNum
	 * @return
	 */
	@ApiOperation(value = "判断机构编号是否存在")
	@RequestMapping(value = "/selectBySqeNum", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectBySqeNum(@ApiParam(value = "机构编号", required = true) @RequestParam(value = "seqNum") Integer SeqNum){
		Response response = new Response();
		Organization record = organizationService.selectBySqeNum(SeqNum);
		if(record!=null){
			return response.success("存在该机构");
		}else{
			return response.failure("不存在该机构");
		}
	}

}
