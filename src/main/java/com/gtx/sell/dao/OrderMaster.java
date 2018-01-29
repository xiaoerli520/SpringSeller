package com.gtx.sell.dao;

import com.gtx.sell.enums.OrderStatusEnum;
import com.gtx.sell.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.Transient;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@DynamicUpdate
@Data
public class OrderMaster {
    @Id
    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private String buyerOpenid;

    private BigDecimal orderAmount;

    private Integer orderStatus = OrderStatusEnum.NEW.getCode(); // 订单状态 默认状态为新

    private Integer payStatus = PayStatusEnum.WAIT.getCode(); // 支付状态 默认0未支付

    private Date createTime;

    private Date updateTime;

//    // 增加orderdetail的list来支持使用ordermaster显示完整的 但是数据库里面是没有的需要注解
//    @Transient
//    private List<OrderDetail> orderDetails;
//  使用DTO 数据传输对象进行绑定

}
