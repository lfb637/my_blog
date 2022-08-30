package com.lfb.dao;

import com.lfb.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author lfb
 * @data 2022/8/19 11:20
 */
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsernameAndPassword(String username, String password);
}
