package com.ning.service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.service.entity.Incidentoperation;
import com.ning.service.entity.Message;
import com.ning.service.mapper.IncidentoperationMapper;
import com.ning.service.mapper.MessageMapper;
import com.ning.service.service.IIncidentoperationService;
import com.ning.service.vo.ResData;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shenjiang
 * @since 2020-09-05
 */
@Service
public class IncidentoperationServiceImpl extends ServiceImpl<IncidentoperationMapper, Incidentoperation> implements IIncidentoperationService {

    @Autowired
    private IncidentoperationMapper incidentoperationMapper;
    @Autowired
    private MessageMapper messageMapper;

    /**
     * 沈江给渠永泉发1000元工资
     * @return
     */
    @Override
    @Transactional
    public ResData updateIncidentoperationMoney() throws Exception {
            int operMoney = 52000;
            Incidentoperation incidentoperation = getIncidentoperationByUsernameName("沈江");
            ResData resData = updateMoney(operMoney, incidentoperation);
            if(resData.code!=200){
                return resData;
            }
            Message message = new Message();
            message.setId(UUID.randomUUID().toString());
            message.setType("加减");
            message.setProcess(0);
            Incidentoperation _incidentoperation = new Incidentoperation();
            _incidentoperation.setUsername("渠永泉");
            _incidentoperation.setMoney(0-operMoney);
            Map<String, Object> map = new HashMap<>();
            incidentoperation.setMoney(operMoney);
            map.put("A",incidentoperation);
            map.put("B",_incidentoperation);
            message.setContent(JSONObject.toJSONString(map));
            message.setCreateTime(LocalDateTime.now());
            message.setUpdateTime(LocalDateTime.now());
            int insert = messageMapper.insert(message);
            return new ResData(200,"","操作已提交");
    }

    /**
     * 沈江操作回退
     * @param incidentoperation
     * @return
     */
    @Override
    public ResData rollback_UpdateIncidentoperationMoney(Incidentoperation incidentoperation_) {
        Incidentoperation incidentoperation = getIncidentoperationByUsernameName(incidentoperation_.getUsername());
        return updateMoney(0-incidentoperation_.getMoney(),incidentoperation);
    }

    /**
     *
     * @param operMoney 操作的金额
     * @param incidentoperation_ 需要操作的用户
     * @return
     */
    private ResData updateMoney(int operMoney,Incidentoperation incidentoperation) {
        System.out.println(incidentoperation.getUsername()+"账户当前金额为："+incidentoperation.getMoney());
        int userMoney = 0;
        if((userMoney=incidentoperation.getMoney()+operMoney)<0){
            return new ResData(201,"","账户余额不足！");
        }
        UpdateWrapper<Incidentoperation> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(Incidentoperation::getUsername,incidentoperation.getUsername())
                .eq(Incidentoperation::getMoney,incidentoperation.getMoney());
        incidentoperation.setMoney(userMoney);
        System.out.println(incidentoperation.getUsername()+"账户操作："+operMoney);
        int update = incidentoperationMapper.update(incidentoperation,updateWrapper);
        Incidentoperation incidentoperation1 = getIncidentoperationByUsernameName(incidentoperation.getUsername());
        System.out.println(incidentoperation.getUsername()+"账户当前金额为："+incidentoperation.getMoney());
        return new ResData(200,"","操作已提交");
    }

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    private Incidentoperation getIncidentoperationByUsernameName(String username) {
        QueryWrapper<Incidentoperation> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Incidentoperation::getUsername,username);
        return incidentoperationMapper.selectOne(wrapper);
    }
}
