package com.lgsc.cjbd.order.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConfig;
import com.github.wxpay.sdk.WXPayUtil;

import com.lgsc.cjbd.order.pay.WXPayConfigImpl;
import com.lgsc.cjbd.order.service.OrderService;
import com.lgsc.cjbd.vo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 支付
 * 
 * @author
 */
@Api(tags = "wxpay", description = "支付")
@RestController
@RequestMapping("/order/wxpay")
public class WXPayController {
	
	private static Logger log = LogManager.getLogger(WXPayController.class);
	
	@Autowired
	private OrderService orderService;
	
	/**
	 * 微信通知商户支付结果
	 */
	@ApiIgnore
	@ApiOperation(value = "微信通知商户支付结果")
	@RequestMapping(value = "/notify", method = RequestMethod.POST, produces = "application/xml; charset=UTF-8")
	public String notifyMerchant(HttpServletRequest request) {
		String ret_wx_msg = "";
		
		try {
			// 将流转换成字符串
			String notifyData = IOUtils.toString(request.getInputStream(), "UTF-8");
			WXPayConfig config = WXPayConfigImpl.getInstance();
	        WXPay wxpay = new WXPay(config);
	        Map<String, String> notifyMap = WXPayUtil.xmlToMap(notifyData);  // 转换成map
	        log.info("微信支付结果：" + notifyMap);
	        if (wxpay.isPayResultNotifySignatureValid(notifyMap)) {
	            if ("SUCCESS".equals(notifyMap.get("return_code")) && "SUCCESS".equals(notifyMap.get("result_code"))) {
	            	orderService.updateOrderStatus(notifyMap.get("out_trade_no"), notifyMap.get("time_end"));
				}
	            // 通知微信支付系统接收到信息
				ret_wx_msg = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
	        } else {
	            // 签名错误，如果数据里没有sign字段，也认为是签名错误
	        	ret_wx_msg = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[sign无效]]></return_msg></xml>";
	        }
		} catch (Exception e) {
			log.error(e);
		} 
		
		return ret_wx_msg;
	}
	
	/**
	 * 测试修改订单状态
	 */
	@ApiIgnore
	@ApiOperation(value = "测试修改订单状态")
	@RequestMapping(value = "/testUpdateOrderStatus", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response testUpdateOrderStatus(
			@ApiParam(value = "商户订单号", required = true) @RequestParam String outTradeNo,
			@ApiParam(value = "支付完成时间", required = true) @RequestParam String timeEnd
	) {
		Response response = new Response();
		try {
			boolean result = orderService.updateOrderStatus(outTradeNo, timeEnd);
			if (result) {
				response.success();
			} else {
				response.failure();
			}
		} catch (Exception e) {
			response.failure();
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 支付完成
	 */
	@ApiOperation(value = "支付完成")
	@RequestMapping(value = "/payFinish/{orderNo}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response payFinish(@ApiParam(value = "订单号", required = true) @PathVariable String orderNo) {
		Response response = new Response();
		WXPayConfig config = WXPayConfigImpl.getInstance();
        WXPay wxpay = new WXPay(config);
        Map<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no", orderNo);
        try {
        	Map<String, String> respMap = wxpay.orderQuery(data);
//        	log.info("微信支付结果：" + respMap);
//        	// 如果不确定微信是否能通知商户支付结果，则手动查询微信订单状态并修改商户订单
//        	if ("SUCCESS".equals(respMap.get("return_code")) && "SUCCESS".equals(respMap.get("result_code")) && "SUCCESS".equals(respMap.get("trade_state"))) {
//        		// 如果支付完成，则修改商户订单状态为成功
//        		orderService.updateOrderStatus(respMap.get("out_trade_no"), respMap.get("time_end"));
//			}
        	response.success(respMap);
        } catch (Exception e) {
        	response.failure(e.getMessage());
        	log.error(e);
        }
		return response;
	}
	
	/**
	 * 查询微信订单
	 */
	@ApiOperation(value = "查询微信订单")
	@RequestMapping(value = "/query/{orderNo}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response query(@ApiParam(value = "订单号", required = true) @PathVariable String orderNo) {
		Response response = new Response();
		WXPayConfig config = WXPayConfigImpl.getInstance();
        WXPay wxpay = new WXPay(config);
        Map<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no", orderNo);
        try {
        	Map<String, String> respMap = wxpay.orderQuery(data);
        	response.success(respMap);
        } catch (Exception e) {
        	response.failure(e.getMessage());
        	log.error(e);
        }
		return response;
	}

}
