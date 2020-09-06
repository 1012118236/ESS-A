package com.ning.service.service;

import com.ning.service.entity.Incidentoperation;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ning.service.vo.ResData;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shenjiang
 * @since 2020-09-05
 */
public interface IIncidentoperationService extends IService<Incidentoperation> {

    ResData updateIncidentoperationMoney() throws Exception;

    ResData rollback_UpdateIncidentoperationMoney(Incidentoperation incidentoperation );
}
