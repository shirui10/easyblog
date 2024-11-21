package com.shirui.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shirui.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Shirui
 * @since 2021-01-13
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
