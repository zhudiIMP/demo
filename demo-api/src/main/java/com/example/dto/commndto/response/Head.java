package com.example.dto.commndto.response;


import com.alibaba.fastjson.JSON;

import java.io.Serializable;


public class Head  implements Serializable {
    private String retFlag;//
    private String retMsg;//返回消息
    private String serno;//流水号

    public Head(String retFlag, String retMsg, String serno) {
        this.retFlag = retFlag;
        this.retMsg = retMsg;
        this.serno = serno;
    }

    public Head(){

    }

    public String getRetFlag() {
        return retFlag;
    }

    public void setRetFlag(String retFlag) {
        this.retFlag = retFlag;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public String getSerno() {
        return serno;
    }

    public void setSerno(String serno) {
        this.serno = serno;
    }
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
