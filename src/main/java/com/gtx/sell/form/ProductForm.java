package com.gtx.sell.form;


import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.math.BigDecimal;
import java.util.Date;
// 用于表单传递的Product

@Data
public class ProductForm {


    private String productId;


    private String productName;


    private BigDecimal productPrice;


    private Integer productStock;


    private String productDescription;


    private String productIcon;


    private Integer categoryType;
}
