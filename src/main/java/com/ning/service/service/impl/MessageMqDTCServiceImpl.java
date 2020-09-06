package com.ning.service.service.impl;


import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ning.service.entity.Incidentoperation;
import com.ning.service.entity.IncidentoperationTwo;
import com.ning.service.entity.Message;
import com.ning.service.entity.MessageTwo;
import com.ning.service.mapper.MessageMapper;
import com.ning.service.mapper.MessageTwoMapper;
import com.ning.service.service.IIncidentoperationTwoService;
import com.ning.service.service.ITaskInfoService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author shenjiang
 * @Description: 消息事件
 * @Date: 2019/7/15 10:14
 */
@Service("messageMqDTC")
@Transactional
public class MessageMqDTCServiceImpl implements ITaskInfoService {

    @Autowired
    public KafkaTemplate kafkaTemplate;

    @Autowired
    public MessageMapper messageMapper;
    @Autowired
    public MessageTwoMapper messageTwoMapper;
    @Autowired
    public MessageTwoServiceImpl messageTwoService;
    @Autowired
    public IIncidentoperationTwoService iIncidentoperationTwoService;

    /**
     * A服务定时发送新增的message给B服务 并重置message为1已发布
     */
    @Override
    @Transactional
    public void execute(){
        QueryWrapper<Message> messageQueryWrapper = new QueryWrapper<>();
        messageQueryWrapper.lambda().eq(Message::getProcess,0);
        List<Message> messages = messageMapper.selectList(messageQueryWrapper);
        messages.stream().forEach(a->{a.setProcess(1);a.setUpdateTime(LocalDateTime.now());});
        UpdateWrapper<Message> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(Message::getProcess,0).set(Message::getProcess,1).set(Message::getUpdateTime,LocalDateTime.now());
         messageMapper.update(new Message(), updateWrapper);
        for (Message message : messages) {
            kafkaTemplate.send("message",JSONObject.toJSONString(message));

        }
    }

    /**
     * B服务接收message并处理 并重置为 2成功 3失败
     * @param record
     */
    @KafkaListener(topics = "message")
    @Transactional
    public void meterTaskList(ConsumerRecord<?,?> record){
        String value = record.value().toString();
        MessageTwo messageTwo = JSONObject.parseObject(value, MessageTwo.class);
        MessageTwo messageTwo1 = messageTwoMapper.selectById(messageTwo.getId());

        if(messageTwo1==null&&messageTwo != null) {
            int insert = messageTwoMapper.insert(messageTwo);
            String type = messageTwo.getType();
            //从message获取对B服务的操作
            String content = messageTwo.getContent();
            HashMap hashMap = JSONObject.parseObject(content, HashMap.class);
            IncidentoperationTwo incidentoperationTwo =JSONObject.parseObject(hashMap.get("B").toString(), IncidentoperationTwo.class);
            try {
                iIncidentoperationTwoService.updateIncidentoperationMoney(incidentoperationTwo);
                messageTwo.setProcess(2);
            } catch (Exception e) {
                messageTwo.setProcess(3);
                System.out.println("操作失败！");
                e.printStackTrace();
            }
            messageTwo.setUpdateTime(LocalDateTime.now());
             messageTwoMapper.updateById(messageTwo);
        }
    }

}
