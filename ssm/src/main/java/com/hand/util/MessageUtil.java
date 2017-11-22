package com.hand.util;

import com.hand.dto.MessageDto;

/**
 * return message create class
 * Created by huiyu.chen on 2017/7/19.
 *
 */
public class MessageUtil<T> {

    /**
     * Set detail message
     * @param code status code
     * @param Message return message
     * @param t primary class
     * @return messageDto
     */
    public MessageDto<T> setMessageDto(Integer code, String Message, T t){
        MessageDto<T> messageDto = new MessageDto<>();
        messageDto.setT(t);
        messageDto.setMessage(Message);
        messageDto.setCode(code);
        return messageDto;
    }
}
