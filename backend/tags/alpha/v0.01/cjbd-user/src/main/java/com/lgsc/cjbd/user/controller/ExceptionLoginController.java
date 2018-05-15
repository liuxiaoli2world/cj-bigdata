package com.lgsc.cjbd.user.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lgsc.cjbd.user.model.ExceptionLogin;
import com.lgsc.cjbd.user.service.ExceptionLoginService;
import com.lgsc.cjbd.vo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 异常设置控制层
 * @author 
 */
@Api(tags = "exceptionlogin", description = "异常设置控制层")
@RestController
@RequestMapping("/user/exceptionlogin")
public class ExceptionLoginController {
	
	@Autowired
	private ExceptionLoginService exceptionLoginService;
    
    /**
	 * 查询异常设置
	 */
	@ApiOperation(value = "查询异常设置")
	@RequestMapping(value = "/query", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response select() {
		return new Response().success(exceptionLoginService.selectByPrimaryKey());
	}
	
	/**
	 * 修改异常设置
	 */
	@ApiOperation(value = "修改异常设置")
	@RequestMapping(value = "/modify", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response updateSelective(@ApiParam(value = "异常设置实体", required = true) @RequestBody @Validated ExceptionLogin record) {
		Response response = new Response();
		record.setUpdatedAt(new Date());
		int num = exceptionLoginService.updateByPrimaryKeySelective(record);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	

}
