package com.ning.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.service.entity.PermissionRole;
import com.ning.service.mapper.PermissionRoleMapper;
import com.ning.service.service.IPermissionRoleService;

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
public class PermissionRoleServiceImpl extends ServiceImpl<PermissionRoleMapper, PermissionRole> implements IPermissionRoleService {

    @Autowired
    private PermissionRoleMapper permissionRoleMapper;

    @Override
    public ResData findPermissionByRoleId(Integer id) {
        QueryWrapper<PermissionRole> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(PermissionRole::getRoleId,id);
        List<PermissionRole> permissionRoles = permissionRoleMapper.selectList(wrapper);
        ArrayList<Object> permissionId = new ArrayList<>();
        for (PermissionRole permissionRole : permissionRoles) {
            permissionId.add(permissionRole.getPermissionId());
        }
        return new ResData(200,permissionId,"");
    }

    @Override
    public ResData savePermissionRole(Map<String, Object> map) {
        ArrayList permissionId = (ArrayList)map.get("permissionId");
        HashSet<Integer> permissionIdSet = new HashSet<>();
        Integer roleId = (Integer)map.get("roleId");
        QueryWrapper<PermissionRole> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(PermissionRole::getRoleId,roleId);
        int delete = permissionRoleMapper.delete(wrapper);
        if(permissionId!=null){
            for (Object permissionid : permissionId) {
                permissionIdSet.add((Integer) permissionid);
            }
            for (Integer permissionid : permissionIdSet) {
                PermissionRole permissionRole = new PermissionRole(roleId, permissionid);
                permissionRoleMapper.insert(permissionRole);
            }
        }
        return null;
    }
}
