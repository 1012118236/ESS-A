package com.ning.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ning.service.entity.Role;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author shenjiang
 * @since 2019-05-28
 */
public interface RoleMapper extends BaseMapper<Role> {

   public List<Role>  getRoleByUserId(int id);
    /**
     * 和3者的关联数量
     * @param id
     * @return
     */
   public List<Map<String,Object>> getRoleByNexrNumber(Integer id);

   public int deleteRoleById(Integer id);

}
