package com.gtx.sell.VO;

import lombok.Data;

/**
 * ViewObject 返回给前端的对象
 * http请求返回的最外层对象
 */
@Data
public class ResultVO<T> {
    private Integer code;

    private String msg;

    private T data; // 泛型

    public ResultVO() {

    }

    public ResultVO(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data=data;
    }
}
