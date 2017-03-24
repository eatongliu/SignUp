package com.gpdata.template.user.dao.impl;

import com.gpdata.template.base.dao.BaseDao;
import com.gpdata.template.user.dao.UserDao;
import com.gpdata.template.user.entity.User;
import org.springframework.stereotype.Repository;

/**
 * Created by acer_liuyutong on 2017/3/24.
 */
@Repository
public class UserDaoImpl extends BaseDao implements UserDao{
    @Override
    public User getUserById(Long userId){
        return (User) this.getCurrentSession().get(User.class,userId);
    }
}
