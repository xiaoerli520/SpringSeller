package com.gtx.sell.repository;

import com.gtx.sell.dao.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {
    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Test
    @Transactional
    public void saveTest() {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("ASDR");
        orderMaster.setBuyerName("骨头轻重");
        orderMaster.setBuyerPhone("13522222222");
        orderMaster.setBuyerAddress("neuq");
        orderMaster.setBuyerOpenid("jk2h1312h3k12");
        orderMaster.setOrderAmount(new BigDecimal(2.6));

        OrderMaster result = orderMasterRepository.save(orderMaster);

        Assert.assertNotNull(result);
    }

    @Test
    public void findByBuyerOpenid() {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid("jk2h1312h3k12", new PageRequest(0, 2));
        Assert.assertNotNull(orderMasterPage);
    }
}