package com.ning.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ning.service.entity.User;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author shenjiang
 * @since 2019-05-22
 */
public interface UserMapper extends BaseMapper<User> {

    List<Map<String,Object>> findUserAll();

    Integer updateIpTimeByid(User user);
}
