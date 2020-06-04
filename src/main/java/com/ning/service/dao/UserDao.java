package com.ning.service.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ning.service.entity.User;
import com.ning.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author shenjiang
 * @Description:
 * @Date: 2019/5/23 11:35
 */
@Repository
public class UserDao {

    @Autowired
    private UserMapper userMapper;

//    public User queryUserByEamilAndPassword(String email,String password){
//        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
//        queryWrapper.lambda().eq(User::getEmail,email).eq(User::getPassword,password);
//        return  userMapper.selectOne(queryWrapper);
//
//    }

    public User queryUserByUsername(String username){
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.lambda().eq(User::getUsername,username);
        return  userMapper.selectOne(queryWrapper);

    }
}
