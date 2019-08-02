package com.example.dto.commndto.response;



import org.slf4j.Logger;

import java.io.Serializable;

/**
 * Created by luyongdang on 2019/2/19.
 */
public class ResponseVo<T> implements Serializable {
    private T body;
    private Head head;

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    public boolean isSuccess() {
        return true;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public ResponseVo( Head head,T body) {
        this.body = body;
        this.head = head;
    }

    public static ResponseVo<Void> success() {
        Head head=new Head();
        head.setRetFlag("00000000");
        head.setRetMsg("success");
        return new ResponseVo(head,null);
    }

    public static <T> ResponseVo<T> success(Head head,T body) {
        head.setRetFlag("00000000");
        head.setRetMsg("success");

        return new ResponseVo(head,body);
    }

    public static <T> ResponseVo<T> success(Logger logger, Head head, T body) {
        head.setRetFlag("00000000");
        head.setRetMsg("success");
        logger.info("returnMsg:" + head.toString() + body.toString());

        return new ResponseVo(head,body);
    }

    public static <T> ResponseVo<T> failure(Head head,T body) {
        return new ResponseVo(head,body);
    }

    public static <T> ResponseVo<T> failure(Head head,T body, Logger logger) {
        logger.info("returnMsg:" + head.toString() );
        return new ResponseVo(head,body);
    }

    private ResponseVo() {
    }
    protected boolean canEqual(Object other) {
        return other instanceof ResponseVo;
    }

}
