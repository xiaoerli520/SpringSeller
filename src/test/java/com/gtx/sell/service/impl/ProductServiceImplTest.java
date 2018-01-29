package com.gtx.sell.service.impl;

import com.gtx.sell.dao.ProductInfo;
import com.gtx.sell.enums.ProductStatusEnum;
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
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {
    @Autowired
    private ProductServiceImpl productService;

    @Test
    public void findOne() {
        ProductInfo productInfo = productService.findOne("123123");
        Assert.assertEquals("123123", productInfo.getProductId());
    }

    @Test
    public void findAll() {
        PageRequest pageRequest = new PageRequest(0, 1);
        Page<ProductInfo> productInfos = productService.findAll(pageRequest);
        System.out.println(productInfos.getTotalElements());
    }

    @Test
    @Transactional
    public void save() {
        ProductInfo productInfo = new ProductInfo("testadd2", new BigDecimal(200), 200, "nice to eat", "www.baidu.com", ProductStatusEnum.DOWN.getCode(), 5);
        productInfo.setProductId("456456");
        ProductInfo result = productService.save(productInfo);
        Assert.assertNotNull(result);
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> productInfos = productService.findUpAll();
        Assert.assertNotNull(productInfos);
    }
}