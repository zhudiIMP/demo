package com.example.manager.factory.impl;


import com.haiercash.cmis.product.manager.factory.ISendMessageToolFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 发送工具工厂类
 *
 * @author keliang.jiang
 * @date 2018/1/8
 */
@Slf4j
@Service
public class SendMessageToolFactory implements ISendMessageToolFactory {

//    @Resource(name = "xgPushTool")
//    private ISendMessageTool xgPushTool;
//    @Resource(name = "mqSendTool")
//    private ISendMessageTool mqSendTool;
//    @Resource(name = "messageSendTool")
//    private ISendMessageTool messageSendTool;

    /**
     * 获取消息发送工具
     *
     * @param sendType   发送方式
     * @return 消息发送工具
     */
    /*@Override
    public ISendMessageTool getSendMessageTool(SendTypeEnum sendType) {
        ISendMessageTool tool = null;
        switch (sendType) {
            case XG:
                tool = xgPushTool;
                break;
            case MESSAGE_RRS:
                tool = messageSendTool;
                break;
            case MESSAGE_LX:
                tool = messageSendTool;
                break;
            case MQ:
                tool = mqSendTool;
                break;
            default:
                throw new MsgException(ReturnCode.msg_not_support, "暂不支持该类型发送工具");
        }
        return tool;
    }*/
}
