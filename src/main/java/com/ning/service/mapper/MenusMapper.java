package com.ning.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ning.service.entity.Menus;
import com.ning.service.entity.User;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author shenjiang
 * @since 2019-05-24
 */
public interface MenusMapper extends BaseMapper<Menus> {

    public List<Menus> queryMenusByUsername(User user);

    public List<Map<String,Object>> queryMenusByUsernameMap(String username);

    void updateMenu(Menus menus);
}
