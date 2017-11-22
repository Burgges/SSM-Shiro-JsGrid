package com.hand.service.impl;

import com.hand.annotation.OperationLog;
import com.hand.dao.UserMapper;
import com.hand.dto.LoginDto;
import com.hand.dto.MessageDto;
import com.hand.model.User;
import com.hand.service.UserService;
import com.hand.util.MessageUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by huiyu.chen on 2017/7/6.
 *
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);

    private static final String USER_NAME_EXISTED = "User name was existed";
    private static final String USER_NOT_FOUND = "User not found";
    private static final String USER_ID_NULL = "User id not null";
    private static final String USER_NAME_NULL = "User name not null";


    @Resource
    private UserMapper userMapper;

    private MessageUtil<User> messageUtil = new MessageUtil<>();

    @Override
    public MessageDto<User> save(User user) throws Exception {
        LOGGER.info("Insert user");
        Date date = new Date();
        user.setCreationDate(date);
        user.setLastUpdateDate(date);
        user.setPassword("123456");
        if(userMapper.findOneByName(user.getUserName()) != null){
            LOGGER.error("User name was existed: {}",new Exception(USER_NAME_EXISTED));
            return messageUtil.setMessageDto(500,USER_NAME_EXISTED, user);
        }
        userMapper.save(user);
        return messageUtil.setMessageDto(200,"Insert success", userMapper.findOneByName(user.getUserName()));
    }

    @Override
    public MessageDto<User> delete(Integer userId) {
        userMapper.delete(userId);
        return null;
    }

    @Override
    public MessageDto<User> update(User user, Integer userId) throws Exception {
        String message = "Update success";
        if(userId == null){
            LOGGER.error(USER_ID_NULL + ": {}", new Exception(USER_ID_NULL));
            return messageUtil.setMessageDto(500,USER_ID_NULL, user);
        }
        User user2 = userMapper.findOneById(userId);
        if(user2 == null){
            LOGGER.error(USER_NOT_FOUND + ": {}", new Exception(USER_NOT_FOUND));
            return messageUtil.setMessageDto(500,USER_NOT_FOUND, user);
        }
        User user3 = userMapper.findOneByName(user.getUserName());
        if(user3 != null && !user3.getUserId().equals(user2.getUserId())){
            LOGGER.error(USER_NAME_EXISTED + ": {}",new Exception(USER_NAME_EXISTED));
            return messageUtil.setMessageDto(500,USER_NAME_EXISTED, user);
        }
        user.setPassword("123456");
        Date date = new Date();
        user.setLastUpdateDate(date);
        user.setUserId(userId);
        userMapper.update(user);
        return messageUtil.setMessageDto(200,message, user);
    }

    @Override
    public User findOneById(Integer userId) throws Exception {
        if(userId == null){
            LOGGER.error(USER_ID_NULL + ": {}", new Exception(USER_ID_NULL));
            return null;
        }
        User user = userMapper.findOneById(userId);
        if(user == null){
            LOGGER.error(USER_NOT_FOUND + ": {}", new Exception(USER_NOT_FOUND));
            return null;
        }
        return user;
    }

    @Override
    public User findOneByName(String userName) throws Exception {
        if(userName == null || "".equals(userName)){
            LOGGER.error(USER_NAME_NULL + ": {}", new Exception(USER_NAME_NULL));
            return null;
        }
        User user = userMapper.findOneByName(userName);
        if(user == null){
            LOGGER.error(USER_NOT_FOUND + ": {}", new Exception(USER_NOT_FOUND));
            return null;
        }
        return user;
    }

    @Override
    public List<User> findList(Map<String, Object> map) {
        return userMapper.findList(map);
    }

    @OperationLog("User login")
    @Override
    public String login(LoginDto loginDto, HttpServletRequest request) throws Exception {
        String result = "login success";
        HttpSession session = request.getSession();
        String captchaCode = (String) session.getAttribute("captchaCode");
        if ("".equals(captchaCode) || captchaCode == null) {
            LOGGER.error("CaptchaCode was invalid: {}",new Exception("CaptchaCode was invalid"));
            return "CaptchaCode was invalid";
        }
        User user = userMapper.findOneByName(loginDto.getUserName());
        if (user == null) {
            LOGGER.error(USER_NOT_FOUND + ": {}",new Exception(USER_NOT_FOUND));
            return USER_NOT_FOUND;
        }
        if(!user.getPassword().equals(loginDto.getPassword())){
            LOGGER.error("Password error: {}",new Exception("Password error"));
            return "Password error";
        }

        String upperCaptchaCode = captchaCode.toUpperCase();
        String loginUpperCaptchaCode = loginDto.getCaptchaCode().toUpperCase();

        if (!upperCaptchaCode.equals(loginUpperCaptchaCode)){
            LOGGER.error("CaptchaCode error: {}",new Exception("CaptchaCode error"));
            return "CaptchaCode error";
        }
        session.setAttribute("userName", user.getUserName());
        session.setAttribute("UID", user.getUserId());
        return result;
    }
}
