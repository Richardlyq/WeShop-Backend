package com.atguigu.spzx.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;
import com.atguigu.spzx.model.entity.user.UserInfo;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @ClassName AuthGlobalFilter
 * @Description
 * @Author Richard
 * @Date 2023-12-26 21:18
 **/
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    @Autowired
    private RedisTemplate<String , String> redisTemplate;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //获取请求路径
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        //判断路径 /api/**/auth/**,登录校验
        if(antPathMatcher.match("/api/**/auth/**",path)){
            //查询redis进行登录校验
            UserInfo userInfo = this.getUserInfo(request);
            if (userInfo == null){ //查不到，用户没登陆
                ServerHttpResponse response = exchange.getResponse();
                return out(response,ResultCodeEnum.LOGIN_AUTH);
            }
        }
        //放行
        return chain.filter(exchange);
    }

    //根据请求头里面的token查询redis，返回用户的信息
    private UserInfo getUserInfo(ServerHttpRequest request) {
        String token = "";
        List<String> tokenList = request.getHeaders().get("token");
        if(tokenList != null){
            token = tokenList.get(0);
        }
        if(StringUtils.hasText(token)){
            //查询redis返回数据
            String userInfoJSON = redisTemplate.opsForValue().get("user:spzx:" + token);
            if(!StringUtils.hasText(userInfoJSON)){
                return null;
            }else{
                UserInfo userInfo = JSON.parseObject(userInfoJSON, UserInfo.class);
                return userInfo;
            }
        }
        return null;
    }

    private Mono<Void> out(ServerHttpResponse response, ResultCodeEnum resultCodeEnum) {
        Result result = Result.build(null, resultCodeEnum);
        byte[] bits = JSONObject.toJSONString(result).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        //指定编码，否则在浏览器中会中文乱码
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
