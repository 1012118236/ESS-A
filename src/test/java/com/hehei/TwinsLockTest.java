package com.hehei;

import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * @author shenjiang
 * @Description:
 * @Date: 2020/8/30 13:09
 */
public class TwinsLockTest {
    @Test
    public void test() throws InterruptedException {



        final Lock lock = new TwinsLock();

        lock.notifyAll();

        class Worker extends Thread{
            @Override
            public void run(){
                while(true){
                    lock.lock();
                    try {
                        TimeUnit.SECONDS.sleep(1);
                        System.out.println(Thread.currentThread().getName());
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }
            }
        }
        for (int i = 0; i < 10 ; i++) {
            Worker worker = new Worker();
            worker.setName("Thread-"+i);
            worker.setDaemon(true);
            worker.start();
        }
        System.out.println(1);
        for (int i = 0; i < 10 ; i++) {
            TimeUnit.SECONDS.sleep(100);
            System.out.println();

        }

    }
}
