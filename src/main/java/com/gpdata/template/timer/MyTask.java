package com.gpdata.template.timer;

import com.gpdata.template.base.common.utils.SpringApplicationContextUtil;
import com.gpdata.template.base.service.RedisOperateService;
import org.springframework.context.ApplicationContext;

/**
 * Created by acer_liuyutong on 2017/5/15.
 */
public class MyTask {
    public static void main(String[] args) {
        SpringApplicationContextUtil springApplicationContextUtil = SpringApplicationContextUtil.getInstance();
        ApplicationContext applicationContext = springApplicationContextUtil.getApplicationContext();
        System.out.println(applicationContext.getBean(RedisOperateService.class));

//        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
//        System.out.println(wac.getBean(RedisOperateService.class));

    }

}
