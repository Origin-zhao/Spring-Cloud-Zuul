package com.joyuan.filters;

import com.netflix.discovery.util.RateLimiter;
import com.netflix.eureka.RateLimitingFilter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.constants.ZuulConstants;
import com.netflix.zuul.context.RequestContext;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class RateLimiterFilter extends ZuulFilter {
    //设置请求速度单位,分钟计算,使用令牌桶算法实现
    private final RateLimiter limiter = new RateLimiter(TimeUnit.MINUTES);

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        // 设置最优先执行
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Override
    public boolean shouldFilter() {
        //默认开启
        return true;
    }

    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletResponse response = context.getResponse();
        //设置最大速率和平均速率,不成功返回false, 最大爆发10,平均速率10
        boolean acquire = limiter.acquire(20, 10);
        if(!acquire){//
            HttpStatus status = HttpStatus.TOO_MANY_REQUESTS;
            response.setContentType(MediaType.TEXT_PLAIN_VALUE);
            response.setStatus(status.value());
            try {
                response.getWriter().append(status.getReasonPhrase());
            } catch (IOException e) {
                e.printStackTrace();
            }
            context.setSendZuulResponse(false);
        }
        return null;
    }
}
