package com.shirui.service;

import com.shirui.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Shirui
 * @since 2021-01-13
 */
public interface UserService extends IService<User> {
    void insert(User user);

    void update(User user);
}
