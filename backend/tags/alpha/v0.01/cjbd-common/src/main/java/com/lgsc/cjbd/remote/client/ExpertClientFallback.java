package com.lgsc.cjbd.remote.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.lgsc.cjbd.vo.Response;

/**
 * 失败回调
 * @author yeqj
 */
@Component
public class ExpertClientFallback implements ExpertClient {
	
	private static Logger logger = LogManager.getLogger(ExpertClientFallback.class);
	@Override
	public Response deleteExpertByUserId(Long userId) {
		logger.info("远程调用删除专家学者失败");
		return null;
	}

}
