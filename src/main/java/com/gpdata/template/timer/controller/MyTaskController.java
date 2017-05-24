package com.gpdata.template.timer.controller;

import com.gpdata.template.base.service.RedisOperateService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by acer_liuyutong on 2017/5/15.
 */
@Controller
public class MyTaskController {

    @RequestMapping(value = "/task",method = RequestMethod.GET)
    @ResponseBody
    public String myTask(){
        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();

        return wac.getBean(RedisOperateService.class).toString();
    }

}
