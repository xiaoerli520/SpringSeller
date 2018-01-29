package com.gtx.sell.controller;


import com.gtx.sell.VO.ProductInfoVO;
import com.gtx.sell.VO.ProductVO;
import com.gtx.sell.VO.ResultVO;
import com.gtx.sell.dao.ProductCategory;
import com.gtx.sell.dao.ProductInfo;
import com.gtx.sell.service.CategoryService;
import com.gtx.sell.service.ProductService;
import com.gtx.sell.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController // 可默认为json格式
@RequestMapping("/buyer/product") // 直接可以URL前缀
public class BuyerProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;



    @GetMapping("/list")
    public ResultVO list() {

        List<ProductInfo> productInfoList = productService.findUpAll(); // 查询所有上架的商品
        // List<Integer> categoryTypeList = new ArrayList<>();

        // 传统方法

//        for (ProductInfo productInfo: productInfoList) {
//            categoryTypeList.add(productInfo.getCategoryType());
//        }

        // 精简做法 java8 lambda表达式
        List<Integer> categoryTypeList = productInfoList.stream().map(e -> e.getCategoryType()).collect(Collectors.toList());



        List<ProductCategory> productCategoryList =  categoryService.findByCategoryTypeIn(categoryTypeList);
        List<ProductVO> productVOList = new ArrayList<>();
        for (ProductCategory productCategory: productCategoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();

            for (ProductInfo productInfo:productInfoList) {
                if (productInfo.getCategoryType().equals(productVO.getCategoryType())) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }




        return ResultVOUtil.success(productVOList);
    }
}
