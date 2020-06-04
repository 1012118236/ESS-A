package com.ning.service.conponent;

import com.alibaba.fastjson.JSONObject;
import com.ning.service.service.impl.UserServiceImpl;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author shenjiang
 * @Description: kafka测试类
 * @Date: 2019/7/10 09:55
 */
@Component
@RestController
public class TestConsumer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Autowired
    private UserServiceImpl userService;

    /**
     * 接收test_topic组的消息消费
     * @param record
     */
    @KafkaListener(topics = "object")
    public void listen(ConsumerRecord<?,?> record){
        System.out.printf("topic = %s, offset = %d, value = %s \n", record.topic(), record.offset(), record.value());
    }

    @RequestMapping(value = "kafkaDemo")
    public void kafkaDemo(){
        List<Map<String, Object>> allUser = userService.findAllUser();
        Object allUserObj = JSONObject.toJSON(allUser);
        kafkaTemplate.send("object",allUserObj.toString());
    }
}
