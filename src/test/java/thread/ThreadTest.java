package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by acer_liuyutong on 2017/4/20.
 */
public class ThreadTest {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        pool.submit(()-> {
            Thread.currentThread().setName("AAAA");
            for (int i = 0; i < 10 ; i++) {
                System.out.println(Thread.currentThread().getName());
            }
        });
        pool.submit(()-> {
            Thread.currentThread().setName("BBBB");
            for (int i = 0; i < 10 ; i++) {
                System.out.println(Thread.currentThread().getName());
            }
        });
        pool.submit(()-> {
            Thread.currentThread().setName("CCCC");
            for (int i = 0; i < 10 ; i++) {
                System.out.println(Thread.currentThread().getName());
            }
        });
        pool.shutdown();
    }
}
