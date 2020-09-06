package com.ning.service.controller;

import com.ning.service.service.IIncidentoperationService;
import com.ning.service.vo.ResData;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 分布式事务实现
 */
@RestController()
@RequestMapping("/dtc")
public class DistributedTransactionController {

    @Autowired
    private IIncidentoperationService iIService;

    /**
     *  转账实现
     * @return
     */
    @RequestMapping("/transferAccounts")
    @ResponseBody
    public ResData transferAccounts(){

        try {
            return iIService.updateIncidentoperationMoney();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResData(500,"","系统错误");
        }
    }
}
