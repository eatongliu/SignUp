package com.gpdata.template.other;

import com.gpdata.template.base.service.RedisOperateService;
import com.gpdata.template.timer.GenerateRandomNum;
import com.gpdata.template.timer.GenerateTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by acer_liuyutong on 2017/5/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:applicationContext.xml"
})
public class OtherTest {
    @Autowired
    private RedisOperateService redisOperateService;
    @Autowired
    private GenerateTask generateTask;
    @Test
    public void test1(){
        System.out.println(redisOperateService);
        System.out.println(generateTask);
    }
    @Test
    public void test2(){
        GenerateRandomNum generateRandomNum = new GenerateRandomNum();
    }
}
