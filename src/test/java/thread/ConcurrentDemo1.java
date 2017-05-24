package thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by acer_liuyutong on 2017/5/12.
 */
//判断：两个线程访问Phone，请打印出先访问的是苹果还是安卓

//1	标准版,先打印苹果还是安卓？
//2 增加Thread.sleep()给苹果方法，先打印苹果还是安卓？
//3 增加Hello方法，先打印苹果还是Hello
//4 有两部手机，先打印苹果还是安卓？
//5 两个静态同步方法，有一部手机，先打印苹果还是安卓？
//6 两个静态同步方法，有两部手机，先打印苹果还是安卓？
//7 一个普通同步方法，一个静态同步方法，有一部手机，先打印苹果还是安卓？
//8 一个普通同步方法，一个静态同步方法，有两部手机，先打印苹果还是安卓？
public class ConcurrentDemo1 {
    public static void main(String[] args) {
        final Phone phone1 = new Phone();
        final Phone phone2 = new Phone();

        ExecutorService pool = new ThreadPoolExecutor(3,5,50, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(20));
        pool.submit(() -> {
            Thread.currentThread().setName("AAA");
            for (int i = 0; i <1; i++) {
                try {
//                    Thread.sleep(2000);
//                    phone1.getIOS();
                    phone2.getAndroid();
//                    phone1.getHello();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        pool.submit(() -> {
            Thread.currentThread().setName("BBB");
            for (int i = 0; i <1; i++) {
                try {
//                    phone1.getAndroid();
                    phone2.getIOS();
                    //phone1.getHello();
//                    phone2.getAndroid();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        pool.shutdown();
    }
}

class Phone {
    public static synchronized void getIOS() throws Exception {
        System.out.println(Thread.currentThread().getName());
        Thread.sleep(2000);
        System.out.println("----------getIOS");
    }

    public synchronized void getAndroid() throws Exception {
        Thread.sleep(2000);
        System.out.println("----------getAndroid");
    }

    public void getHello() {
        System.out.println("----------getHello");
    }
}