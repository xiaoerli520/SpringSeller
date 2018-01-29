package com.gtx.sell.service;


import com.gtx.sell.dao.ProductInfo;
import com.gtx.sell.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 定义ProductService
 */
public interface ProductService {
    public ProductInfo findOne(String productId);

    public Page<ProductInfo> findAll(Pageable pageable); // 管理端 传入分页参数用来分页

    public ProductInfo save(ProductInfo productInfo);

    // 查询所有上架的

    public List<ProductInfo> findUpAll();

    // 增加库存
    void increaseStock(List<CartDTO> cartDTOList);


    // 减少库存

    void decreaseStock(List<CartDTO> cartDTOList);

}
