package com.shirui.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shirui.common.lang.Result;
import com.shirui.entity.BlogManager;
import com.shirui.service.BlogManagerService;
import com.shirui.util.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Shirui
 * @since 2021-01-23
 */
@RestController
public class BlogManagerController {

    @Autowired
    BlogManagerService blogManagerService;

    @GetMapping("/blogs-manager")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage) {

        Page page = new Page(currentPage, 5);
        IPage pagedata = blogManagerService.page(page, new QueryWrapper<BlogManager>().eq("is_stick", 0).orderByDesc("created"));

        return Result.success(pagedata);
    }

    @GetMapping("/blogs-manager-top")
    public Result list_top(@RequestParam(defaultValue = "1") Integer currentPage) {

        Page page = new Page(currentPage, 10);
        IPage pagedata = blogManagerService.page(page, new QueryWrapper<BlogManager>().eq("is_stick", 1).orderByDesc("weight_num"));

        return Result.success(pagedata);
    }

    @GetMapping("/blog-manager/{id}")
    public Result detail(@PathVariable(name = "id") Long id) {
        BlogManager blogManager = blogManagerService.getById(id);
        Assert.notNull(blogManager, "该博客已被删除");

        return Result.success(blogManager);
    }

    @RequiresAuthentication
    @PostMapping("/blog-manager/edit")
    public Result edit(@Validated @RequestBody BlogManager blogManager) {

        BlogManager temp = null;
        if (blogManager.getId() != null) {

            temp = blogManagerService.getById(blogManager.getId());
            //只编辑自己的文章
            Assert.isTrue(temp.getUserId().longValue() == ShiroUtils.getProfile().getId().longValue(), "没有权限编辑");

        } else {    //若无，则进行添加

            temp = new BlogManager();
            temp.setUserId(ShiroUtils.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
        }

        BeanUtil.copyProperties(blogManager, temp, "id", "userId", "created", "status");
        blogManagerService.saveOrUpdate(temp);

        return Result.success(null);
    }

}
