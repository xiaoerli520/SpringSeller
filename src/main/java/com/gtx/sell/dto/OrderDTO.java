package com.gtx.sell.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gtx.sell.dao.OrderDetail;
import com.gtx.sell.enums.OrderStatusEnum;
import com.gtx.sell.enums.PayStatusEnum;
import com.gtx.sell.utils.serializer.Date2LongSerializer;
import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
// @JsonInclude(JsonInclude.Include.NON_NULL) // 去掉null的属性值
public class OrderDTO {


    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private String buyerOpenid;

    private BigDecimal orderAmount;

    private Integer orderStatus; // 订单状态 默认状态为新

    private Integer payStatus; // 支付状态 默认0未支付

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    private List<OrderDetail> orderDetailList = new ArrayList<>(); // 需要返回默认一个中括号
}
