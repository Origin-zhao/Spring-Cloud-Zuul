package com.joyuan;

import com.joyuan.filters.RateLimiterFilter;
import com.joyuan.filters.TokenFilter;
import com.netflix.discovery.util.RateLimiter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.EnableZuulServer;
import org.springframework.context.annotation.Bean;

//启动网关
@EnableZuulProxy
@SpringBootApplication
public class ZuulServerApplication {

	@Bean
	public RateLimiterFilter tokenFilter(){
		return  new RateLimiterFilter();
	}

	public static void main(String[] args) {
		SpringApplication.run(ZuulServerApplication.class, args);
	}
}
