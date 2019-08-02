package com.example.dto.commndto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class Head  implements Serializable{
    private String tradeCode;
    @NotBlank(message = "报文流水号不能为空")
    @Length(max=32,message = "报文流水号超长！不能大于32")
    private String serno;//报文流水号
    @NotBlank(message = "系统标识不能为空")
    @Length(max=2,message = "系统标识超长！不能大于2")
    private String sysFlag;//系统标识
//    交易类型不是必输项，所以不再校验
//    @NotBlank(message = "交易类型不能为空")
//    @Length(max=3,message = "交易类型超长！不能大于3")
    private String tradeType;//交易类型
    @NotBlank(message = "交易日期不能为空")
    @Length(max=10,message = "交易日期超长！不能大于10")
    private String tradeDate;//交易日期
    @NotBlank(message = "交易时间不能为空")
    @Length(max=8,message = "交易时间超长！不能大于8")
    private String tradeTime;//交易时间
    @NotBlank(message = "渠道编码不能为空")
    @Length(max=2,message = "渠道编码超长！不能大于2")
    private String channelNo;//渠道编码

    public String getSerno() {
        return serno;
    }

    public void setSerno(String serno) {
        this.serno = serno;
    }

    public String getSysFlag() {
        return sysFlag;
    }

    public void setSysFlag(String sysFlag) {
        this.sysFlag = sysFlag;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(String channelNo) {
        this.channelNo = channelNo;
    }

    public String getTradeCode() {
        return tradeCode;
    }

    public void setTradeCode(String tradeCode) {
        this.tradeCode = tradeCode;
    }
}
