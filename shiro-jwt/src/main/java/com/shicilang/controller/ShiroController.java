package com.shicilang.controller;

import cn.hutool.json.JSONUtil;
import com.shicilang.entity.Account;
import com.shicilang.util.secure.SecureUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

/**
 * @author shicilang
 * @date 2023/3/7 11:14
 * @Version 1.0
 */
@RestController
@RequestMapping("/")
public class ShiroController {
    @PostMapping("login")
    public String login(@RequestBody Account account) {
        // 验证账号密码
        //创建一个shiro的Subject对象，这个对象始于登录接口,验证通过后shiro保存正确的账号密码,后面再次登录将账号密码封装好token(可不是jwt的密文token)对象扔给shiro做比对即可
        Subject subject = SecurityUtils.getSubject();
        //创建一个用户账号和密码的Token对象，并设置用户输入的账号和密码
        UsernamePasswordToken token = new UsernamePasswordToken(account.getAccountName(), account.getAccountPassword());
        try {
            //调用login后，Shiro就会自动执行自定义的Realm中的认证方法
            subject.login(token);
            Session session = subject.getSession();
            if (session != null) session.setTimeout(1000 * 60); // 非常关键 这里是shiro缓存session判断登录状态的失效时间,与token失效时间一致即可,不做扩展
            return "登录成功" + SecureUtil.mkToken(account.getAccountName());
        } catch (UnknownAccountException e) {
            //表示用户的账号错误，myRealm的认证方法抛出
            return "账号不存在";
        } catch (LockedAccountException e) {
            //表示用户的账号被锁定，myRealm的认证方法抛出
            return "账号被锁定";
        } catch (IncorrectCredentialsException e) {
            //表示用户的密码错误，myRealm的认证方法抛出
            return "账号或密码错误";
        }
    }

    //@RequiresRoles("systemAdmin")

    //@RequiresPermissions("lll")
    @RequiresRoles("sysAdmin")
    @GetMapping("testApi")
    public String testApi() {
        return "testApi正常访问";
    }
}
