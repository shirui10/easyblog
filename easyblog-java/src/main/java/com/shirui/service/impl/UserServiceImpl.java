package com.shirui.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shirui.entity.User;
import com.shirui.mapper.UserMapper;
import com.shirui.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Shirui
 * @since 2021-01-13
 */
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void update(User user) {
        userMapper.updateById(user);
    }

    @Override
    public void insert(User user) {
        userMapper.insert(user);
    }
}
