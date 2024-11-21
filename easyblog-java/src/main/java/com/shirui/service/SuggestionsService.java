package com.shirui.service;

import com.shirui.entity.Suggestions;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Shirui
 * @since 2021-02-22
 */
public interface SuggestionsService extends IService<Suggestions> {

    void insert(Suggestions suggestions);

}
