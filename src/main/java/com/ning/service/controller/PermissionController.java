package com.ning.service.controller;


import com.ning.service.entity.Permission;
import com.ning.service.service.IPermissionService;
import com.ning.service.vo.ResData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author shenjiang
 * @since 2019-05-29
 */
@Controller
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private IPermissionService permissionService;

    /**
     * 查询全部权限以key label的形式
     * @return
     */
    @PostMapping(value = "/findAllPermissionWhatKey")
    @ResponseBody
    public ResData findAllPermissionWhatKey(){
        return permissionService.findAllPermissionWhatKey();
    }

    /**
     * 查询全部权限
     * @return
     */
    @PostMapping(value = "/findAllPermission")
    @ResponseBody
    public ResData findAllPermission(){
        return permissionService.findAllPermission();
    }


    /**
     * 查询用户的全部权限
     * @return
     */
    @PostMapping(value = "/findPermissionByUser")
    @ResponseBody
    public ResData findPermissionByUser(){
        return permissionService.findPermissionByUser();
    }



    @PostMapping(value = "/saveOrUpdatePermission")
    @ResponseBody
    public ResData saveOrUpdatePermission(@RequestBody Permission permission){
        return permissionService.saveOrUpdatePermission(permission);
    }
}

