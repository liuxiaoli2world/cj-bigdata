package com.lgsc.cjbd.vo;

import java.util.Map;

/**
 * 请求消息
 * @author yeqj
 */
public class RequestMessage {
	
	private Biz biz;// 业务
	private Map<String, Object> data;// 数据
	
	public RequestMessage(Biz biz, Map<String, Object> data) {
		this.biz = biz;
		this.data = data;
	}

	public Biz getBiz() {
		return biz;
	}

	public void setBiz(Biz biz) {
		this.biz = biz;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

}
