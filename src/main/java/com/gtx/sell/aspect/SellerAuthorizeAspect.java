package com.gtx.sell.aspect;


import com.gtx.sell.constant.CookieConstant;
import com.gtx.sell.constant.RedisConstant;
import com.gtx.sell.enums.ResultEnum;
import com.gtx.sell.exception.SellException;
import com.gtx.sell.exception.SellerAuthorizeException;
import com.gtx.sell.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Pointcut("execution(public * com.gtx.sell.controller.Seller*.*(..))" + "&& !execution(public * com.gtx.sell.controller.SellerUserController.*(..))")
    public void verify() {}

    @Before("verify()")
    public void doVerify() {
        ServletRequestAttributes requestAttributes =(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = requestAttributes.getRequest();

        // 查询cookie
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);

        if (cookie == null) {
            log.error("[登陆验证] 用户没有登陆 cookie中没有token"); // 这边已经是登陆过的，登陆的时候回验证openid 这里就不用验证了
            throw new SellerAuthorizeException();
        }

        // 去redis里面查询
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));

        if (StringUtils.isEmpty(tokenValue)) {
            log.error("[登陆验证] 用户没有登陆 redis中没有token");
            throw new SellerAuthorizeException();
        }

    }
}
