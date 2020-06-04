package com.ning.service.config;

import com.ning.service.job.TestJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;

/**
 * @author shenjiang
 * @Description:  简单quartz配置类
 * @Date: 2019/7/12 11:23
 */
//@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail testQuartz1(){
        return JobBuilder.newJob(TestJob.class).withIdentity("testJoin").storeDurably().build();
    }

    @Bean
    public Trigger testQuartzTrigger1(){
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(5).repeatForever();
        return TriggerBuilder.newTrigger().forJob(testQuartz1())
                .withIdentity("testTask1")
                .withSchedule(scheduleBuilder)
                .build();
    }

}
