package thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by acer_liuyutong on 2017/5/12.
 */
public class ConcurrentTest{
    public static final Logger logger = LoggerFactory.getLogger(ConcurrentTest.class);
    public static void main(String[] args) {
        ShareData shareData = ShareData.getInstance();
        ExecutorService pool = new ThreadPoolExecutor(3, 5, 50, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(20));
        pool.submit(()->{
            for (int i = 0; i < 20 ; i++) {
                shareData.increment();
            }
        });
        pool.submit(()->{
            for (int i = 0; i < 20 ; i++) {
                shareData.decrement();
            }
        });
        pool.shutdown();
    }
}

class ShareData
{
    private int number = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private static ShareData shareData = null;

    private ShareData() {}

    static synchronized ShareData getInstance(){
        if(shareData == null){
            shareData = new ShareData();
        }
        return shareData;
    }

    void increment(){
        lock.lock();
        try {
            while(number != 0) {
                condition.await();//this.wait();
            }
            ++number;
            Thread.sleep(10000);
            System.out.println(Thread.currentThread().getName()+"\t"+number);
            condition.signalAll();//this.notifyAll();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }

    void decrement(){
        lock.lock();
        try {
            while(number == 0) {
                condition.await();//this.wait();
            }
            --number;
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName()+"\t"+number);
            condition.signalAll();//this.notifyAll();
        } catch (Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }

}
