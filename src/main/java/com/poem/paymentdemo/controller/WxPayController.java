package com.poem.paymentdemo.controller;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.poem.paymentdemo.service.WxPayService;
import com.poem.paymentdemo.service.impl.WxPayServiceImpl;
import com.poem.paymentdemo.util.HttpUtils;
import com.poem.paymentdemo.util.WechatPay2ValidatorRequest;
import com.poem.paymentdemo.vo.R;
import com.wechat.pay.contrib.apache.httpclient.auth.Verifier;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j  //注入log对象，可以打印日志
@CrossOrigin
@RestController
@RequestMapping("api/wx-pay")
@Api(tags = "微信支付API")
public class WxPayController {
    @Resource
    private Verifier verifier;

    @Resource
    private WxPayService wxPayService;

    @ApiOperation("调用统一下单API，生成支付二维码")
    @PostMapping("native/{productId}")
    public R nativePay(@PathVariable Long productId) throws Exception {   //参数向前端传递了商品id
        log.info("发起支付请求");

        //返回支付二维码链接和订单号
        Map<String,Object> map = wxPayService.nativePay(productId);

        return R.ok().setData(map);
    }

    @PostMapping("/native/notify")
    public String nativeNotify(HttpServletRequest request, HttpServletResponse response){
        Gson gson = new Gson();
        Map<String,Object> map = new HashMap<>();   //应答对象

        try {
            //处理通知参数
            String body = HttpUtils.readData(request);
            Map<String,Object> bodyMap = gson.fromJson(body, HashMap.class);
            String requestId = (String)bodyMap.get("id");
            log.info("支付通知的id ===> {}",bodyMap.get("id"));
            log.info("支付通知完整数据 ===> {}",body);

            //TODO: 签名的验证
            WechatPay2ValidatorRequest wechatPay2ValidatorRequest = new WechatPay2ValidatorRequest(verifier, requestId, body);
            if(!wechatPay2ValidatorRequest.validate(request)){
                log.error("通知验签失败");
                //失败应答
                response.setStatus(500);
                map.put("code", "ERROR");
                map.put("message","通知验签失败");
                return gson.toJson(map);
            }
            log.info("通知验签成功");
            //TODO: 处理订单
            wxPayService.processOrder(bodyMap);

            //成功应答
            response.setStatus(200);
            map.put("code", "SUCCESS");
            map.put("message","成功");
            return gson.toJson(map);
        } catch (Exception e) {
            //失败应答
            response.setStatus(500);
            map.put("code", "ERROR");
            map.put("message","失败");
            return gson.toJson(map);
        }
    }

    //退款通知
    @PostMapping("/refunds/notify")
    public String refundsNotify(HttpServletRequest request, HttpServletResponse response){
        log.info("退款通知执行");
        Gson gson = new Gson();
        Map<String,Object> map = new HashMap<>();   //应答对象

        try {
            //处理通知参数
            String body = HttpUtils.readData(request);
            Map<String,Object> bodyMap = gson.fromJson(body, HashMap.class);
            String requestId = (String)bodyMap.get("id");
            log.info("支付通知的id ===> {}",bodyMap.get("id"));
            log.info("支付通知完整数据 ===> {}",body);

            //签名的验证
            WechatPay2ValidatorRequest wechatPay2ValidatorRequest = new WechatPay2ValidatorRequest(verifier, requestId, body);
            if(!wechatPay2ValidatorRequest.validate(request)){
                log.error("通知验签失败");
                //失败应答
                response.setStatus(500);
                map.put("code", "ERROR");
                map.put("message","通知验签失败");
                return gson.toJson(map);
            }
            log.info("通知验签成功");
            //处理退款单
            wxPayService.processRefund(bodyMap);

            //成功应答
            response.setStatus(200);
            map.put("code", "SUCCESS");
            map.put("message","成功");
            return gson.toJson(map);
        } catch (Exception e) {
            //失败应答
            response.setStatus(500);
            map.put("code", "ERROR");
            map.put("message","失败");
            return gson.toJson(map);
        }
    }

    //取消订单
    @PostMapping("/cancel/{orderNo}")
    public R cancel(@PathVariable String orderNo) throws Exception {
        log.info("取消订单");
        wxPayService.cancelOrder(orderNo);
        return R.ok().setMessage("订单已取消");
    }

    //查询订单
    @GetMapping("/query/{orderNo}")
    public R queryOrder(@PathVariable String orderNo) throws Exception {
        log.info("查询订单");
        String result = wxPayService.queryOrder(orderNo);
        return R.ok().setMessage("查询成功").data("result",result);
    }

    //下载账单
    @ApiOperation("下载账单")
    @GetMapping("/downloadbill/{billdate}/{type}")
    public R downlodBill(@PathVariable String billDate,@PathVariable String type) throws Exception{
        log.info("下载账单");
        String result = wxPayService.downloadBill(billDate,type);

        return R.ok().data("result", result);
    }

    //申请退款
    @ApiOperation("申请退款")
    @PostMapping("/refunds/{orderNo}/{reason}")
    public R refunds(@PathVariable String orderNo, @PathVariable String reason) throws Exception {
        log.info("申请退款");
        wxPayService.refund(orderNo,reason);
        return R.ok();
    }
}
