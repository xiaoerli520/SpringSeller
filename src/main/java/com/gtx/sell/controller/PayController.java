package com.gtx.sell.controller;


import com.gtx.sell.dto.OrderDTO;
import com.gtx.sell.enums.ResultEnum;
import com.gtx.sell.exception.SellException;
import com.gtx.sell.service.OrderService;
import com.gtx.sell.service.PayService;
import com.lly835.bestpay.model.PayResponse;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/pay")
@Slf4j
public class PayController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PayService payService;

    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId, @RequestParam("returnUrl") String returnUrl, Map<String, Object> map) {

        // 1.查询订单
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO == null) {
            log.error("[订单支付] 找不到订单 orderId = {}", orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        // 2.发起支付
        PayResponse payResponse = payService.create(orderDTO); // pay response是空的
        orderService.paid(orderDTO);
        map.put("payResponse", payResponse);
        map.put("returnUrl", returnUrl);


        return new ModelAndView("pay/create", map);
    }

    // 接受微信异步通知

    @PostMapping("/notify")
    public ModelAndView notify(@RequestBody String notifyData) {
        payService.notify(notifyData);

        // 返还给微信处理结果
        return new ModelAndView("pay/success");
    }
}
