package com.ning.service.service;

import com.ning.service.entity.EmployeeES;
import com.ning.service.vo.ResData;
import org.springframework.stereotype.Service;

/**
 * @author shenjiang
 * @Description:
 * @Date: 2019/7/25 14:46
 */
@Service
public interface IEmployeeSearchService {
    void add(EmployeeES employeeES);

    ResData searchTitle(String title);
}
