package com.gpdata.template.timer;

import com.gpdata.template.base.common.utils.AESCoder;
import com.gpdata.template.base.service.RedisOperateService;
import org.apache.commons.math3.primes.Primes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimerTask;

/**
 * Created by acer_liuyutong on 2017/5/15.
 */
@Component
public class GenerateTask extends TimerTask{
    private final Logger logger = LoggerFactory.getLogger(GenerateTask.class);
    @Autowired
    private RedisOperateService redisOperateService;

    @Override
    public void run() {
        logger.debug("======正子时已到，开始搞事情======");
        Random random = new Random();
        String str = "RANDOM_NUM_GOGOGO";
        String SEED = "this is a number for luguangqiu in bigscreen";
        String key = AESCoder.encryptStringToBase64(str, SEED);
        int max = 28412;
        int range = 10;
        int rand = random.nextInt(range);

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int value ;
        //如果是质数则加上随机数，否则减去随机数
        if (Primes.isPrime(day)) {
            value = max + rand;
        }else {
            value = max - rand;
        }
        logger.debug("生成的数儿为: {}",value);

        redisOperateService.setValue(key, value + "");
        logger.debug("===把生成的数放到redis中===");
    }
}
