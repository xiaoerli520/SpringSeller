package com.gtx.sell.repository;

import com.gtx.sell.dao.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


/**
 * 单元测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {
    @Autowired
    private ProductCategoryRepository repository;



    @Test
    public void findOneTest() {
        ProductCategory productCategory = repository.findOne(1);
        System.out.println(productCategory.toString());
    }

    @Test
    @Transactional // Test 事务，可以和service的不一样 可以测试后不留痕迹
    public void saveTest() {
        ProductCategory productCategory = new ProductCategory("男生最爱", 6);
        ProductCategory result = repository.save(productCategory);
        Assert.assertNotNull(result);
        // Assert.assertNotEquals(null, result); // 不期望的 实际的
    }
    @Test
    public void findByCategoryTypeInTest() {
        List<Integer> list = Arrays.asList(4,5,6);
        List<ProductCategory> result = repository.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0, result.size());
    }




}