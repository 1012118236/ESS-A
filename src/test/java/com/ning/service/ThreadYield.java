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
        Integer[] str = {1,2,3,4};
        List<Integer> collect = Arrays.stream(str).collect(Collectors.toList());
        System.out.println(1);
    }


}
