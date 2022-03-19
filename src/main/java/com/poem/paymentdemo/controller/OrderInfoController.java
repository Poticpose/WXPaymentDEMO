package com.poem.paymentdemo.controller;

import com.poem.paymentdemo.entity.OrderInfo;
import com.poem.paymentdemo.enums.OrderStatus;
import com.poem.paymentdemo.service.OrderInfoService;
import com.poem.paymentdemo.vo.R;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/order-info")
@Api(tags = "商品订单管理")
public class OrderInfoController {
    @Resource
    private OrderInfoService orderInfoService;

    @GetMapping("/list")
    public R list(){
        List<OrderInfo> list = orderInfoService.listOrderByCreateTimeDesc();
        return R.ok().data("list", list);
    }

    //查询订单状态
    @GetMapping("query-order-status/{orderNo}")
    public R queryOrderStatus(@PathVariable String orderNo){
        String orderStatus = orderInfoService.getOrderStatus(orderNo);
        if(OrderStatus.SUCCESS.getType().equals(orderStatus)){
            return R.ok().setMessage("支付成功");
        }
        return R.ok().setCode(101).setMessage("支付中......");
    }
}
