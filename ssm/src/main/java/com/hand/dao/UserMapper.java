package com.hand.dao;

import com.hand.dto.UserDto;
import com.hand.model.User;

import java.util.List;
import java.util.Map;

/**
 * Created by huiyu.chen on 2017/7/6.
 *
 */
public interface UserMapper {

    /**
     * create user
     * @param user create object
     */
    void save(User user);

    /**
     * delete user by userId
     * @param userId user id
     */
    void delete(Integer userId);

    /**
     * update user by userId
     * @param user new user
     */
    void update(User user);

    /**
     * select user by userId
     * @param userId condition of userId
     * @return result user
     */
    User findOneById(Integer userId);

    /**
     * select user by user name
     * @param userName condition of userId
     * @return result user
     */
    User findOneByName(String userName);

    /**
     * select user list by user properties
     * @param map condition of user properties
     * @return user list
     */
    List<User> findList(Map<String, Object> map);
}
