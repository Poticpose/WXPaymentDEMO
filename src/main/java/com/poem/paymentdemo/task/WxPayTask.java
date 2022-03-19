package com.poem.paymentdemo.task;

import com.poem.paymentdemo.entity.OrderInfo;
import com.poem.paymentdemo.service.OrderInfoService;
import com.poem.paymentdemo.service.WxPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Component  //项目启动自动初始化
public class WxPayTask {
    @Resource
    private WxPayService wxPayService;

    @Resource
    private OrderInfoService orderInfoService;

    //每30秒查询一次超过5分钟未支付的订单
    @Scheduled(cron = "0/30 * * * * ?") //每30秒执行一次
    public void orderConfirm() throws Exception {
        log.info("orderConfirm 被执行......");

        List<OrderInfo> orderInfoList = orderInfoService.getNoPayOrderByDuration(5);

        for(OrderInfo orderInfo : orderInfoList){
            String orderNo = orderInfo.getOrderNo();
            log.warn("超时订单 ===> {}",orderNo);

            //核实订单状态：调用微信支付查单接口
            wxPayService.checkOrderStatus(orderNo);
        }
    }
}
