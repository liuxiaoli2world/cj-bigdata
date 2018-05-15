package com.lgsc.cjbd.order.pay;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PayUtil {

	/**
	 * 订单号为年月日时分秒加八位随机数
	 * @return
	 */
	public static String createOrderNo() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String now = df.format(new Date());
		String orderNo = now + (int) ((Math.random() * 9 + 1) * 10000000);
		return orderNo;
	}

}
