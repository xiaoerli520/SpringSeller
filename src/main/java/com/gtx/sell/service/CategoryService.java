package com.gtx.sell.service;

import com.gtx.sell.dao.ProductCategory;

import java.util.List;

/**
 * 定义类目service接口
 * 从JpA所需要的方法
 */
public interface CategoryService {

    public ProductCategory findOne(Integer categoryId);

    public List<ProductCategory> findAll();

    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    public ProductCategory save(ProductCategory productCategory);

    // public ProductCategory update(ProductCategory productCategory); ...不存在的
}
