package com.ning.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.service.entity.Permission;
import com.ning.service.mapper.PermissionMapper;
import com.ning.service.service.IPermissionService;
import com.ning.service.utils.UserUtils;
import com.ning.service.vo.ResData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shenjiang
 * @since 2019-05-29
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public ResData findAllPermissionWhatKey() {
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Permission::getStatus,0);
        List<Permission> permissionsAll = permissionMapper.selectList(wrapper);
        ArrayList<Object> data = new ArrayList<>();
        for (Permission permission : permissionsAll) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("key",permission.getId());
            map.put("label",permission.getName());
            data.add(map);
        }
        return new ResData(200,data,"");
    }

    @Override
    public ResData findAllPermission() {
        List<Permission> permissionsAll = permissionMapper.findAll();
        return new ResData(200,permissionsAll,"");
    }

    @Override
    public ResData saveOrUpdatePermission(Permission permission) {
        try {
            boolean b = this.saveOrUpdate(permission);
            QueryWrapper<Permission> wrapper = new QueryWrapper<Permission>();
            wrapper.lambda().eq(Permission::getName,permission.getName());

            return new ResData(200,permissionMapper.selectOne(wrapper),"保存成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResData(500,"","保存失败!");
        }
    }

    @Override
    public ResData findPermissionByUser() {
        String username = UserUtils.getUsername();
        List<Permission> permissions = permissionMapper.findPermissionByUser(username);
        return new ResData(200,permissions,"");
    }
}
