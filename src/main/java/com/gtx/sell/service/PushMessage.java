package com.gtx.sell.service;

import com.gtx.sell.dto.OrderDTO;

public interface PushMessage {


    /**
     * 订单状态变更消息
     * @param orderDTO
     */
    void orderStatus(OrderDTO orderDTO);
}
