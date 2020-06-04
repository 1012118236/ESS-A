package com.ning.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @author shenjiang
 * @Description: quertz 配置
 * @Date: 2019/7/15 09:20
 */
@Configuration
@EnableScheduling
public class QuartzConfiguration {

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(){
        return new SchedulerFactoryBean();
    }
}
