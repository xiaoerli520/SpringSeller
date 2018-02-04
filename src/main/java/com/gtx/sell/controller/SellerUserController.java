package com.gtx.sell.controller;


import com.gtx.sell.config.ProjectUrl;
import com.gtx.sell.constant.CookieConstant;
import com.gtx.sell.constant.RedisConstant;
import com.gtx.sell.dao.SellerInfo;
import com.gtx.sell.enums.ResultEnum;
import com.gtx.sell.service.SellerService;
import com.gtx.sell.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@Slf4j
@RequestMapping("/seller")
public class SellerUserController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ProjectUrl projectUrl;

    @GetMapping("/login")
    public ModelAndView login(@RequestParam("openid") String openid, HttpServletResponse response, Map<String, Object> map) {
        // 1.openid 去到数据库查询
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(openid);

        if (sellerInfo == null) {
            map.put("msg", ResultEnum.LOGIN_FAIL.getMessage());
            // map.put("url", "/sell/seller/order/list");
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }
        // 2. 设置到redis

        // 生成token
        String token = UUID.randomUUID().toString();
        Integer expire = RedisConstant.EXPIRE;
        // todo 使用openid进行登录 后期写个界面直接输出就可以了


        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token), openid, expire, TimeUnit.SECONDS);

        // 3.设置token至cookie
        CookieUtil.set(response, CookieConstant.TOKEN, token, expire);
        return new ModelAndView("redirect:" + projectUrl.getSell()+"/sell/seller/order/list"); // 这里跳到orderlist的方法
        // 跳转尽量用完整地址
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        // 1.查cookie
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie != null) {
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
            CookieUtil.set(response, CookieConstant.TOKEN, null, 0);
        }
        map.put("msg", ResultEnum.LOGOUT_SUCCESS.getMessage());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success", map);

    }
}
