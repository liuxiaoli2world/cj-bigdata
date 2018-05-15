package com.lgsc.cjbd.order.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConfig;
import com.lgsc.cjbd.order.mapper.HisBookMapper;
import com.lgsc.cjbd.order.mapper.HisVipMapper;
import com.lgsc.cjbd.order.mapper.OrderMapper;
import com.lgsc.cjbd.order.model.HisBook;
import com.lgsc.cjbd.order.model.HisVip;
import com.lgsc.cjbd.order.model.Order;
import com.lgsc.cjbd.order.pay.PayUtil;
import com.lgsc.cjbd.order.pay.WXPayConfigImpl;
import com.lgsc.cjbd.remote.client.BookClient;
import com.lgsc.cjbd.remote.client.UserClient;

/**
 * 订单服务
 */
@Service
public class OrderService {
	
	private static Logger log = LogManager.getLogger(OrderService.class);
	
	@Value("${wxpay.notifyUrl}")
	private String notifyUrl;// 支付结果通知地址
	
	@Value("${pay.overtime}")
	private Integer payOvertime;// 支付超时秒数

	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private HisVipMapper hisVipMapper;

	@Autowired
	private HisBookMapper hisBookMapper;

	@Autowired
	private UserClient userClient;

	@Autowired
	private BookClient bookClient;

	public int updateByPrimaryKey(Order record) {
		return orderMapper.updateByPrimaryKey(record);
	}

	public int updateByPrimaryKeySelective(Order record) {
		return orderMapper.updateByPrimaryKeySelective(record);
	}
	
