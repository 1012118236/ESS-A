package com.ning.service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.service.entity.Incidentoperation;
import com.ning.service.entity.IncidentoperationTwo;
import com.ning.service.entity.Message;
import com.ning.service.mapper.IncidentoperationTwoMapper;
import com.ning.service.mapper.MessageTwoMapper;
import com.ning.service.service.IIncidentoperationTwoService;
import com.ning.service.vo.ResData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
public class IncidentoperationTwoServiceImpl extends ServiceImpl<IncidentoperationTwoMapper, IncidentoperationTwo> implements IIncidentoperationTwoService {

    @Autowired
    private IncidentoperationTwoMapper incidentoperationTwoMapper;
    @Autowired
    private MessageTwoMapper messageTwoMapper;

    /**
     * 渠永泉账户+1000元工资
     * @param user  操作人
     * @return
     */
    @Override
    @Transactional
    public ResData updateIncidentoperationMoney(IncidentoperationTwo user) throws Exception {
        int operMoney = user.getMoney();
        QueryWrapper<IncidentoperationTwo> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(IncidentoperationTwo::getUsername,user.getUsername());
        //查询被操作用户
        IncidentoperationTwo incidentoperationTwo = incidentoperationTwoMapper.selectOne(wrapper);
        System.out.println(incidentoperationTwo.getUsername()+"账户当前金额为："+incidentoperationTwo.getMoney());
        int userMoney = 0;
        if((userMoney=incidentoperationTwo.getMoney()+operMoney)<0){
            throw new Exception();
        }
        UpdateWrapper<IncidentoperationTwo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(IncidentoperationTwo::getUsername,incidentoperationTwo.getUsername())
                .eq(IncidentoperationTwo::getMoney,incidentoperationTwo.getMoney());
        incidentoperationTwo.setMoney(userMoney);
        System.out.println(incidentoperationTwo.getUsername()+"账户操作："+operMoney);
        int update = incidentoperationTwoMapper.update(incidentoperationTwo,updateWrapper);
        IncidentoperationTwo incidentoperation1 = incidentoperationTwoMapper.selectOne(wrapper);
        System.out.println(incidentoperationTwo.getUsername()+"账户当前金额为："+incidentoperationTwo.getMoney());

        return new ResData(200,"","操作已提交");
    }

}
