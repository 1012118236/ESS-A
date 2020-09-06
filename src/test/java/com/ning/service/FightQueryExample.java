package com.ning.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 生成3个线程并执行，调用三个线程的join方法，调用某个线程的join方法会使调用者等待，知道被调用者释放中断或执行完毕，但是不会影响到其他线程，
 * 当3个线程join执行完毕，获取3个线程的结果并合并。
 *
 * 线程池尚未执行的线程，无法调用其join方法。（join需要被调用线程启动才能实现其作用）
 *
 * @author shenjiang
 * @Description:线程join方法使用
 * @Date: 2019/3/15 15:45
 */
public class FightQueryExample {
    private static List<String> fightCompany = Arrays.asList("CSA","CEA","HNA","sdf","dsfsd","asdas");

    public static void main(String[] args) throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        List<String> result = search("SH", "BJ");
        System.out.println("------------------result-----------");
        result.forEach(System.out::println);
    }

    /**
     * 通过join实现子线程完成前主线程阻塞
     * @param original
     * @param dest
     * @return
     * @throws Exception
     */
    private static List<String> search(String original, String dest) throws Exception {
        final List<String> result = new ArrayList<>();
        List<FightQueryTask> tasks = fightCompany.stream().map(f -> createSearchTask(f, original, dest)).collect(Collectors.toList());

        tasks.forEach(Thread::start);
        tasks.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        tasks.stream().map(FightQuery::get).forEach(result::addAll);
        return result;
    }

    /**
     * 通过计数器实现子线程完成前主线程阻塞
     * @param original
     * @param dest
     * @param countDownLatch
     * @return
     * @throws Exception
     */
    private static List<String> search(String original, String dest, CountDownLatch countDownLatch) throws Exception {
        final List<String> result = new ArrayList<>();
        List<FightQueryTask> tasks = fightCompany.stream().map(f -> createSearchTask(f, original, dest,countDownLatch)).collect(Collectors.toList());
        tasks.forEach(Thread::start);
        countDownLatch.await();
        tasks.stream().map(FightQuery::get).forEach(result::addAll);
        return result;
    }


    private static FightQueryTask createSearchTask(String fight, String original, String dest,CountDownLatch countDownLatch) {
        return new FightQueryTask(fight,original,dest,countDownLatch);
    }
    private static FightQueryTask createSearchTask(String fight, String original, String dest) {
        return new FightQueryTask(fight,original,dest);
    }



}
