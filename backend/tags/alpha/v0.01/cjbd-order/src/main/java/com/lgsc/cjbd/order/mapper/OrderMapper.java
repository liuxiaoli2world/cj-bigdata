package com.lgsc.cjbd.order.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lgsc.cjbd.dao.BaseMapper;
import com.lgsc.cjbd.order.model.Order;

public interface OrderMapper extends BaseMapper<Order> {

	/**
	 * 条件查询订单
	 * 
	 * @param orderType
	 *            商品类型 book=图书 vip=会员
	 * @param condition
	 *            收货人/订单号
	 * @param orderDate
	 *            下单日期
	 * @return
	 */
	List<Map<String, Object>> selectBy(@Param("userId") Long userId, @Param("orderType") String orderType, @Param("condition") String condition,
			@Param("orderDate") String orderDate);

	/**
	 * 查询图书订单详情
	 * @param orderId
	 * @return
	 */
	Map<String, Object> selectBookOrderDetail(@Param("orderId") Long orderId);

	/**
	 * 查询vip订单详情
	 * @param orderId
	 * @return
	 */
	Map<String, Object> selectVipOrderDetail(@Param("orderId") Long orderId);
	
	/**
	 * 修改订单状态
	 * @param orderNo
	 * @param payStatus
	 * @param payTime
	 * @return
	 */
	int updateOrderStatus(@Param("orderNo") String orderNo, @Param("payStatus") Short payStatus, @Param("payTime") Date payTime);
	
	/**
	 * 将订单状态改为支付过时
	 * @param payOvertime 超时分钟数
	 * @return
	 */
	int updateToOverStatus(@Param("payOvertime") Integer payOvertime, @Param("userId") Long userId);
	
}