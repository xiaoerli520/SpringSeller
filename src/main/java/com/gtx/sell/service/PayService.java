package com.gtx.sell.service;

import com.gtx.sell.dto.OrderDTO;

public interface PayService {
    void create(OrderDTO orderDTO);
}
