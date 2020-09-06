package com.ning.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author shenjiang
 * @Description:
 * @Date: 2019/3/14 14:28
 */
public class ThreadYield {
    public static void main(String[] args) {
//        Integer[] str = {1,2,3,4};
//        List<Integer> collect = Arrays.stream(str).collect(Collectors.toList());
//        System.out.println(1);
        System.out.println("1111"+Thread.currentThread().isDaemon());
        Thread thread = new Thread(()->{
            Thread thread1 = new Thread();
            System.out.println("我是"+ thread1.isDaemon());
            thread1.setDaemon(false);
            System.out.println("我是"+ thread1.isDaemon());
            while (true){
                try {
                    Thread.sleep(1000);
                    System.out.println(123);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println(thread.isDaemon());
        thread.setDaemon(true);
        System.out.println(thread.isDaemon());
        thread.start();
        Thread thread1 = new Thread(()->{
            try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread1.start();

    }


}
