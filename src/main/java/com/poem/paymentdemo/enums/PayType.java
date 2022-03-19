package com.poem.paymentdemo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor //定义为全参函数
@Getter
public enum PayType {
    /**
     * 微信
     */
    WXPAY("微信"),


    /**
     * 支付宝
     */
    ALIPAY("支付宝");

    /**
     * 类型
     */
    private final String type;
}
