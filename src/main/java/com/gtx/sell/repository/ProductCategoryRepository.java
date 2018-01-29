package com.gtx.sell.repository;

import com.gtx.sell.dao.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList); // 根据分类type列表进行查找详情列表



}
