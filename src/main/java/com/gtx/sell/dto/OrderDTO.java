package com.gtx.sell.dto;


import com.gtx.sell.dao.OrderDetail;
import com.gtx.sell.enums.OrderStatusEnum;
import com.gtx.sell.enums.PayStatusEnum;
import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderDTO {


    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private String buyerOpenid;

    private BigDecimal orderAmount;

    private Integer orderStatus; // 订单状态 默认状态为新

    private Integer payStatus; // 支付状态 默认0未支付

    private Date createTime;

    private Date updateTime;

    private List<OrderDetail> orderDetailList;
}
