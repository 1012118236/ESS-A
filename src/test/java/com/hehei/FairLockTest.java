package com.hehei;

import lombok.SneakyThrows;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author shenjiang
 * @Description:  测试  公平锁和非公平锁都是可重入锁。公平锁lock释放后再次获得锁会加入到阻塞队列尾部。非公平锁释放后所有阻塞线程重新竞争锁。
 * @Date: 2020/8/31 10:57
 */
public class FairLockTest {
   static ReentrantLock2 lock = new ReentrantLock2(true);
   static ReentrantLock2 unlock = new ReentrantLock2(false);

    public static void main(String[] args) throws InterruptedException {

        lockTest(unlock);
        unlock.lock();
        Condition condition = unlock.newCondition();
        condition.await();
    }

    private static void lockTest(ReentrantLock2 lock) {
        new Job("1", lock).start();
        new Job("2", lock).start();
        new Job("3", lock).start();
        new Job("4", lock).start();
        new Job("5", lock).start();
    }


    private static class Job extends Thread {
        private ReentrantLock2 lock;

        public Job(String name, ReentrantLock2 lock) {
            super(name);
            this.lock = lock;
        }

        @SneakyThrows
        @Override
        public void run() {
            lock.lock();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.lock();

            System.out.println(Thread.currentThread().getName()+",WaitingBy"+Arrays.toString(lock.getQueuedThreads().toArray()));
            System.out.println(Thread.currentThread().getName()+",WaitingBy"+Arrays.toString(lock.getQueuedThreads().toArray()));
            lock.unlock();

            lock.unlock();

        }
    }
}
