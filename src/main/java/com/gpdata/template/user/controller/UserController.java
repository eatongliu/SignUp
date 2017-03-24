package com.gpdata.template.user.controller;

import com.gpdata.template.base.controller.BaseController;
import com.gpdata.template.user.entity.User;
import com.gpdata.template.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by acer_liuyutong on 2017/3/24.
 */
@Controller
public class UserController extends BaseController{

    @Autowired
    private UserService userService;
    @RequestMapping(value = "/user/{userId}",method = RequestMethod.GET)
    public String getUser(@PathVariable Long userId, HttpServletRequest request){
        User user = userService.getUserById(userId);
        request.setAttribute("user",user);
        return "user/show";
    }
}
