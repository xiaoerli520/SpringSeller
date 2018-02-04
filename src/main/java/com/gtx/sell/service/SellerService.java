package com.gtx.sell.service;

import com.gtx.sell.dao.SellerInfo;

public interface SellerService {

    // 业务逻辑层就不要像repo那样写的太过于简单
    SellerInfo findSellerInfoByOpenid(String openid);

}
