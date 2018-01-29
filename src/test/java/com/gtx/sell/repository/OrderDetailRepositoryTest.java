package com.gtx.sell.repository;

import com.gtx.sell.dao.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Test
    public void saveTest() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId("ASD");
        orderDetail.setDetailId("ASDDETAIL");
        orderDetail.setProductId("123123");
        orderDetail.setProductName("damizhou");
        orderDetail.setProductPrice(new BigDecimal(2.3));
        orderDetail.setProductQuantity(2);
        orderDetail.setProductIcon("www.baidu.com");

        OrderDetail result = orderDetailRepository.save(orderDetail);

        Assert.assertEquals("ASDDETAIL", result.getDetailId());
    }

    @Test
    public void findByOrderId() {
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId("ASD");
        Assert.assertNotEquals(0, orderDetailList.size());



    }
}