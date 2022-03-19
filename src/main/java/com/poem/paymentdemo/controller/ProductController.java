package com.poem.paymentdemo.controller;

import com.poem.paymentdemo.entity.Product;
import com.poem.paymentdemo.service.ProductService;
import com.poem.paymentdemo.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@CrossOrigin    //开启跨域
@Api(tags = "商品管理")    //设置测试页面测试接口名称
@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Resource
    private ProductService productService;

    @GetMapping("/test")
    @ApiOperation("测试接口")
    public R test(){
        return R.ok().data("message","hello").data("now", new Date());
    }

    @GetMapping("/list")
    public R list(){    //商品列表查询
        List<Product> list = productService.list();
        return R.ok().data("productList", list);
    }
}
