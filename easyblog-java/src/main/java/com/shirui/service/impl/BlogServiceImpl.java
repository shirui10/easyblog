package com.shirui.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shirui.entity.Blog;
import com.shirui.mapper.BlogMapper;
import com.shirui.service.BlogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Shirui
 * @since 2021-01-13
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {
}
