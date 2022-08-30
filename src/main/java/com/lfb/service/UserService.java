package com.lfb.service;

import com.lfb.po.User;

/**
 * @author lfb
 * @data 2022/8/19 11:16
 */
public interface UserService {
    User checkUser(String name,String password);
}
