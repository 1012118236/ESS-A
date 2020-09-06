package com.ning.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author shenjiang
 * @Description:读取第三方数据的接口
 * @Date: 2019/3/14 11:03
 */
public class FightQueryTask extends Thread implements FightQuery{

    private final String origin;

    private final String destination;

    private final List<String> flightList = new ArrayList<>();

    private CountDownLatch countDownLatch;

    public FightQueryTask(String airline, String origin, String destination, CountDownLatch countDownLatch){
        super("["+airline+"]");
        this.origin = origin;
        this.destination = destination;
        this.countDownLatch = countDownLatch;
    }

    public FightQueryTask(String airline, String origin, String destination){
        super("["+airline+"]");
        this.origin = origin;
        this.destination = destination;
    }

    @Override
    public void run() {
        System.out.printf("%s-query from %s to %s \n",getName(),origin,destination);
        //单个生成一个随机数
        int randomVal = ThreadLocalRandom.current().nextInt(10);
        try {
            TimeUnit.SECONDS.sleep(randomVal);
            this.flightList.add(getName() + "-" + randomVal);
            System.out.printf("The Fight:%s list query successful\n",getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if(null!=countDownLatch){
                countDownLatch.countDown();
            }
        }
    }

    @Override
    public List<String> get() {
        return this.flightList;
    }
}
