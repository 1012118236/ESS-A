package com.ning.service.service.impl;


import com.ning.service.service.ITaskInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author shenjiang
 * @Description: taskInfo 任务实现类
 * @Date: 2019/7/15 10:14
 */
@Service("taskInfo")
@Transactional
public class TaskInfoServiceImpl implements ITaskInfoService {

    @Override
    public void execute(){
        System.out.println("任务执行开始===============任务执行开始");
        //WebSocket.sendMessage("sj1012118236","收到消息了吗？");
        System.out.println(new Date());
        System.out.println("任务执行结束===============任务执行结束");
    }


}
