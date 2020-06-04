package com.ning.service.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ning.service.entity.Role;
import com.ning.service.vo.ResData;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shenjiang
 * @since 2019-05-28
 */
public interface IRoleService extends IService<Role> {

    List<Role> findRoleAll();

    ResData deleteRoleById(Integer ids);
}
