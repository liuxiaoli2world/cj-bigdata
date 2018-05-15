package com.lgsc.cjbd.remote.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lgsc.cjbd.vo.Response;

import io.swagger.annotations.ApiParam;

@FeignClient(name = "cjbd-expert", fallback = ExpertClientFallback.class)
public interface ExpertClient {
	
	@RequestMapping(value = "/deleteExpertByUserId/{userId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response deleteExpertByUserId(@ApiParam(value = "id", required = true) @PathVariable(name="userId") Long userId);
	

}
