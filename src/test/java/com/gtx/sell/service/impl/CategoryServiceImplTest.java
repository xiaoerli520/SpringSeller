package com.gtx.sell.service.impl;

import com.gtx.sell.dao.ProductCategory;
import com.gtx.sell.repository.ProductCategoryRepository;
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

@RunWith(SpringRunner.class)
@SpringBootTest
/**
 * 开发流程 DAO - service（Repository - Service - ServiceImpl） - Controller
 */
public class CategoryServiceImplTest {

    @Autowired
    private CategoryServiceImpl categoryService;
    //private ProductCategoryRepository productCategoryRepository;
    @Test
    public void findOne() {
        ProductCategory productCategory = categoryService.findOne(1);
        Assert.assertEquals(new Integer(1), productCategory.getCategoryId());
    }

    @Test
    public void findAll() {
        List<ProductCategory> productCategories = categoryService.findAll();
        Assert.assertNotNull(productCategories);
    }

    @Test
    public void findByCategoryTypeIn() {
        List<Integer> toFind = Arrays.asList(4, 5, 6);
        List<ProductCategory> productCategories = categoryService.findByCategoryTypeIn(toFind);
        Assert.assertNotEquals(0, productCategories.size());
    }

    @Test
    @Transactional
    public void save() {
        ProductCategory productCategory = new ProductCategory("sss", 6);
        ProductCategory result = categoryService.save(productCategory);
        Assert.assertEquals(new Integer(6), result.getCategoryType());
    }
}