package com.poem.paymentdemo.controller;

import com.poem.paymentdemo.config.WxPayConfig;
import com.poem.paymentdemo.vo.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "测试控制器")
@RestController
@RequestMapping("/api/test")
public class TestController {
    @Resource   //将WxPayConfig注入
    private WxPayConfig wxPayConfig;

    @GetMapping
    public R getWxPay(){
        String mchId = wxPayConfig.getMchId();  //获取商户ID
        return R.ok().data("mchId", mchId);
    }
}
