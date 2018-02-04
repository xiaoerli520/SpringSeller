package com.gtx.sell.service.impl;

import com.gtx.sell.dao.SellerInfo;
import com.gtx.sell.repository.SellerInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SellerServiceImplTest {

    @Autowired
    private SellerServiceImpl sellerService;


    private static final String OPEN_ID = "abc";

    @Test
    public void findSellerInfoByOpenid() {
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(OPEN_ID);
        Assert.assertNotNull(sellerInfo);
    }
}