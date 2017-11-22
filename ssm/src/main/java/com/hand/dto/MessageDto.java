package com.hand.dto;

/**
 * Created by huiyu.chen on 2017/7/11.
 *
 */
public class MessageDto<T> {

    private String message;

    private Integer code;

    private T t;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
