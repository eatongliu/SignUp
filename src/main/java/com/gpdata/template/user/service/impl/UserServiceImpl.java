package com.gpdata.template.user.service.impl;

import com.gpdata.template.base.service.BaseService;
import com.gpdata.template.user.dao.UserDao;
import com.gpdata.template.user.entity.User;
import com.gpdata.template.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by acer_liuyutong on 2017/3/20.
 */
@Service
public class UserServiceImpl extends BaseService implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(Long userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public User getUserByName(String account) {
        return null;
    }
}
