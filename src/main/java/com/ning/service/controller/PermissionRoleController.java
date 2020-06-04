package com.ning.service.controller;


import com.ning.service.entity.Role;
import com.ning.service.service.IPermissionRoleService;
import com.ning.service.vo.ResData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author shenjiang
 * @since 2019-06-14
 */
@Controller
@RequestMapping("/permissionRole")
public class PermissionRoleController {


    @Autowired
    private IPermissionRoleService permissionRoleService;

    /**
     * 根据角色ID查询角色权限ID
     * @param role
     * @return
     */
    @PostMapping(value = "/findPermissionByRoleId")
    @ResponseBody
    public ResData findPermissionByRoleId(@RequestBody Role role){
        return permissionRoleService.findPermissionByRoleId(role.getId());
    }

    @PostMapping(value = "/savePermissionRole")
    @ResponseBody
    public ResData savePermissionRole(@RequestBody Map<String,Object> map){
        return  permissionRoleService.savePermissionRole(map);
    }

}

