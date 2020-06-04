package com.ning.service.controller;

import com.ning.service.entity.EmployeeES;
import com.ning.service.service.IEmployeeSearchService;
import com.ning.service.vo.ResData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

/**
 * @author shenjiang
 * @Description:
 * @Date: 2019/7/25 14:49
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private IEmployeeSearchService employeeSearchService;

    @PostMapping("/save")
    @ResponseBody
    public ResData save(@RequestBody EmployeeES employeeES1){
        EmployeeES employeeES = new EmployeeES(
                employeeES1.getId(),
                "1",
                "沈江",
                "huawei华为HUAWEI手机P30徕卡三摄"+employeeES1.getId(),
                "哈哈哈哈哈啊哈"+employeeES1.getId(),
                "www.baidu.com");
        employeeSearchService.add(employeeES);
        return new ResData(200,"","添加成功");
    }

    @PutMapping("/searchTitle")
    @ResponseBody
    public ResData searchTitle(@RequestBody EmployeeES employeeES1){
        return employeeSearchService.searchTitle(employeeES1.getTitle());
    }

    @Autowired
    public EmployeeController(@Qualifier("employeeSearchServiceImpl") IEmployeeSearchService employeeSearchService){
        this.employeeSearchService = employeeSearchService;

    }
}
