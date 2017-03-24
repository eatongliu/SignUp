package com.gpdata.template;

import com.gpdata.template.user.entity.User;
import com.gpdata.template.user.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by acer_liuyutong on 2017/3/23.
 */
public class ProxyUtil {
    private static ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
    private static UserService userService = (UserService)context.getBean("userServiceImpl");

    public static void main(String[] args) {
        User user = userService.getUserById(1L);
        System.out.println(user);
    }
}
