package thread;

import java.util.concurrent.*;

/**
 * Created by acer_liuyutong on 2017/5/2.
 */
public class TreadTest2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //创建等待队列
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(20);
        ExecutorService pool = new ThreadPoolExecutor(3,5,50, TimeUnit.MILLISECONDS,queue);
        pool.submit(() -> {
            System.out.println(Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        pool.submit(() -> System.out.println(Thread.currentThread().getName()));
        pool.submit(() -> System.out.println(Thread.currentThread().getName()));
        pool.submit(() -> System.out.println(Thread.currentThread().getName()));
        pool.submit(() -> System.out.println(Thread.currentThread().getName()));
        pool.submit(() -> System.out.println(Thread.currentThread().getName()));
        pool.submit(() -> System.out.println(Thread.currentThread().getName()));
        pool.submit(() -> System.out.println(Thread.currentThread().getName()));
        pool.submit(() -> System.out.println(Thread.currentThread().getName()));
        pool.submit(() -> System.out.println(Thread.currentThread().getName()));
        pool.submit(() -> System.out.println(Thread.currentThread().getName()));
        pool.submit(() -> System.out.println(Thread.currentThread().getName()));
        pool.submit(() -> System.out.println(Thread.currentThread().getName()));
        pool.submit(() -> System.out.println(Thread.currentThread().getName()));
        pool.submit(() -> System.out.println(Thread.currentThread().getName()));
        pool.submit(() -> System.out.println(Thread.currentThread().getName()));
        pool.submit(() -> System.out.println(Thread.currentThread().getName()));
        pool.shutdown();
    }
}