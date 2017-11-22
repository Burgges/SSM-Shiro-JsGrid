package com.hand.service.impl;

import com.hand.dto.MessageDto;
import com.hand.model.User;
import com.hand.service.UserService;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by huiyu.chen on 2017/7/7.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-mybatis.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceImplTest {

    private static Logger logger = Logger.getLogger(UserServiceImplTest.class);

    @Resource
    private UserService userService;

    private static Integer userId;
    private static String userName;

    @Test
    public void aSave() throws Exception {
        User user = new User();
        userName = "test001"+new Date().getTime();
        user.setUserName(userName);
        user.setPassword("123456");
        user.setUserSex("f");
        user.setUserMail("test@hand-china.com");
        MessageDto<User> messageDto = userService.save(user);
        Assert.assertNotNull(messageDto.getT().getUserId());
        userId = messageDto.getT().getUserId();
        logger.info("aSave: " + userId);
    }

    @Test
    public void zDelete() throws Exception {
        logger.info("zDelete: " + userId);
        userService.delete(userId);
    }

    @Test
    public void update() throws Exception {
        logger.info("update: " + userId);
        User user = new User();
        userName = "test001_"+new Date().getTime();
        user.setUserName(userName);
        user.setPassword("123456");
        user.setUserSex("f");
        user.setUserMail("test01@hand-china.com");
        userService.update(user, userId);
        Assert.assertEquals(user.getUserName(),userService.findOneById(userId).getUserName());
    }

    @Test
    public void findOneById() throws Exception {
        logger.info("findOneById: " + userId);
        Assert.assertNotNull(userService.findOneById(userId));
    }

    @Test
    public void findOneByName() throws Exception {
        logger.info("findOneByName: " + userId +"_"+userName);
        Assert.assertNotNull(userService.findOneByName(userName));
    }

    @Test
    public void findList() throws Exception {
        logger.info("findList: " + userId);
        Assert.assertNotNull(userService.findList(null));
    }

}