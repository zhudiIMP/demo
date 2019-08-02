package com.example.common.exception;


/**
 * @Descirption: 虚拟账户业务异常
 * @author fxh
 * 2016年12月6日15:53:31
 */
public class MsgException extends BaseException {

    public MsgException() {
        super();
    }

    public MsgException(String defineCode) {
        super(defineCode);
    }

    public MsgException(String defineCode, String defineMessage) {
        super(defineCode, defineMessage);
    }

//    public MsgException(ReturnCode returnCode) {
//        super(returnCode.toCode(), returnCode.getDesc());
//    }
//
//    public MsgException(ReturnCode returnCode, String defineMessage) {
//        super(returnCode.toCode(), StringUtils.isBlank(defineMessage) ? returnCode.getDesc() : defineMessage);
//    }

}
