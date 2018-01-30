package com.gtx.sell.controller;


import com.gtx.sell.dto.OrderDTO;
import com.gtx.sell.enums.ResultEnum;
import com.gtx.sell.exception.SellException;
import com.gtx.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/pay")
@Slf4j
public class PayController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/create")
    public void create(@RequestParam("orderId") String orderId, @RequestParam("returnUrl") String returnUrl) {

        // 1.查询订单
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO == null) {
            log.error("[订单支付] 找不到订单 orderId = {}", orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        // 2.发起支付
    }
}
