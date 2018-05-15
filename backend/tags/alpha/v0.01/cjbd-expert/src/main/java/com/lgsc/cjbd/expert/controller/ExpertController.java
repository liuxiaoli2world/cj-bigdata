package com.lgsc.cjbd.expert.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lgsc.cjbd.expert.model.Expert;
import com.lgsc.cjbd.expert.service.ExpertService;
import com.lgsc.cjbd.vo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 专家Controller
 * 
 * @author Pengmeiyan
 *
 */
@Api(tags = "expert", description = "专家")
@RestController
@RequestMapping("/expert/expert")
public class ExpertController {

	@Autowired
	private ExpertService expertService;

	/**
	 * 新增专家信息
	 * 
	 * @param expert
	 * @return
	 */
	@ApiOperation(value = "新增专家")
	@RequestMapping(value = "/insertExpert", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response insertExpert(@ApiParam(value = "专家信息", required = true) @RequestBody @Validated Expert expert) {
		Response response = new Response();
		Map<String, String> map = expertService.insertExpert(expert);
		if (!map.containsKey("error")) {
			response.success();
		} else {
			response.failure("新增失败，" + map.get("error"));
		}
		return response;
	}

	/**
	 * 根据id删除专家
	 * 
	 * @param expertId
	 * @return
	 */
	@ApiOperation(value = "根据id删除专家")
	@RequestMapping(value = "/deleteExpert/{expertId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response deleteExpert(@ApiParam(value = "id", required = true) @PathVariable Long expertId) {
		Response response = new Response();
		int num = expertService.deleteExpert(expertId);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 根据用户id删除专家
	 * 
	 * @param expertId
	 * @return
	 */
	@ApiOperation(value = "根据用户id删除专家")
	@RequestMapping(value = "/deleteExpertByUserId/{userId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response deleteExpertByUserId(@ApiParam(value = "id", required = true) @PathVariable Long userId) {
		Response response = new Response();
		int num = expertService.deleteExpertByUserId(userId);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 修改专家
	 * 
	 * @param expert
	 * @return
	 */
	@ApiOperation(value = "修改专家")
	@RequestMapping(value = "/updateExpert", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response updateExpert(@ApiParam(value = "修改的内容", required = true) @RequestBody @Validated Expert expert) {
		Response response = new Response();
		int num = expertService.updateExpert(expert);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 查询前20个专家姓名
	 * 
	 * @return
	 */
	@ApiOperation(value = "查询前20个专家姓名")
	@RequestMapping(value = "/selectAllExpertName", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectAllExpertName() {

		return new Response().success(expertService.selectAllExpertName());
	}

	/**
	 * 条件查询:根据分类，姓名分页查询专家信息
	 * 
	 * @param expertClassify
	 * @param realName
	 * @return
	 */
	@ApiOperation(value = "根据分类，姓名分页查询专家信息 ")
	@RequestMapping(value = "/selectByClassifyAndRealName", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response selectByClassifyAndRealName(
			@ApiParam(value = "当前页", required = true) @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@ApiParam(value = "每页的数量", required = true) @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
			@ApiParam(value = "分类名称", required = false) @RequestParam(name = "expertClassify", required = false) String expertClassify,
			@ApiParam(value = "专家姓名", required = false) @RequestParam(name = "realName", required = false) String realName) {
		return new Response()
				.success(expertService.selectByClassifyAndRealName(pageNum, pageSize, expertClassify, realName));
	}

	/**
	 * 根据id查询专家和著作
	 * 
	 * @param expertId
	 * @return
	 */
	@ApiOperation(value = "根据id查询专家和著作")
	@RequestMapping(value = "/selectExpertAndComposition/{expertId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectExpertAndComposition(@ApiParam(value = "专家编号", required = true) @PathVariable Long expertId) {
		return new Response().success(expertService.selectExpertAndComposition(expertId));
	}

	/**
	 * 根据姓名分页查询专家和著作
	 * 
	 * @param realName
	 * @return
	 */
	@ApiOperation(value = "根据姓名分页查询专家和著作")
	@RequestMapping(value = "/selectExpertAndCompositionByName", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectExpertAndCompositionByName(
			@ApiParam(value = "当前页", required = true) @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@ApiParam(value = "每页的数量", required = true) @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
			@ApiParam(value = "专家姓名", required = false) @RequestParam(name = "realName", required = false) String realName) {
		return new Response().success(expertService.selectExpertAndCompositionByName(pageNum, pageSize, realName));
	}

	/**
	 * 查询所有专家和著作
	 * 
	 * @return
	 */
	@ApiIgnore
	@ApiOperation(value = "查询所有专家和著作")
	@RequestMapping(value = "/selectAllExpertAndComposition", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectAllExpert() {
		return new Response().success(expertService.selectAllExpertAndComposition());
	}

	/**
	 * 查询所有专家信息
	 * 
	 * @return
	 */
	@ApiIgnore
	@ApiOperation(value = "查询所有专家信息")
	@RequestMapping(value = "/selectAllExpertMessage", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectAllExpertMessage() {
		return new Response().success(expertService.selectAllExpert());
	}

	/**
	 * 查询推荐专家和著作
	 * 
	 * @return
	 */
	@ApiOperation(value = "查询推荐专家和著作 ")
	@RequestMapping(value = "/selectRecommendExpertAndComposition", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectRecommendExpertAndComposition() {
		return new Response().success(expertService.selectRecommendExpertAndComposition());
	}


	/**
	 * 根据id查询专家
	 * 
	 * @param expertId
	 * @return
	 */
	@ApiIgnore
	@ApiOperation(value = "根据id查询专家")
	@RequestMapping(value = "/selectExpert/{expertId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectExpert(@ApiParam(value = "专家编号", required = true) @PathVariable Long expertId) {
		return new Response().success(expertService.selectByPrimaryKey(expertId));
	}

}
