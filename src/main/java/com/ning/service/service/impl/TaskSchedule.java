package com.ning.service.service.impl;


import com.ning.service.service.ITaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * @author shenjiang
 * @Description: 启动自动运行任务调度
 * @Date: 2019/7/15 10:05
 */
@Component
public class TaskSchedule implements CommandLineRunner {
    private static Logger logger = LoggerFactory.getLogger(TaskSchedule.class);

    @Autowired
    private ITaskService taskService;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostConstruct
    public void delRedis(){
        logger.info("redis缓存清空中....");
        Set keys = redisTemplate.keys("*");
        redisTemplate.delete(keys);
        logger.info("redis缓存清空完成....");

    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("任务调度初始化开始");
        taskService.timingTask();
        logger.info("任务调度初始化结束");
    }
}
