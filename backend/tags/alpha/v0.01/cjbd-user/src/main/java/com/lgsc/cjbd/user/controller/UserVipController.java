package com.lgsc.cjbd.user.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lgsc.cjbd.user.model.UserVip;
import com.lgsc.cjbd.user.service.UserVipService;
import com.lgsc.cjbd.vo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 用户会员关系
 * @author 
 */
@Api(tags = "用户会员关系")
@RestController
@RequestMapping("/user/uservip")
public class UserVipController {
	
	private static Logger log = LogManager.getLogger(UserVipController.class);
	
	@Autowired
	private UserVipService userVipService;
    
    /**
	 * 
	 */
	@ApiOperation(value = "")
	@RequestMapping(value = "/query/{id}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response select(@ApiParam(value = "", required = true) @PathVariable Long id) {
		return new Response().success(userVipService.selectByPrimaryKey(id));
	}
	
	/**
	 * 
	 */
	@ApiOperation(value = "")
	@RequestMapping(value = "/queryAll", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectAll(@ApiParam(value = "当前页", required = true) @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@ApiParam(value = "每页的数量", required = true) @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
		return new Response().success(userVipService.selectAll(pageNum, pageSize));
	}
	
	/**
	 * 添加用户会员关系
	 */
//	@ApiOperation(value = "添加用户会员关系")
//	@RequestMapping(value = "/addByUserVip", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
//	public Response insertSelective(@ApiParam(value = "用户会员关系", required = true) @RequestBody @Validated UserVip record) {
//		Response response = new Response();
//		int num = userVipService.insertSelective(record);
//		if (num > 0) {
//			response.success();
//		} else {
//			response.failure();
//		}
//		return response;
//	}
	
	/**
	 * 添加用户会员关系
	 */
//	@ApiOperation(value = "添加用户会员关系")
//	@RequestMapping(value = "/addByParam", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
//	public Boolean insertSelective(
//			@ApiParam(value = "会员商品编号", required = true) @RequestParam Long vipId,
//			@ApiParam(value = "用户编号", required = true) @RequestParam Long userId,
//			@ApiParam(value = "会员开始时间", required = true) @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date beginDate,
//			@ApiParam(value = "会员到期时间", required = true) @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endDate) {
//		UserVip record = new UserVip();
//		record.setVipId(vipId);
//		record.setUserId(userId);
//		record.setBeginDate(beginDate);
//		record.setEndDate(endDate);
//		int num = userVipService.insertSelective(record);
//		return num > 0 ? true : false;
//	}
	
	/**
	 * 
	 */
	@ApiOperation(value = "")
	@RequestMapping(value = "/modify", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response updateSelective(@ApiParam(value = "", required = true) @RequestBody @Validated UserVip record) {
		Response response = new Response();
		int num = userVipService.updateByPrimaryKeySelective(record);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}
	
	/**
	 * 
	 */
	@ApiOperation(value = "")
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response delete(@ApiParam(value = "", required = true) @PathVariable Long id) {
		Response response = new Response();
		int num = userVipService.deleteByPrimaryKey(id);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}
	/**
	 * 
	 * 通过用户id查找用户会员关系
	 * @return
	 */
	@ApiOperation(value = "通过用户id查找用户会员关系")
	@RequestMapping(value = "/selectUserVipByUserId/{userId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectUserVipByUserId(@PathVariable("userId")Long userId){
		Response response = new Response();
		return response.success(userVipService.selectUserVipByUserId(userId));
	}
	
	/**
	 * 添加或修改会员到期时间
	 * @param userId 用户id
	 * @param vipId vipId
	 * @param beginDate 开始时间，类型为字符串，格式为yyyy-MM-dd HH:mm:ss
	 * @param endDate 到期时间，类型为字符串，格式为yyyy-MM-dd HH:mm:ss
	 * @param duration 购买时长
	 * @return 是否成功
	 */
	@ApiIgnore
	@ApiOperation(value = "添加或修改会员到期时间")
	@RequestMapping(value = "/addOrModifyUserVip", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Boolean insertOrUpdateUserVip(
			@ApiParam(value = "用户id", required = true) @RequestParam("userId") Long userId, 
			@ApiParam(value = "vipId", required = true) @RequestParam("vipId") Long vipId, 
			@ApiParam(value = "开始时间", required = true) @RequestParam("beginDate") String beginDate, 
			@ApiParam(value = "到期时间", required = true) @RequestParam("endDate") String endDate, 
			@ApiParam(value = "购买时长", required = true) @RequestParam("duration") Integer duration
	) {
		SimpleDateFormat indf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Boolean result = false;
		try {
			result = userVipService.insertOrUpdateSelective(userId, vipId, indf.parse(beginDate), indf.parse(endDate), duration);
		} catch (ParseException e) {
			log.error(e);
		}
		return result;
	}
	
	/**
	 * 根据用户id查询会员状态
	 * @param userId
	 * @return
	 */
	@ApiOperation(value = "根据用户id查询会员状态")
	@RequestMapping(value = "/selectVipStatus" ,method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public String selectVipStatus(@ApiParam(value = "用户id", required = true) @RequestParam("userId") Long userId) {
		return userVipService.selectVipStatus(userId);
	}

}
