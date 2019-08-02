package com.example.dto.response;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.Serializable;
@Data
public class DemoResponse implements Serializable {
    private String demo;

    public String toString(){
        return JSON.toJSONString(this);
    }
}
