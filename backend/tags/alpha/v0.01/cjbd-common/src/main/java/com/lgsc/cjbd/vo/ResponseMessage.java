package com.lgsc.cjbd.vo;

import java.util.Map;

/**
 * 返回消息
 * @author yeqj
 */
public class ResponseMessage {
	
	private Biz biz;// 业务
	private Map<String, Object> data;// 数据
	private Result result;// 结果
	private String msg = "";// 错误信息
	
	public ResponseMessage(Biz biz, Map<String, Object> data, Result result) {
		this.biz = biz;
		this.data = data;
		this.result = result;
	}
	
	public ResponseMessage(Biz biz, Map<String, Object> data, Result result, String msg) {
		this.biz = biz;
		this.data = data;
		this.result = result;
		this.msg = msg;
	}

	public Biz getBiz() {
		return biz;
	}

	public void setBiz(Biz biz) {
		this.biz = biz;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

}
