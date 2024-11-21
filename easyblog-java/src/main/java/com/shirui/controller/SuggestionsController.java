package com.shirui.controller;


import com.shirui.common.lang.Result;
import com.shirui.entity.Suggestions;
import com.shirui.service.SuggestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Shirui
 * @since 2021-02-22
 */
@RestController
public class SuggestionsController {

    @Autowired
    SuggestionsService suggestionsService;


    @PostMapping("/suggestions")
    public Result register(@RequestBody Suggestions suggestions) {
        suggestions.setTime(LocalDateTime.now());
        suggestionsService.insert(suggestions);
        return Result.success(suggestions);
    }
}
