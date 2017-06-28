package com.gpdata.template.timer;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

/**
 * Created by acer_liuyutong on 2017/5/15.
 */
public class GenerateRandomNum implements ServletContextListener{
    //时间间隔(一天)
    private static final long PERIOD_DAY = 24 * 60 * 60 * 1000;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 16); //凌晨0点
        calendar.set(Calendar.MINUTE, 20);
        calendar.set(Calendar.SECOND, 0);
        Date date=calendar.getTime(); //第一次执行定时任务的时间
        //如果第一次执行定时任务的时间 小于当前的时间
        //此时要在 第一次执行定时任务的时间加一天，以便此任务在下个时间点执行。如果不加一天，任务会立即执行。
//        if (date.before(new Date())) {
//            date = DateUtils.addDay(date,1);
//        }
        Timer timer = new Timer();

        //通过util获取IOC容器，并取出GenerateTask对象
        WebApplicationContext ioc = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        GenerateTask task = ioc.getBean(GenerateTask.class);
        //安排指定的任务在指定的时间开始进行重复的固定延迟执行。
        timer.schedule(task, date, PERIOD_DAY);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
