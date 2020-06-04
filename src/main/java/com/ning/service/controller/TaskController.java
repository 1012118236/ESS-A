package com.ning.service.controller;

import com.alibaba.fastjson.JSONObject;
import com.ning.service.service.impl.TaskServiceImpl;
import com.ning.service.utils.RedisUtil;
import com.ning.service.websocket.WebSocket;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author shenjiang
 * @Description:
 * @Date: 2019/5/23 15:16
 */
@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskServiceImpl taskService;


    @Resource
    private RedisUtil redisUtil;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @GetMapping
    public String listTasks() {
        return "任务列表";
    }

    /**
     * 增加电表任务
     * @return
     */
    @PostMapping
    public String newTasks() {

        //redisUtil.set("test",123);
        stringRedisTemplate.opsForValue().set("test","100",60*10,TimeUnit.SECONDS);
        int onLineCount = new WebSocket().getOnLineCount();
        redisTemplate.opsForValue().set("test","123",60*10,TimeUnit.SECONDS);
        ArrayList<Object> meterTaskList = new ArrayList<>();
        Object allUserObj = JSONObject.toJSON(meterTaskList);
        kafkaTemplate.send("meterTaskList",allUserObj.toString());
        return "新增了两个任务";
    }

    @KafkaListener(topics = "meterTaskList")
    public void meterTaskList(ConsumerRecord<?,?> record){
        Object test = redisTemplate.opsForValue().get("test");
        String test1 = stringRedisTemplate.opsForValue().get("test");
        System.out.println("redisUtil:"+test.toString());
        System.out.println("stringRedisTemplate:"+test1.toString());
        taskService.deleteTask();
    }

    @PutMapping("/{taskId}")
    public String updateTasks(@PathVariable("taskId") Integer id) {
        return "更新了一下id为:" + id + "的任务";
    }

    @DeleteMapping("/{taskId}")
    public String deleteTasks(@PathVariable("taskId") Integer id) {
        return "删除了id为:" + id + "的任务";
    }

}