	/**
	 * 删除订单
	 * @param orderNo 商户订单号
	 * @return 影响行数
	 */
	@Transactional
	public int deleteByOrderNo(String orderNo) {
		int result = 0;
		
		Order record = new Order();
		record.setOrderNo(orderNo);
		Order order = orderMapper.selectOne(record);
		if (order != null) {
			if ("book".equals(order.getGoodType())) {
				HisBook hisBook = new HisBook();
				hisBook.setOrderNo(orderNo);
				hisBookMapper.delete(hisBook);
			} else if ("vip".equals(order.getGoodType())) {
				HisVip hisVip = new HisVip();
				hisVip.setOrderNo(orderNo);
				hisVipMapper.delete(hisVip);
			}
			result = orderMapper.delete(record);
		}
		
		return result;
	}

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
	@Transactional(rollbackFor = Exception.class)
	public PageInfo<Map<String, Object>> selectBy(Integer pageNum, Integer pageSize, Long userId, String orderType, String condition,
			Date orderDate) {
		// 将超时订单改为支付过时
		orderMapper.updateToOverStatus(payOvertime, userId);
		
		String dateString = null;
		if (orderDate != null) {
			dateString = new SimpleDateFormat("yyyy-MM-dd").format(orderDate);
		}
		PageHelper.startPage(pageNum, pageSize);
		List<Map<String, Object>> list = orderMapper.selectBy(userId, orderType, condition, dateString);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	
	/**
	 * 查询订单详情
	 * @param orderId	订单id
	 * @param type	订单类型 book-图书 vip-会员
	 * @return
	 */
	public Map<String, Object> selectBookOrderDetail(Long orderId, String type) {
		Map<String, Object> map = null;
		switch (type) {
		case "book":
			map = orderMapper.selectBookOrderDetail(orderId);
			break;
		case "vip":
			map = orderMapper.selectVipOrderDetail(orderId);
			break;
		}
		if (map != null && map.get("consumeTime") != null) {
			int residueTime = payOvertime - Integer.parseInt(map.get("consumeTime").toString());// 剩余支付时间
			map.put("residueTime", residueTime);
		}
		return map;
	}

	/**
	 * 添加 vip 订单
	 * @param vipId vip商品id
	 * @param userid 用户id
	 * @param num 商品数量
	 * @param ip 终端ip
	 * @return 用户扫码支付的二维码链接
	 * @throws Exception 
	 */
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> insertVipOrder(Long vipId, Long userId, Integer num, String ip) throws Exception {
		log.info("添加vip订单开始，参数：[vipId=" + vipId + ", userId=" + userId + ", num=" + num + ", ip=" + ip + "]");
		Map<String, Object> result = new HashMap<>();
		String codeUrl = "";// 用户扫码支付的二维码链接
		
		/* 插入到本系统订单表 */
		Map<String, Object> vipMap = userClient.selectDetail(vipId);
		Order order = new Order(); // 订单
		String orderNo = PayUtil.createOrderNo();
		Date date = new Date();
		// order 邮箱、用户名、用户编号、订单号、支付状态、支付时间、创建时间(下单时间)、更新时间
		order = setUserProperties(order, userId, orderNo, date);
		if (vipMap == null || order == null) {
			return result;
		}
		HisVip hisVip = new HisVip(); // 会员商品
		order.setGoodType("vip"); // order 商品类型 book=图书 vip=会员
		order.setNum(num); // order 数量
		hisVip.setOrderNo(orderNo); // hisVip 订单号
		hisVip.setVipId(vipId); // hisVip 会员商品编号
		hisVip.setCreatedAt(date);// hisVip 创建时间
		hisVip.setUpdatedAt(date); // hisVip 更改时间
		hisVip.setName(String.valueOf(vipMap.get("name"))); // hisVip 商品名称
		hisVip.setImageUrl(String.valueOf(vipMap.get("imageUrl"))); // hisVip 商品图
		String duration = vipMap.get("duration").toString();
		hisVip.setDuration(Integer.parseInt(duration) + ""); // hisVip 时长
		Double price = (Double) vipMap.get("price");
		hisVip.setPrice(price);// hisVip 单价
		Double amount = new BigDecimal(price * num).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();// 总价，保留2位小数四舍五入
		order.setAmount(amount); // order 订单金额
		hisVipMapper.insertSelective(hisVip);
		orderMapper.insertSelective(order); // 插入订单
		log.info("订单插入订单表成功：[order = " + JSON.toJSONString(order) + ", hisVip = " + JSON.toJSONString(hisVip) + "]");
		
		/* 微信统一下单 */
		WXPayConfig config = WXPayConfigImpl.getInstance();
        WXPay wxpay = new WXPay(config);
        Map<String, String> data = new HashMap<String, String>();
        data.put("body", hisVip.getName());// 商品描述
        data.put("out_trade_no", orderNo);// 商户订单号
        data.put("device_info", "WEB");// 可以为终端设备号(门店号或收银设备ID)，PC网页或公众号内支付可以传"WEB"
        data.put("fee_type", "CNY");// 标价币种
        data.put("total_fee", String.valueOf((int) (order.getAmount() * 100)));// 标价金额，单位为分无小数点
        data.put("spbill_create_ip", ip);// 终端ip
        data.put("notify_url", notifyUrl);// 支付结果通知地址
        data.put("trade_type", "NATIVE");// 此处指定为扫码支付
        data.put("product_id", hisVip.getVipId() + "");// 商品ID
        log.info("微信统一下单开始：" + data);
        Map<String, String> resp = wxpay.unifiedOrder(data);
        if ("SUCCESS".equals(resp.get("return_code")) && "SUCCESS".equals(resp.get("result_code"))) {
        	log.info("微信统一下单成功：" + resp);
        	codeUrl = resp.get("code_url");
        	Order order2 = new Order();
        	order2.setOrderId(order.getOrderId());
        	order2.setPayCodeUrl(codeUrl);
        	orderMapper.updateByPrimaryKeySelective(order2);// 保存支付二维码地址
		} else {
			log.error("微信统一下单失败：" + resp);
			throw new Exception("微信统一下单失败");
		}
        
        log.info("添加vip订单结束");
        result.put("codeUrl", codeUrl);// 二维码地址
        result.put("payOvertime", payOvertime);// 支付超时分钟数
        result.put("orderNo", orderNo);// 商户订单号
		return result;
	}

	/**
	 * 添加book订单
	 * @param userId 用户id
	 * @param bookDirIds 图书目录id
	 * @param isFull 是否为全本图书
	 * @param ip 终端ip
	 * @return 用户扫码支付的二维码链接
	 * @throws Exception 
	 */
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> insertBookOrder(Long userId, String[] bookDirIds, Short isFull, String ip) throws Exception {
		log.info("添加book订单开始，参数：[userId=" + userId + ", bookDirIds=" + bookDirIds + ", isFull=" + isFull + ", ip=" + ip + "]");
		Map<String, Object> result = new HashMap<>();
		String codeUrl = "";// 用户扫码支付的二维码链接
		
		/* 插入到本系统订单表 */
		Order order = new Order(); // 订单
		String orderNo = PayUtil.createOrderNo();// 商户订单号
		Date date = new Date();
		// order 邮箱、用户名、用户编号、订单号、支付状态、支付时间、创建时间(下单时间)、更新时间
		order = setUserProperties(order, userId, orderNo, date);
		if (order == null) {
			return result;
		}
		String isbn = "";
		String body = "";
		for (int i = 0; i < bookDirIds.length; i++) { // 图书目录id
			HisBook hisBook = new HisBook();// 图书商品
			hisBook.setOrderNo(orderNo);// 图书商品 订单号
			String bookDirId = bookDirIds[i];
			String json = bookClient.selectBookDirDetail(Long.parseLong(bookDirId));
			if ("".equals(json) || json == null) {
				return result;
			}
			hisBook.setBookDirId(Long.parseLong(bookDirId));// 图书商品 图书目录编号
			JSONObject jsonObj = (JSONObject) JSONObject.parse(json);
			isbn = jsonObj.getString("isbn");
			String dirName = jsonObj.getString("dirName");
			String bookName = jsonObj.getString("bookName");
			String imageUrl = jsonObj.getString("imageUrl");
			Double fullPrice = jsonObj.getDouble("fullPrice");
			Double chapterPrice = jsonObj.getDouble("chapterPrice");
			hisBook.setDirName(dirName);// 图书商品 目录名称
			hisBook.setBookName(bookName);// 图书商品 商品名称
			hisBook.setImageUrl(imageUrl);// 图书商品 商品图
			hisBook.setIsbn(isbn);// 图书商品 isbn
			hisBook.setChapterPrice(chapterPrice);// 图书商品 单价
			hisBook.setIsFull(isFull);
			hisBook.setCreatedAt(date);// 图书商品 创建时间(下单时间)
			hisBook.setUpdatedAt(date);// 图书商品 修改时间
			hisBookMapper.insertSelective(hisBook);
			if (i == 0) {
				order.setNum(bookDirIds.length);// 订单 数量
				if (isFull == 1) {
					order.setAmount(fullPrice);
				} else {
					Double amount = new BigDecimal(bookDirIds.length * chapterPrice).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();// 总价，保留2位小数四舍五入
					order.setAmount(amount);
				}
				body = bookName;
			}
		}
		order.setGoodType("book");// 订单 商品类型
		orderMapper.insertSelective(order);
		log.info("订单插入订单表成功：" + JSON.toJSONString(order));
		
		/* 微信统一下单 */
		WXPayConfig config = WXPayConfigImpl.getInstance();
        WXPay wxpay = new WXPay(config);
        Map<String, String> data = new HashMap<String, String>();
        data.put("body", body);// 商品描述
        data.put("out_trade_no", orderNo);// 商户订单号
        data.put("device_info", "WEB");// 可以为终端设备号(门店号或收银设备ID)，PC网页或公众号内支付可以传"WEB"
        data.put("fee_type", "CNY");// 标价币种
        data.put("total_fee", String.valueOf((int) (order.getAmount() * 100)));// 标价金额，单位为分无小数点
        data.put("spbill_create_ip", ip);// 终端ip
        data.put("notify_url", notifyUrl);// 支付结果通知地址
        data.put("trade_type", "NATIVE");// 此处指定为扫码支付
        data.put("product_id", isbn);// 商品ID，此处设置为图书isbn
        log.info("微信统一下单开始：" + data);
        Map<String, String> resp = wxpay.unifiedOrder(data);
        if ("SUCCESS".equals(resp.get("return_code")) && "SUCCESS".equals(resp.get("result_code"))) {
        	log.info("微信统一下单成功：" + resp);
        	codeUrl = resp.get("code_url");
        	Order order2 = new Order();
        	order2.setOrderId(order.getOrderId());
        	order2.setPayCodeUrl(codeUrl);
        	orderMapper.updateByPrimaryKeySelective(order2);// 保存支付二维码地址
		} else {
			log.error("微信统一下单失败：" + resp);
			throw new Exception("微信统一下单失败");
		}
		
        log.info("添加book订单结束");
        result.put("codeUrl", codeUrl);// 二维码地址
        result.put("payOvertime", payOvertime);// 支付超时分钟数
        result.put("orderNo", orderNo);// 商户订单号
		return result;
	}

	/**
	 * 订单部分赋值
	 * @param order
	 * @param userId
	 * @param orderNo
	 * @param date
	 * @return
	 */
	private Order setUserProperties(Order order, Long userId, String orderNo, Date date) {
		Map<String, Object> userMap = userClient.selectSome(userId);
		if (userMap == null) {
			return null;
		}
		order.setUsername(String.valueOf(userMap.get("username"))); // order 用户名
		order.setEmail(String.valueOf(userMap.get("email"))); // order 邮箱
		order.setOrderNo(orderNo); // order 订单号
		order.setUserId(userId); // order 用户编号
		order.setPayStatus((short) 0); // order 支付状态 0=待支付 1=已支付 2=支付失败
		order.setPayTime(null); // order 支付时间
		order.setCreatedAt(date); // order 创建时间(下单时间)
		order.setUpdatedAt(date); // order 更新时间
		return order;
	}

	/**
	 * 修改订单状态为支付成功
	 * @param outTradeNo 商户订单号
	 * @param timeEnd 支付完成时间
	 * @return
	 * @throws Exception 
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean updateOrderStatus(String outTradeNo, String timeEnd) throws Exception {
		log.info("修改订单状态开始，参数：[outTradeNo=" + outTradeNo + ", timeEnd=" + timeEnd + "]");
		Boolean result = false;
		
		/* 修改订单支付状态 */
		Order orderParam = new Order();
		orderParam.setOrderNo(outTradeNo);
		orderParam.setPayStatus((short) 1);
		Order orderResult = orderMapper.selectOne(orderParam);
		if (orderResult != null) {
			log.info("该订单已支付");
			return result = false;
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		Date payTime = df.parse(timeEnd);
		int num = orderMapper.updateOrderStatus(outTradeNo, (short) 1, payTime);
		if (num > 0) {
			log.info("订单支付状态已修改为支付成功");
		}
		
		Order orderParam2 = new Order();
		orderParam2.setOrderNo(outTradeNo);
		Order order = orderMapper.selectOne(orderParam2);
		if ("vip".equals(order.getGoodType())) {// 如果商品类型是vip
			HisVip hisVipParam = new HisVip();
			hisVipParam.setOrderNo(outTradeNo);
			HisVip hisVip = hisVipMapper.selectOne(hisVipParam);
			
			/* 添加用户开始时间和结束时间 */
			int totalDuration = order.getNum() * Integer.parseInt(hisVip.getDuration());
			Calendar calendar = Calendar.getInstance();// 获取日历实例  
			calendar.setTime(payTime);
			calendar.add(Calendar.MONTH, totalDuration);
			Date beginDate = payTime;
			Date endDate = calendar.getTime();
			HisVip hisVipParam2 = new HisVip();
			hisVipParam2.setHisVipId(hisVip.getHisVipId());
			hisVipParam2.setBeginDate(beginDate);
			hisVipParam2.setEndDate(endDate);
			int num2 = hisVipMapper.updateByPrimaryKeySelective(hisVipParam2);
			if (num2 > 0) {
				log.info("会员商品历史信息开始时间和结束时间修改成功");
			}
			
			/* 添加用户会员商品关系 */
			SimpleDateFormat indf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			result = userClient.insertOrUpdateUserVip(order.getUserId(), hisVip.getVipId(), indf.format(beginDate), indf.format(endDate), totalDuration);
			if (result) {
				log.info("添加用户会员商品关系成功");
			}
		} else if ("book".equals(order.getGoodType())) {
			HisBook hisBookParam= new HisBook();
			hisBookParam.setOrderNo(outTradeNo);
			List<HisBook> hisBookList = hisBookMapper.select(hisBookParam);
			
			/* 添加用户图书商品关系 */
			List<Map<String, Object>> list = new ArrayList<>();
			for (HisBook hisBook : hisBookList) {
				Map<String, Object> map = new HashMap<>();
				map.put("userId", order.getUserId());
				map.put("isbn", hisBook.getIsbn());
				map.put("bookDirId", hisBook.getBookDirId());
				list.add(map);
			}
			result = userClient.batchInsertUserBook(list);
			if (result) {
				log.info("添加用户图书商品关系成功");
			}
		} else {
			result = false;
		}
		
		if (!result) {
			throw new Exception("修改订单状态失败");
		} else {
			log.info("修改订单状态结束");
		}
		
		return result;
	}
	
}
