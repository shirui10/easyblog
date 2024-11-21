package com.shirui.service.impl;

import com.shirui.entity.Suggestions;
import com.shirui.mapper.SuggestionsMapper;
import com.shirui.service.SuggestionsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Shirui
 * @since 2021-02-22
 */
@Service
public class SuggestionsServiceImpl extends ServiceImpl<SuggestionsMapper, Suggestions> implements SuggestionsService {

    @Autowired
    SuggestionsMapper suggestionsMapper;

    @Override
    public void insert(Suggestions suggestions) {
        suggestionsMapper.insert(suggestions);
    }
}
