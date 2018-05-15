package com.lgsc.cjbd.web;

import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

//@Configuration
public class HttpMessageConfiguration {
	
	@Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
    	FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
    	FastJsonConfig fastConfig = new FastJsonConfig();
    	fastConfig.setSerializerFeatures(
    			SerializerFeature.PrettyFormat,// 结果格式化
    			SerializerFeature.DisableCircularReferenceDetect,// 消除对同一对象循环引用的问题
    			SerializerFeature.WriteMapNullValue,// 输出值为null的字段
    			SerializerFeature.WriteNullStringAsEmpty,// 字符类型字段如果为null,输出为""
    			SerializerFeature.WriteNullListAsEmpty,// List字段如果为null,输出为[]
    			SerializerFeature.WriteDateUseDateFormat// 日期格式化
    	);
    	fastConverter.setFastJsonConfig(fastConfig);
    	HttpMessageConverter<?> converter = fastConverter;
    	return new HttpMessageConverters(converter);
    }

}
