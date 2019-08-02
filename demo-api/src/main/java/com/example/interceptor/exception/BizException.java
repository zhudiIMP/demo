package com.haiercash.cmis.product.interceptor.exception;



/**自定义异常
 * @author
 * @date
 */
//todo 待修改
public class BizException extends RuntimeException {
    private String errorMessage;
    private String errorCode;

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }


    public BizException(String code, String msg) {
        this.errorCode = code;
        this.errorMessage = msg;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
