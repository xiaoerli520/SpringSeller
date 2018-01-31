package com.gtx.sell.service.impl;

import com.gtx.sell.dao.OrderDetail;
import com.gtx.sell.dto.OrderDTO;
import com.gtx.sell.enums.OrderStatusEnum;
import com.gtx.sell.enums.PayStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.criterion.Order;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    private final String BUYER_OPENID = "1234566";

    private final String ORDER_ID = "1517111474489633294";

    @Test
    public void create() {

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("郭庆哲");
        orderDTO.setBuyerOpenid(BUYER_OPENID);
        orderDTO.setBuyerPhone("123123123213");
        // 购物车
        List<OrderDetail> orderDetailList = new ArrayList<>();

        OrderDetail o1 = new OrderDetail();

        o1.setProductId("123123");
        o1.setProductQuantity(20);

        orderDetailList.add(o1);

        orderDTO.setOrderDetailList(orderDetailList);
        orderDTO.setBuyerAddress("NEUQ");

        OrderDTO result = orderService.create(orderDTO);
        log.info("[创建订单] result = {}", result);
        Assert.assertNotNull(result);
    }

    @Test
    public void findOne() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        log.info("【查询单个订单】 = {}", orderDTO);
        Assert.assertNotNull(orderDTO);
    }

    @Test
    public void findList() {
        Page<OrderDTO> orderDTOPage = orderService.findList(BUYER_OPENID, new PageRequest(0,1));
        Assert.assertNotNull(orderDTOPage);

    }

    @Test
    @Transactional
    public void cancel() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result =  orderService.cancel(orderDTO);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(), result.getOrderStatus());
    }

    @Test
    @Transactional
    public void finish() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result =  orderService.finish(orderDTO);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(), result.getOrderStatus());
    }

    @Test
    @Transactional
    public void paid() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result =  orderService.paid(orderDTO);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(), result.getPayStatus());
    }

    @Test
    public void findAllList() {
        PageRequest pageRequest = new PageRequest(0, 10);
        Page<OrderDTO> orderDTOPage = orderService.findList(pageRequest);
        // log.info(orderDTOPage.getContent());
        System.out.println(orderDTOPage);
        Assert.assertNotNull(orderDTOPage);
    }
}