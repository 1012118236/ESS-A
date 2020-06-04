package com.ning.service.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.service.entity.Role;
import com.ning.service.mapper.RoleMapper;
import com.ning.service.service.IRoleService;
import com.ning.service.vo.ResData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shenjiang
 * @since 2019-05-28
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 查询所有角色信息
     * @return
     */
    @Override
    public List<Role> findRoleAll() {
        List<Role> roles = roleMapper.selectList(null);
        return roles;
    }

    /**
     * 根据角色id集合删除角色信息
     * @param id
     * @return
     */
    @Override
    @Transactional
    public ResData deleteRoleById(Integer id) {
        ResData resData = new ResData();
        try {
            List<Map<String, Object>> roleByAllNexr = roleMapper.getRoleByNexrNumber(id);
            if (roleByAllNexr != null && roleByAllNexr.size() == 1) {
                Map<String, Object> stringObjectMap = roleByAllNexr.get(0);
                Long urid = (Long) stringObjectMap.get("urid");
                Long mrid = (Long) stringObjectMap.get("mrid");
                Long prid = (Long) stringObjectMap.get("prid");
                int ur = urid.intValue();
                int mr = mrid.intValue();
                int pr = prid.intValue();
                if (ur != 0l || mr != 0l || pr != 0l) {
                    resData.setCode(500);
                    resData.setMessage("该角色已关联(权限/菜单/用户),请取消关联关系重试！");
                    return resData;
                }
                int count = roleMapper.deleteRoleById(id);
                resData.setCode(200);
                resData.setMessage("删除成功");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            resData.setCode(500);
            resData.setMessage("删除失败!");
        }
        return resData;
    }
}
