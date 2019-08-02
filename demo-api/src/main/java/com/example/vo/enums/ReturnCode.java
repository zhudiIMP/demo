package com.haiercash.cmis.product.vo.enums;

/**
 * 响应码规范:
 * 00000 成功
 * 第一位,第二位:
 * 00 特殊码  不记录异常，只是提示
 * 01 数据库错误
 * 02 网络错误
 * 03 操作系统错误
 * 04 应用系统错误
 * 05 加解密异常
 * 10 数据检查错误
 * 20 配置信息错误
 * 30 业务逻辑错误
 * 40 银行返回错误映射
 * 50 消息发送错误
 * <p>
 * 第三位,系统标识:
 * 4 账务中心
 * 5 虚拟账户
 * 6 新消息平台
 */
public enum ReturnCode {
    success("00000", "成功"),

    /*01 数据库错误*/
    msg_insert_fail("MSG0160001", "数据库新增失败"),
    msg_update_fail("MSG0160002", "数据库修改失败"),
    msg_delete_fail("MSG0160003", "数据库删除失败"),

    /*10 数据检查错误*/
    msg_request_is_null("MSG1060001", "请求对象不能为空"),
    msg_request_param_is_null("MSG1060002", "请求参数不能为空"),
    msg_request_param_not_valid("MSG1060003", "请求参数未通过校验"),
    msg_param_lack("MSG1060004", "参数缺失"),

    /*04 应用系统错误*/
    msg_sys_error("MSG0460001", "系统异常"),

    /*05 加解密错误*/

    /*20 配置信息错误*/
    msg_not_support("MSG2060001", "不支持此业务"),
    msg_config_not_exists("MSG2060002", "配置不存在"),
    msg_config_disable("MSG2060003", "配置未开启"),
    msg_config_error("MSG2060004", "配置信息有误"),
    msg_dict_not_exists("MSG2060005", "字典项未配置"),
    msg_dict_error("MSG2060006", "字典项配置错误"),

    /* 50 消息发送错误 */
    msg_xg_send_error("MSG5060001", "信鸽发送失败"),
    msg_xg_user_error("MSG5060002", "信鸽推送用户错误"),
    msg_message_user_error("MSG5060002", "短信发送用户错误"),
    msg_mq_user_error("MSG5060003", "MQ推送用户错误"),

    /* 30 工作流错误*/
    msg_approve_role_not_exists("MSG30060001", "下一级审批角色无相关负责人"),
    msg_workflow_fail("MSG30060002", "开启工作流失败");

    private String code;
    private String desc;


    ReturnCode(String code, String message) {
        this.code = code;
        this.desc = message;
    }

    public String toCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
