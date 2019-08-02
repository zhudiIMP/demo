package com.example.dto.request;

import com.alibaba.fastjson.JSON;
import com.example.dto.commndto.request.Head;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class DemoRequest implements Serializable {
    @Valid
    @NotNull(message = "head不能为空")
    Head head;

    @Valid
    @NotNull(message = "body不能为空")
    DemoRequestBody body;

    @Override
    public String toString(){
        return JSON.toJSONString(this);
    }
}
