package com.gtx.sell.repository;

import com.gtx.sell.dao.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {
    List<ProductInfo> findByProductStatus(Integer productStatus); // 是对自带的数据库操作的一个扩充

}
