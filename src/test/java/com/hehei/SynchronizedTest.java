package com.hehei;

/**
 * @author shenjiang
 * @Description:
 * @Date: 2020/8/28 09:36
 */
public class SynchronizedTest {
    int a = 0;
    public synchronized void writer(){
        a++;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public synchronized void reader(){
        int i = a;
        System.out.println(i);
    }
}
