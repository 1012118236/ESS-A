package com.ning.service.controller;


import com.ning.service.entity.Role;
import com.ning.service.service.IRoleService;
import com.ning.service.vo.ResData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author shenjiang
 * @since 2019-05-28
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    /**
     * 获取所有角色信息
     * @return
     */
    @PostMapping(value = "/findRoleAll")
    @ResponseBody
    public ResData findRoleAll(){
        List<Role> roleAll = roleService.findRoleAll();
        ResData resData = new ResData(200, roleAll, "");
        return resData;
    }

    /**
     * 根据角色id删除角色
     * @param map
     * @return
     */
    @PostMapping(value="/deleteRoleById")
    @ResponseBody
    public ResData deleteRoleById(@RequestBody Map<String,Object> map){
        return roleService.deleteRoleById(Integer.parseInt(map.get("roleids").toString()));
    }

}

