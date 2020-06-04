package com.ning.service.job;

import com.ning.service.entity.ScheduleJob;
import com.ning.service.utils.SpringUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author shenjiang
 * @Description: 任务调度Job 执行对应任务的方法
 * @Date: 2019/7/15 09:50
 */
public class QuartzFactory implements Job {

    private static Logger log = LoggerFactory.getLogger(QuartzFactory.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("QuartzFactory开始执行");
        //获取调度数据
        ScheduleJob scheduleJob = (ScheduleJob) jobExecutionContext.getMergedJobDataMap().get("scheduleJob");
        //获取对应的bean
        Object object = SpringUtil.getBean(scheduleJob.getSpringId());
        try {
            Method method = object.getClass().getMethod(scheduleJob.getMethodName());
            method.invoke(object);
            log.info("正在执行任务的对象："+object.toString()+"；   方法："+scheduleJob.getMethodName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("QuartzFactory执行结束");
    }
}
