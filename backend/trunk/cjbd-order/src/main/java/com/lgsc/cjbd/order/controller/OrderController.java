package com.lgsc.cjbd.order.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.pagehelper.PageInfo;
import com.lgsc.cjbd.order.model.Order;
import com.lgsc.cjbd.order.service.OrderService;
import com.lgsc.cjbd.vo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 订单管理
 * 
 * @author
 */
@Api(tags = "order", description = "订单管理")
@RestController
@RequestMapping("/order/order")
public class OrderController {

	private static Logger log = LogManager.getLogger(OrderController.class);

	@Autowired
	private OrderService orderService;

	/**
	 * 查询订单详细
	 */
	@ApiOperation(value = "查询订单详细")
	@RequestMapping(value = "/query", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response select(
			@ApiParam(value = "订单id", required = true) @RequestParam("orderId") Long orderId,
			@ApiParam(value = "订单类型 book=图书 vip=会员", required = true) @RequestParam("type") String type
	) {
		return new Response().success(orderService.selectBookOrderDetail(orderId, type));
	}

	/**
	 * 查询订单
	 */
	@ApiOperation(value = "查询订单")
	@RequestMapping(value = "/queryBy", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded", produces = "application/json; charset=UTF-8")
	public Response selectBy(
			@ApiParam(value = "当前页", required = true) @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@ApiParam(value = "每页的数量", required = true) @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
			@ApiParam(value = "查询场景 portal=门户 bg=后台", required = true) @RequestParam(name = "scene", required = true) String scene,
			@ApiParam(value = "用户id", required = false) @RequestParam(name = "userId", required = false) Long userId,
			@ApiParam(value = "商品类型 book=图书 vip=会员", required = false) @RequestParam(name = "orderType", required = false) String orderType,
			@ApiParam(value = "收货人/订单号", required = false) @RequestParam(name = "condition", required = false) String condition,
			@ApiParam(value = "下单日期", required = false) @RequestParam(name = "orderDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") @JsonFormat(pattern = "yyyy-MM-dd") Date orderDate
	) {
		PageInfo<Map<String, Object>> info = orderService.selectBy(pageNum, pageSize, scene, userId, orderType, condition, orderDate);
		if (info == null) {
			return new Response().failure();
		}
		return new Response().success(info);
	}

	/**
	 * 新增会员订单
	 */
	@ApiOperation(value = "新增会员订单")
	@RequestMapping(value = "/addVipOrder", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response insertVipOrder(
			HttpServletRequest request,
			@ApiParam(value = "用户编号", required = true) @RequestParam(value = "userId", required = true) Long userId,
			@ApiParam(value = "会员商品编号", required = true) @RequestParam(value = "vipId", required = true) Long vipId,
			@ApiParam(value = "商品数量", required = true) @RequestParam(value = "num", required = true) Integer num
	) {
		Response response = new Response();
		String ip = getRemoteHost(request);
		try {
			Map<String, Object> result = orderService.insertVipOrder(vipId, userId, num, ip);
			if (result != null && result.size() > 0) {
				response.success(result);
			} else {
				response.failure();
			}
		} catch (Exception e) {
			response.failure();
			log.error(e);
		}
		return response;
	}

	/**
	 * 新增图书订单
	 */
	@ApiOperation(value = "新增图书订单")
	@RequestMapping(value = "/addBookOrder", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response insertBookOrder(
			HttpServletRequest request,
			@ApiParam(value = "用户编号", required = true) @RequestParam(value = "userId", required = true) Long userId,
			@ApiParam(value = "图书商品编号", required = true) @RequestParam(value = "bookDirIds", required = true) String bookDirIds,
			@ApiParam(value = "是否为全本图书 0=否 1=是", required = true) @RequestParam(value = "isFull", required = true) Short isFull
	) {
		Response response = new Response();
		String[] ids = bookDirIds.split(",");
		String ip = getRemoteHost(request);
		try {
			Map<String, Object> result = orderService.insertBookOrder(userId, ids, isFull, ip);
			if (result != null && result.size() > 0) {
				response.success(result);
			} else {
				response.failure();
			}
		} catch (Exception e) {
			response.failure();
			log.error(e);
		}
		return response;
	}

	/**
	 * 修改订单
	 */
	@ApiIgnore
	@ApiOperation(value = "修改订单")
	@RequestMapping(value = "/modify", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response updateSelective(@ApiParam(value = "订单", required = true) @RequestBody @Validated Order record) {
		Response response = new Response();
		int num = orderService.updateByPrimaryKeySelective(record);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 删除订单
	 */
	@ApiOperation(value = "删除订单")
	@RequestMapping(value = "/remove", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response delete(
			@ApiParam(value = "商户订单号", required = true) @RequestParam String orderNo,
			@ApiParam(value = "删除场景 portal=门户 bg=后台", required = true) @RequestParam String scene
	) {
		Response response = new Response();
		int num = orderService.deleteByOrderNo(orderNo, scene);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	public String getRemoteHost(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
	}

}
