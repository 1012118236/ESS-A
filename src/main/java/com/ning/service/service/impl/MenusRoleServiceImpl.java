package com.ning.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.service.entity.MenusRole;
import com.ning.service.mapper.MenusRoleMapper;
import com.ning.service.service.IMenusRoleService;
import com.ning.service.vo.ResData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shenjiang
 * @since 2019-06-14
 */
@Service
public class MenusRoleServiceImpl extends ServiceImpl<MenusRoleMapper, MenusRole> implements IMenusRoleService {

    @Autowired
    private MenusRoleMapper menusRoleMapper;

    /**
     * 根据角色ID查询菜单id
     * @param roleId
     * @return
     */
    @Override
    public ResData findMenusIdByRoleId(Integer roleId) {
        QueryWrapper<MenusRole> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(MenusRole::getRoleId, roleId);
        List<MenusRole> menusRoles = menusRoleMapper.selectList(wrapper);
        ArrayList<Object> menusId = new ArrayList<>();
        for (MenusRole menusRole : menusRoles) {
            menusId.add(menusRole.getMenusId());
        }
        return new ResData(200,menusId,"");
    }

    @Override
    public ResData saveMenusRole(Map<String, Object> map) {
        ArrayList menusId = (ArrayList)map.get("menusId");
        HashSet<Integer> menusIdSet = new HashSet<>();
        Integer roleId = (Integer)map.get("roleId");
        QueryWrapper<MenusRole> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(MenusRole::getRoleId,roleId);
        int delete = menusRoleMapper.delete(wrapper);
        if(menusId!=null){
            for (Object menusid : menusId) {
                menusIdSet.add((Integer) menusid);
            }
            for (Integer menusid : menusIdSet) {
                MenusRole menusRole = new MenusRole(menusid,roleId);
                menusRoleMapper.insert(menusRole);
            }
        }
        return new ResData(200,"","");
    }
}
