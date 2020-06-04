package com.ning.service.controller;


import com.ning.service.entity.Menus;
import com.ning.service.service.IMenusService;
import com.ning.service.vo.ResData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器 菜单栏
 * </p>
 *
 * @author shenjiang
 * @since 2019-05-24
 */
@RestController
@RequestMapping("/menus")
public class MenusController {

    @Autowired
    @Qualifier("menusServiceImpl")
    private IMenusService menusService;

    /**
     * 根据用户获取菜单menus
     * @return
     */
    @RequestMapping("/queryMenus")
    @ResponseBody
    public ResData queryMenusByPermission(){
        return menusService.queryMenusByPermission();
    }

    /**
     * 修改指定菜单内容
     * @param menus
     * @return
     */
    @RequestMapping("/updateMenu")
    @ResponseBody
    public ResData updateMenu(@RequestBody Menus menus){
        return menusService.updateMenu(menus);
    }

    /**
     *查询所有菜单以Ztree树结构形式返回
     */
    @RequestMapping("/findAllMenusZtree")
    @ResponseBody
    public ResData findAllMenusZtree(){
        return  menusService.findAllMenusZtree();
    }

    /**
     * 查询所有菜单
     * @return
     */
    @RequestMapping("/findAllMenus")
    @ResponseBody
    public ResData findAllMenus(){
        List<Menus> menusList = menusService.list();
        ArrayList<Object> data = new ArrayList<>();
        for (Menus menus : menusList) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("key",menus.getId());
            map.put("label",menus.getMenuName());
            data.add(map);
        }
        return new ResData(200,data,"");
    }

}

