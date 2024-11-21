package com.shirui.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shirui.common.lang.Result;
import com.shirui.entity.Blog;
import com.shirui.service.BlogService;
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
 * @since 2021-01-13
 */
@RestController
public class BlogController {

    @Autowired
    BlogService blogService;

    @GetMapping("/blogs")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage) {

        Page page = new Page(currentPage, 5);
        IPage pagedata = blogService.page(page, new QueryWrapper<Blog>().eq("is_stick", 0).orderByDesc("created"));

        return Result.success(pagedata);
    }


    @GetMapping("/blogs-person")
    public Result list_person(@RequestParam(defaultValue = "1") Integer currentPage, @RequestParam Long userId) {

        Page page = new Page(currentPage, 5);
        IPage pagedata = blogService.page(page, new QueryWrapper<Blog>().eq("user_id", userId).orderByDesc("created"));

        return Result.success(pagedata);
    }

    @GetMapping("/blogs-top")
    public Result list_top(@RequestParam(defaultValue = "1") Integer currentPage) {

        Page page = new Page(currentPage, 10);
        IPage pagedata = blogService.page(page, new QueryWrapper<Blog>().eq("is_stick", 1).orderByDesc("weight_num"));

        return Result.success(pagedata);
    }

    @GetMapping("/blog/{id}")
    public Result detail(@PathVariable(name = "id") Long id) {
        Blog blog = blogService.getById(id);
        Assert.notNull(blog, "该博客已被删除");

        return Result.success(blog);
    }

    @RequiresAuthentication
    @PostMapping("/blog/edit")
    public Result edit(@Validated @RequestBody Blog blog) {

        Blog temp = null;
        if (blog.getId() != null) {

            temp = blogService.getById(blog.getId());
            //只编辑自己的文章
            Assert.isTrue(temp.getUserId().longValue() == ShiroUtils.getProfile().getId().longValue(), "没有权限编辑");

        } else {    //若无，则进行添加

            temp = new Blog();
            temp.setUserId(ShiroUtils.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
        }

        BeanUtil.copyProperties(blog, temp, "id", "userId", "created", "status");
        blogService.saveOrUpdate(temp);

        return Result.success(null);
    }

    @GetMapping("/blogs-search")
    public Result searchBlog(@RequestParam(defaultValue = "1") Integer currentPage, @RequestParam String msg) {

        Page page = new Page(currentPage, 5);

        //进行模糊搜索
        IPage pagedata = blogService.page(page, new QueryWrapper<Blog>().like("title", msg).orderByDesc("created"));

        return Result.success(pagedata);
    }
}
