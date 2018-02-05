package com.gtx.sell.service.impl;

import com.gtx.sell.dao.OrderMaster;
import com.gtx.sell.dto.OrderDTO;
import com.gtx.sell.service.OrderService;
import com.gtx.sell.service.PushMessage;
import org.aspectj.weaver.ast.Or;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PushMessageImplTest {

    @Autowired
    private PushMessageImpl pushMessage;

    @Autowired
    private OrderService orderService;

    @Test
    public void orderStatus() {
        OrderDTO orderDTO = orderService.findOne("1517384748713153531");

        pushMessage.orderStatus(orderDTO);

    }
}