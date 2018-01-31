package com.gtx.sell.controller;

import com.gtx.sell.dto.OrderDTO;
import com.gtx.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 卖家端订单控制器
 */
@Controller
@Slf4j
@RequestMapping("/seller/order")
public class SellerOrderController {
    // 用模板渲染进行

    @Autowired
    private OrderService orderService;

    /**
     * 订单列表
     * @param page 第几页 第一页开始
     * @param size 一页有多少条数据
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size, Map<String, Object> map) {
        PageRequest pageRequest = new PageRequest(page - 1, size);
        Page<OrderDTO> orderDTOPage = orderService.findList(pageRequest);
        map.put("orderDTOpage", orderDTOPage);
        map.put("currPage", page);
        // map.put("size", size);
        return new ModelAndView("order/list", map);
    }
}
