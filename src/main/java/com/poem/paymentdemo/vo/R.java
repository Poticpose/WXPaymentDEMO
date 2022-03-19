package com.poem.paymentdemo.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

@Accessors(chain = true)    //可以进行链式操作
@Data   //lombok注解，自动生成get、set等操作
public class R {
    private Integer code;   //响应码
    private String message; //响应消息
    private Map<String,Object> data = new HashMap<>();

    public static R ok(){   //成功获取数据的提示方法
        R r = new R();
        r.setCode(0);   //响应码为0则成功
        r.setMessage("成功");
        return r;
    }

    public static R error(){   //错误获取数据的提示方法
        R r = new R();
        r.setCode(-1);   //响应码为-1则失败
        r.setMessage("失败");
        return r;
    }

    public R data(String key,Object value){    //对前端进行数据传输
        this.data.put(key,value);
        return this;
    }
}
