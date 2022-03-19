package com.poem.paymentdemo.service;

import com.poem.paymentdemo.entity.OrderInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.poem.paymentdemo.enums.OrderStatus;

import java.util.List;

public interface OrderInfoService extends IService<OrderInfo> {
    OrderInfo createOrderByProductId(Long productId);

    void saveCodeUrl(String orderNo,String codeUrl);

    List<OrderInfo> listOrderByCreateTimeDesc();

    void updateStatusByOrderNo(String orderNo, OrderStatus orderStatus);

    String getOrderStatus(String orderNo);

    List<OrderInfo> getNoPayOrderByDuration(int minutes);

    OrderInfo getOrderByOrderNo(String orderNo);
}
