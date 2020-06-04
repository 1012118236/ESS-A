package com.ning.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.ning.service.entity.ScheduleJob;
import com.ning.service.job.QuartzFactory;
import com.ning.service.mapper.ScheduleJobMapper;
import com.ning.service.service.ITaskService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author shenjiang
 * @Description: 任务调度配置  从数据库获取任务信息配置定时任务
 * @Date: 2019/7/15 09:53
 */
@Service
@Transactional
public class TaskServiceImpl implements ITaskService {

    private static Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Autowired
    private ScheduleJobMapper scheduleJobMapper;

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    /**
     * 执行所有任务
     */
    @Override
    public void timingTask(){
        QueryWrapper<ScheduleJob> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(ScheduleJob::getJobStatus,1);
        List<ScheduleJob> scheduleJobs = scheduleJobMapper.selectList(wrapper);
        if(scheduleJobs != null){
            scheduleJobs.forEach(this::execute);
        }
    }

    /**
     * 删除所有任务
     */
    @Override
    public void deleteTask(){
        QueryWrapper<ScheduleJob> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(ScheduleJob::getJobStatus,1);
        List<ScheduleJob> scheduleJobs = scheduleJobMapper.selectList(wrapper);
        if(scheduleJobs != null){
            scheduleJobs.forEach(this::delete);
        }
    }

    //添加任务
    public void execute(ScheduleJob scheduleJob) {
        try {
            //声明调度器
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            //添加触发调度名称
            TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName());
            //设置触发时间
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());
            //触发建立
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(cronScheduleBuilder).build();
            //添加作业名称
            JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName());
            //建立作业
            JobDetail jobDetail = JobBuilder.newJob(QuartzFactory.class).withIdentity(jobKey).build();
            //传入调度数据，在QuartzFactory中需要使用
            jobDetail.getJobDataMap().put("scheduleJob",scheduleJob);
            //调度作业
            scheduler.scheduleJob(jobDetail,trigger);
            log.info(scheduleJob.getJobName()+":该任务已添加");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    //删除任务
    public void delete(ScheduleJob scheduleJob){
        try {
            //获取调度器
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            //获取任务key
            JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName());
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if(jobDetail!=null){
                scheduler.deleteJob(jobKey);
                log.info(scheduleJob.getJobName()+"：该任务已删除！");
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }


    }

}
