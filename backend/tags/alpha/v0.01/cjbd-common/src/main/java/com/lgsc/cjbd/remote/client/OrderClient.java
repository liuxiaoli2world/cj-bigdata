package com.lgsc.cjbd.remote.client;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "cjbd-order", fallback = OrderClientFallback.class)
public interface OrderClient {
	
	

}
