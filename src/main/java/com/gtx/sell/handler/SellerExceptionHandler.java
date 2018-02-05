package com.gtx.sell.handler;


import com.gtx.sell.config.ProjectUrl;
import com.gtx.sell.exception.SellerAuthorizeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class SellerExceptionHandler {

    @Autowired
    private ProjectUrl projectUrl;

    // 拦截登陆异常
    @ExceptionHandler(value = SellerAuthorizeException.class)
    public ModelAndView handlerSellerAuthorException() {
        return new ModelAndView("redirect:".concat(projectUrl.getSell()).concat("/sell/wechat/qrAuthorize"));
    }

}
