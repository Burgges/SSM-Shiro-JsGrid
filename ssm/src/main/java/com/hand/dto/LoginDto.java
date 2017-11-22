package com.hand.dto;

/**
 * Created by huiyu.chen on 2017/7/19.
 *
 */
public class LoginDto {

    private String userName;

    private String password;

    private String captchaCode;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCaptchaCode() {
        return captchaCode;
    }

    public void setCaptchaCode(String captchaCode) {
        this.captchaCode = captchaCode;
    }
}
