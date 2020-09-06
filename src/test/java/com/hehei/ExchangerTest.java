package com.hehei;

import java.util.concurrent.Exchanger;

public class ExchangerTest {

    private     static  final Exchanger<String> exchanger = new Exchanger();


    public static void main(String[] args) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                String str = "呵呵";
                try {
                    System.out.println("等待1");
                    String exchange = exchanger.exchange(str);
                    System.out.println(exchange);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                super.run();
            }
        };
        thread.start();
        Thread thread1 = new Thread() {
            @Override
            public void run() {
                String str = "哈哈";
                try {
                    System.out.println("加载。。。");
                    Thread.sleep(1000);
                    System.out.println("加载完成。。。");
                    String exchange = exchanger.exchange(str);
                    System.out.println(exchange);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                super.run();
            }
        };
        thread1.start();
    }


}
