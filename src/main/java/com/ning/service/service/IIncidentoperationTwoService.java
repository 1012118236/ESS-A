package com.ning.service.service;

import com.ning.service.entity.IncidentoperationTwo;
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
public interface IIncidentoperationTwoService extends IService<IncidentoperationTwo> {
    ResData updateIncidentoperationMoney(IncidentoperationTwo user) throws Exception;
}
