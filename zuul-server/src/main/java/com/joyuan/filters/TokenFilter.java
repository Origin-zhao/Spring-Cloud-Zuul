package com.joyuan.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import javax.servlet.http.HttpServletRequest;

/**
 *请求必须包含token参数,否则直接抛出异常
 */
public class TokenFilter extends ZuulFilter {

    private  static final Log log = LogFactory.getLog(TokenFilter.class);

    @Override
    public String filterType() {
        //pre过滤器
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        //执行顺序,越小越优先值
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        //返回true,执行过滤处理
        return true;
    }

    @Override
    public Object run() {
        //获取当前上下文
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String token = request.getParameter("token");
        if(token == null){
            log.warn("token 没有找到");
            context.setSendZuulResponse(false); // 不对请求继续做路由处理
            context.setResponseStatusCode(401);
        }
        return null;
    }
}
