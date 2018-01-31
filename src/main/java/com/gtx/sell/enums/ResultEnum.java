package com.gtx.sell.enums;


import lombok.Getter;

@Getter
public enum  ResultEnum {
    PARAM_ERROR(1, "参数不正确"),

    PRODUCT_NOT_EXIST(10, "商品不存在"),

    PRODUCT_STOCK_ERROR(11, "库存不正确"),

    ORDER_NOT_EXIST(12, "订单不存在"),

    ORDERDETAIL_NOT_EXIST(13, "订单详情不存在"),

    ORDER_STATUS_ERROR(14, "订单状态不正确"),

    ORDER_UPDATE_FAIL(15, "订单状态更新失败"),

    ORDER_DETAIL_EMPTY(16, "订单详情为空"),

    ORDER_PAY_STATUS_ERROR(17, "订单支付状态错误"),

    CART_CANNOT_NULL(18, "购物车为空"),

    OPEN_ID(19, "OPENID不能为空"),

    ORDER_OWNER_ERROR(20, "订单openid不匹配"),

    WECHAT_MP_ERROR(21, "微信公众号错误"),

    WXPAY_NOTIFY_MONEY_VERIFY_ERROR(22, "微信支付异步通知校验不通过"),

    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
