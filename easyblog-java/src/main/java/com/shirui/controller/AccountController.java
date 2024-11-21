package com.shirui.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shirui.common.dto.LoginDto;
import com.shirui.common.lang.Result;
import com.shirui.entity.User;
import com.shirui.mapper.UserMapper;
import com.shirui.service.UserService;
import com.shirui.util.IpUtils;
import com.shirui.util.JwtUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@RestController
public class AccountController {

    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response, HttpServletRequest request) {

        User user = userService.getOne(new QueryWrapper<User>().eq("username", loginDto.getUsername()));
        Assert.notNull(user, "用户不存在");

        //将输入的密码进行md5加密后与数据库里已md5加密的格式的密码进行比对
        if (!user.getPassword().equals(SecureUtil.md5(loginDto.getPassword()))) {
            return Result.fail("账号或密码不正确");     //同时返回账号和密码不正确避免抓包定点爆破密码
        }

        if (user.getStatus().equals(1)) {
            Assert.isTrue(false, "该账号已被冻结");
        }

        String jwt = jwtUtils.generateToken(user.getId());

        //把jwt放在header里方便延期，延期时判断header里是存在jwt后直接刷新即可，如若直接在下面return jwt的话延期需要另外调用另一个接口
        response.setHeader("Authorization", jwt);
        response.setHeader("Access-control-Expose-Headers", "Authorization");

        String ip = IpUtils.getIpAddr(request);
        user.setIp(ip);
        user.setLastLogin(LocalDateTime.now());
        userService.update(user);

        return Result.success(MapUtil.builder().
                put("id", user.getId()).
                put("name", user.getName()).
                put("username", user.getUsername()).
                put("avatar", user.getAvatar()).
                put("email", user.getEmail()).
                map()
        );
    }

    @RequiresAuthentication
    @GetMapping("/logout")
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.success(null);
    }


    /*
     *      注册功能
     */

    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        String username = user.getUsername();
        String name = user.getName();
        String password = SecureUtil.md5(user.getPassword());
        //验证是否重名
        Integer integer1 = userMapper.selectCount(new QueryWrapper<User>().eq("username", username));
        if (integer1 >= 1) {
            Assert.isTrue(false, "该账号已存在");
        }
        Integer integer2 = userMapper.selectCount(new QueryWrapper<User>().eq("name", name));
        if (integer2 >= 1) {
            Assert.isTrue(false, "该昵称已存在");
        }
        user.setStatus(0);
        user.setPassword(password);
        user.setCreated(LocalDateTime.now());
        userService.insert(user);
        return Result.success(user);
    }

    @GetMapping("user-search")
    public Result searchUser(@RequestParam(defaultValue = "1") Integer currentPage, @RequestParam String msg) {

        Page page = new Page(currentPage, 6);

        //进行模糊搜索
        IPage pagedata = userService.page(page, new QueryWrapper<User>().like("name", msg).orderByAsc("id"));

        return Result.success(pagedata);
    }

    @GetMapping("get-user")
    public Result userData(@RequestParam Integer id) {
        User user = userService.getOne(new QueryWrapper<User>().eq("id", id));

        return Result.success(user);
    }
}
