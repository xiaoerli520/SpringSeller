package com.gtx.sell.repository;


import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class OrderForm {

    @NotEmpty(message = "姓名必填")
    private String name; // 买家姓名

    @NotEmpty(message = "手机必填")
    private String phone; // 买家手机

    @NotEmpty(message = "地址必填")
    private String address; // 买家地址

    @NotEmpty(message = "买家OPENID必填")
    private String openid;

    @NotEmpty(message = "items必填")
    private String items;

}
