package com.ning.service.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.service.entity.Menus;
import com.ning.service.entity.Status;
import com.ning.service.entity.User;
import com.ning.service.mapper.MenusMapper;
import com.ning.service.service.IMenusService;
import com.ning.service.utils.UserUtils;
import com.ning.service.vo.MenusVo;
import com.ning.service.vo.ResData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shenjiang
 * @since 2019-05-24
 */
@Service("menusServiceImpl")
public class MenusServiceImpl extends ServiceImpl<MenusMapper, Menus> implements IMenusService {

    @Autowired
    private MenusMapper menusMapper;

    /**
     * 根据用户角色获取菜单
     * @return
     */
    public ResData queryMenusByPermission() {
        String username = UserUtils.getUsername();
        User user = new User();
        user.setUsername(username);
        //用户拥有的权限
        List<Menus> menusByUsername = menusMapper.queryMenusByUsername(user);
        //所有目录
        List<Menus> menusAll = menusMapper.selectList(null);
        return getMenusZtreeByUsername(menusByUsername, menusAll);
        //return menusListMap(menusByUsername, menusMap,menusAll);
    }


    @Override
    public ResData findAllMenusZtree() {
        //用户拥有的权限
        List<Menus> menusByUsername = menusMapper.queryMenusByUsername(new User());
        //所有目录
        List<Menus> menusAll = menusMapper.selectList(null);
        return getMenusZtreeByUsername(menusByUsername, menusAll);


//        Map<String, Object> data = new HashMap<>();
//        List<Menus> menus = menusMapper.selectList(null);
//        Map<Integer, Object> menusMap = new HashMap<>();
//        return menusListMap(menus, menusMap,null);
    }

    private ResData menusListMap(List<Menus> menus, Map<Integer, Object> menusMap, List<Menus> menusAll) {
        //所有菜单结果集
        HashMap<Integer, Menus> map = new HashMap<>();
        if(menusAll!=null){
            for (Menus menus1 : menusAll) {
                map.put(menus1.getId(),menus1);
            }
        }
        //遍历用户目录
        for (int i = 0; i < menus.size(); i++) {
            Menus menus2 = menus.get(i);
            MenusVo menu = new MenusVo(menus2);
            Integer pid = menu.getPid();
            if(pid==null){
                //顶级目录
                menusMap.put(menu.getId(),menu);
            }else{
                if(menusMap.containsKey(pid)){
                    //已添加目录存在父目录
                    MenusVo menu1 = (MenusVo) menusMap.get(pid);
                    List<MenusVo> menusList = menu1.getChildren();
                    menusList.add(menu);
                    menu1.setChildren(menusList);
                    menusMap.put(menu1.getId(),menu1);
                }else{
                    //已添加目录不存在父目录
//                    if(menusAll!=null){
//                        //获取上一层目录
//                        Menus menus1 = map.get(pid);
//                        MenusVo menusVo = new MenusVo(menus1);
//                        List<MenusVo> children = menusVo.getChildren();
//                        children.add(menu);
//                        menusVo.setChildren(children);
//                        if(menus1.getPid()!=null){
//                            menus.add(menus1);
//                        }
//                        for (MenusVo child : children) {
//                            if(menusMap.containsKey(child.getId())){
//                                MenusVo o = (MenusVo)menusMap.get(child.getId());
//                                child = o;
//                                menusMap.remove(child.getId());
//                            }
//                        }
//                        menusMap.put(pid,menusVo);
//                    }else{
                        Boolean bool = false;
                        for (Integer integer : menusMap.keySet()) {
                            MenusVo menus1 = (MenusVo) menusMap.get(integer);
                            bool = menuRecursion(menu, menus1.getChildren());
                            if(bool){
                                break;
                            }
                        }
                        if(!bool){
                            menus.add(menus2);
                        }
                    }
                }
           // }
        }

        List<Object> menusList = new ArrayList<>();
        for (Integer integer : menusMap.keySet()) {
            menusList.add(menusMap.get(integer));
        }
        return new ResData(200,menusList,"");
    }

    /**
     * 菜单递归
     * @param menus
     * @param menusList
     * @return
     */
    private boolean menuRecursion(MenusVo menus, List<MenusVo> menusList) {
        Iterator<MenusVo> iterator = menusList.iterator();
        while (iterator.hasNext()) {
            MenusVo next = iterator.next();
            List<MenusVo> menusList1 = next.getChildren();
            if(next.getId()==menus.getPid()){
                menusList1.add(menus);
                next.setChildren(menusList1);
                return true;
            }
            if(menuRecursion(menus,menusList1)){
                return true;
            }
        }
        return false;
    }

    /**
     * 修改指定菜单
     * @param menus
     * @return
     */
    @Override
    public ResData updateMenu(Menus menus) {
        ResData resData=null;
        try {
            menusMapper.updateMenu(menus);
            resData = new ResData(Status._200, "", "保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            resData= new ResData(Status._500,"","保存失败");
        }

        return resData;
    }

    /**
     * 根据用户权限树集合 整理 菜单树
     * @param menusByUsername
     * @param menusAll
     * @return
     */
    private ResData getMenusZtreeByUsername(List<Menus> menusByUsername, List<Menus> menusAll) {
        HashMap<Integer, MenusVo> menusAllmap = new HashMap<>();
        if(menusAll!=null){
            for (Menus menus1 : menusAll) {
                menusAllmap.put(menus1.getId(),new MenusVo(menus1));
            }
        }
        ArrayList<Integer> menusIdList = new ArrayList<>();
        Map<Integer, Object> menusMap = new HashMap<>();
        for (Menus menus : menusByUsername) {
            MenusVo menusVo = new MenusVo(menus);
            Integer pid = menus.getPid();
            if(pid==null){
                menusIdList.add(menus.getId());
                menusMap.put(menus.getId(),menusVo);
            }else{
                if(menusMap.containsKey(pid)){
                    MenusVo menusVo1 = (MenusVo)menusMap.get(pid);
                    List<MenusVo> children = menusVo1.getChildren();
                    children.add(menusVo);
                    menusVo1.setChildren(children);
                    menusMap.put(pid,menusVo1);
                }else{
                    MenusVo menusVo1 = (MenusVo)menusAllmap.get(pid);
                    Integer pid1 = menusVo1.getPid();
                    MenusVo menusVo2 = (MenusVo)menusMap.get(pid1);
                    List<MenusVo> children = menusVo2.getChildren();
                    for (MenusVo menusVo3 : children) {
                        if(menusVo3.getId()==pid){
                            List<MenusVo> children1 = menusVo3.getChildren();
                            children1.add(menusVo);
                            menusVo3.setChildren(children1);
                        }
                    }

                }

            }
        }
        ArrayList<Object> data = new ArrayList<>();
        for (Integer id : menusIdList) {
            data.add(menusMap.get(id));
        }

        return new ResData(200,data,"");
    }

}
