package com.lfb.service;

import com.lfb.dao.UserRepository;
import com.lfb.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lfb
 * @data 2022/8/19 11:18
 */
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;
    @Override
    public User checkUser(String name, String password) {
        User user = userRepository.findByUsernameAndPassword(name,password);
        return user;
    }
}
