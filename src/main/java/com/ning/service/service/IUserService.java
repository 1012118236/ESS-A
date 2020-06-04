package com.ning.service.service;



import com.baomidou.mybatisplus.extension.service.IService;
import com.ning.service.entity.User;
import com.ning.service.vo.ResData;
import com.ning.service.vo.UserVo;


import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shenjiang
 * @since 2019-05-22
 */
public interface IUserService extends IService<User> {

    /**
     * 查询所有用户
     * @return
     */
    List<Map<String,Object>> findAllUser();

    Integer updateIpTimeByid(User user);

    ResData saveOrUpdateUserRole(UserVo user);

    /**
     * 更改用户状态
     * @param user
     * @return
     */
    ResData updateDvailableById(User user);
}
