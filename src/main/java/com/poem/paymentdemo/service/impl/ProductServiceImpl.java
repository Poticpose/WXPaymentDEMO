package com.poem.paymentdemo.service.impl;

import com.poem.paymentdemo.entity.Product;
import com.poem.paymentdemo.mapper.ProductMapper;
import com.poem.paymentdemo.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

}
