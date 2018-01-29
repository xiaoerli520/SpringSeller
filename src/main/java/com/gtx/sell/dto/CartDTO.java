package com.gtx.sell.dto;

import lombok.Data;

/**
 * 购物车数据传输对象
 */
@Data
public class CartDTO {

    private String productId;

    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
