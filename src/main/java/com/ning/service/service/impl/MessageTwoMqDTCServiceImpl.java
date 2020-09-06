package com.ning.service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ning.service.entity.Incidentoperation;
import com.ning.service.entity.Message;
import com.ning.service.entity.MessageTwo;
import com.ning.service.mapper.MessageMapper;
import com.ning.service.mapper.MessageTwoMapper;
import com.ning.service.service.IIncidentoperationService;
import com.ning.service.service.IIncidentoperationTwoService;
import com.ning.service.service.ITaskInfoService;
import com.ning.service.vo.ResData;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service("messageTwoDTC")
@Transactional
public class MessageTwoMqDTCServiceImpl implements ITaskInfoService {
    @Autowired
    public KafkaTemplate kafkaTemplate;

    @Autowired
    public MessageMapper messageMapper;
    @Autowired
    public MessageTwoMapper messageTwoMapper;
    @Autowired
    public MessageTwoServiceImpl messageTwoService;
    @Autowired
    public IIncidentoperationService iIncidentoperationService;

    /**
     * B服务定时发送处理失败服务到A
     */
    @Override
    @Transactional
    public void execute(){
        //查询处理失败服务
        QueryWrapper<MessageTwo> messageTwoQueryWrapper = new QueryWrapper<>();
        messageTwoQueryWrapper.lambda().eq(MessageTwo::getProcess,3);
        List<MessageTwo> messageTwos = messageTwoMapper.selectList(messageTwoQueryWrapper);
        LocalDateTime now = LocalDateTime.now();
        //处理失败重置为已发布
        messageTwos.stream().forEach(a->{a.setProcess(1);a.setUpdateTime(now);});
        //kafka发送给A服务
        UpdateWrapper<MessageTwo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(MessageTwo::getProcess,3).set(MessageTwo::getProcess,2).set(MessageTwo::getUpdateTime,now);
        for (MessageTwo messageTwo : messageTwos) {
            kafkaTemplate.send("messageTwo",JSONObject.toJSONString(messageTwo));

        }
        //重置状态为
        messageTwoMapper.update(new MessageTwo(), updateWrapper);

    }

    /**
     * A服务回退失败sql
     * @param record
     */
    @KafkaListener(topics = "messageTwo")
    @Transactional
    public void meterTaskList(ConsumerRecord<?,?> record){
        String value = record.value().toString();
        Message message = JSONObject.parseObject(value, Message.class);
        LambdaQueryWrapper<Message> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Message::getId,message.getId()).eq(Message::getProcess,1);
        Message messageOne = messageMapper.selectOne(wrapper);
        if(messageOne!=null){
            //需要处理
            //从message获取对B服务的操作
            String content = message.getContent();
            HashMap hashMap = JSONObject.parseObject(content, HashMap.class);
            Incidentoperation incidentoperation =JSONObject.parseObject(hashMap.get("A").toString(), Incidentoperation.class);
            ResData resData = iIncidentoperationService.rollback_UpdateIncidentoperationMoney(incidentoperation);

        }


    }

}
