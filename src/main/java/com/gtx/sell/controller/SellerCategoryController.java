package com.gtx.sell.controller;


import com.gtx.sell.dao.ProductCategory;
import com.gtx.sell.exception.SellException;
import com.gtx.sell.form.CategoryForm;
import com.gtx.sell.service.CategoryService;
import com.lly835.bestpay.rest.type.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/seller/category")
public class SellerCategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ModelAndView list(Map<String, Object> map) {
        List<ProductCategory> productCategoryList = categoryService.findAll();
        map.put("productCategoryList", productCategoryList);
        return new ModelAndView("category/list", map);
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId", required = false) Integer categoryId, Map<String, Object> map) {
        if (categoryId != null) {
            ProductCategory productCategory = categoryService.findOne(categoryId);
            map.put("productCategory", productCategory);
        }

        return new ModelAndView("category/index", map);
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid CategoryForm form, BindingResult bindingResult, Map<String, Object>map) {
        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage()); // 直接使用异常的方法进行报错
            map.put("url", "/sell/seller/category/index");
            return new ModelAndView("common/error", map);
        }
        ProductCategory productCategory  = new ProductCategory();
        try {
            if (form.getCategoryId() != null) {
                productCategory = categoryService.findOne(form.getCategoryId());
            }
            BeanUtils.copyProperties(form, productCategory);
            categoryService.save(productCategory);
        } catch (SellException e) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage()); // 直接使用异常的方法进行报错
            map.put("url", "/sell/seller/category/index");
            return new ModelAndView("common/error", map);
        }
        map.put("url", "/sell/seller/category/list");
        return new ModelAndView("common/success", map);

    }

}
