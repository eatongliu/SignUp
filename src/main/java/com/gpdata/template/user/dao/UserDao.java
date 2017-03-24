package com.gpdata.template.user.dao;

import com.gpdata.template.user.entity.User;

/**
 * Created by acer_liuyutong on 2017/3/24.
 */
public interface UserDao {
    public User getUserById(Long userId);
}
