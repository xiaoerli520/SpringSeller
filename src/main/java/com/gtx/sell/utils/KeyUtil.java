package com.gtx.sell.utils;

import java.util.Random;

/**
 * 生成随机数
 */
public class KeyUtil {

    /**
     * 生成唯一主键
     * 格式：时间+随机数
     * @return
     */
    public static synchronized String genUniqueKey() {
        Random random = new Random();
        Integer a = random.nextInt(900000) + 100000; // 随机数 10 - 99 +10保证了两位数
        return System.currentTimeMillis() + String.valueOf(a);
    }
}
