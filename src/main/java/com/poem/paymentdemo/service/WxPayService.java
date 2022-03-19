package com.poem.paymentdemo.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;

public interface WxPayService {
    Map<String, Object> nativePay(Long productId) throws Exception;

    void processOrder(Map<String, Object> bodyMap) throws GeneralSecurityException, Exception;

    Map<String, Object> nativePayV2(Long productId, String remoteAddr) throws Exception;

    void cancelOrder(String orderNo) throws IOException, Exception;

    String queryOrder(String orderNo) throws Exception;

    void checkOrderStatus(String orderNo) throws Exception;

    void refund(String orderNo, String reason) throws Exception;

    String queryRefund(String refundNo) throws Exception;

    void processRefund(Map<String, Object> bodyMap) throws GeneralSecurityException, Exception;

    String queryBill(String billDate,String type) throws Exception;

    String downloadBill(String billDate, String type) throws Exception;

    void checkRefundStatus(String refundNo) throws Exception;
}
