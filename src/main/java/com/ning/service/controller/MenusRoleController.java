package com.ning.service.controller;


import com.ning.service.entity.MenusRole;
import com.ning.service.service.IMenusRoleService;
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
@RequestMapping("/menusRole")
public class MenusRoleController {

    @Autowired
    private IMenusRoleService menusRoleService;

    /**
     * 根据角色ID查询菜单id
     * @return
     */
    @RequestMapping("/findMenusByRoleId")
    @ResponseBody
    public ResData findMenusIdByRoleId(@RequestBody MenusRole menusRole){
        return menusRoleService.findMenusIdByRoleId(menusRole.getRoleId());
    }

    /**
     * 保存菜单角色关系
     * @param map
     * @return
     */
    @PostMapping(value = "/saveMenusRole")
    @ResponseBody
    public ResData saveMenusRole(@RequestBody Map<String,Object> map){
        return  menusRoleService.saveMenusRole(map);
    }
}

