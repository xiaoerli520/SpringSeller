package com.gtx.sell.service.impl;

import com.gtx.sell.dto.OrderDTO;
import com.gtx.sell.enums.ResultEnum;
import com.gtx.sell.exception.SellException;
import com.gtx.sell.service.PayService;
import com.gtx.sell.utils.JsonUtil;
import com.gtx.sell.utils.MathUtil;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class PayServiceImpl implements PayService {

    private static final String ORDER_NAME = "微信点餐订单";

    @Autowired
    private BestPayServiceImpl bestPayService;

    @Autowired
    private OrderServiceImpl orderService;

    @Override
    public PayResponse create(OrderDTO orderDTO) {
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("[微信支付] 发起支付 request = {}", JsonUtil.toJson(payRequest));

        // PayResponse payResponse = bestPayService.pay(payRequest); 取消pay动作 因为用不了接口啊

        PayResponse payResponse = new PayResponse();
        log.info("[微信支付] 发起支付 response = {}", JsonUtil.toJson(payResponse));
        return payResponse;

    }
    @Override
    public PayResponse notify(String notifyData) {
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("[微信支付] 异步通知 payresponse = {}", JsonUtil.toJson(payResponse));

        // 修改订单支付状态
        OrderDTO orderDTO = orderService.findOne(payResponse.getOrderId());
        // 判断订单是否存在
        if (orderDTO == null) {
            log.error("[微信支付] 订单不存在 orderid={}", orderDTO.getOrderId());
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        // 判断金额是否一致
        if (!MathUtil.equals(payResponse.getOrderAmount(), orderDTO.getOrderAmount().doubleValue())) {
            log.error("[微信支付] 订单价格不符合 orderId={}", orderDTO.getOrderId());
            throw new SellException(ResultEnum.WXPAY_NOTIFY_MONEY_VERIFY_ERROR);
        }

        orderService.paid(orderDTO);
        return payResponse;
    }

    // 退款

    @Override
    public RefundResponse refund(OrderDTO orderDTO) {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderId(orderDTO.getOrderId());
        refundRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("[微信退款] request = {} ", refundRequest);
        RefundResponse refundResponse = bestPayService.refund(refundRequest);
        log.info("[微信退款] reponse = {}", refundResponse);

        return refundResponse;
    }
}
