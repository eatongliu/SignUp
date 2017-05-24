package utils;

import org.junit.Test;

import java.util.Random;

/**
 * Created by acer_liuyutong on 2017/3/20.
 */
public class SimpleTest {

    @Test
    public void test1(){
        boolean b = new Integer(123) == new Integer(123);
        System.out.println(b);
    }
    @Test
    public void test2(){
        for (int i = 1;i<80;i++){
            System.out.print(i+",");
        }
    }
    @Test
    public void test3(){
        Random random = new Random(10);
        Random random2 = new Random(10);
        int num1 = random.nextInt();
        int num2 = random2.nextInt();
        System.out.println(num1);
        System.out.println(num2);
    }
}
