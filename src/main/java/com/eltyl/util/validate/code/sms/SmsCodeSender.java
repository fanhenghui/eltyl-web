package com.eltyl.util.validate.code.sms;

/**
 * 短信发送接口
 */
public interface SmsCodeSender {

    /**
     * 发送短信验证码
     * @param mobile
     * @param code
     */
    void send(String mobile, String code);
}
