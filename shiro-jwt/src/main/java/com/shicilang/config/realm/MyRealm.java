package com.shicilang.config.realm;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shicilang.entity.Account;
import com.shicilang.entity.Permission;
import com.shicilang.service.AccountService;
import com.shicilang.service.PermissionService;
import com.shicilang.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author shicilang
 * @date 2023/3/7 10:27
 * @Version 1.0
 */
@Slf4j
public class MyRealm extends AuthorizingRealm {
    @Autowired
    private AccountService adminService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    /**
     * 用来做授权   人话:只管理权限,查表的地方
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("---------------- 执行 Shiro 权限获取 ---------------------");
        //从Shiro中获取用户名
        Subject subject = SecurityUtils.getSubject();
        Account currentAccount = (Account) subject.getPrincipal(); // 因为认证时principal用的是account对象,所以可以直接取出来强转
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 数据库查询权限
        Set<Permission> jurisdictionByAccountName = permissionService.getJurisdictionByAccountName(currentAccount.getAccountName());
        Set<String> jurisdictions = jurisdictionByAccountName.stream().map(Permission::getUri).collect(Collectors.toSet());
        log.info("当前用户:{} 拥有权限:{}", currentAccount.getAccountName(), jurisdictions.toString());
        //设置accountName拥有的权限
        authorizationInfo.addStringPermissions(jurisdictions);
        //设置accountName拥有的角色
        Set<String> roles = roleService.getRolesByAccountName(currentAccount.getAccountName());
        authorizationInfo.setRoles(roles);
        return authorizationInfo;
    }

    /**
     * 用来做认证   人话:校验账号密码是否正确的地方
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.debug("===============Shiro身份认证开始============doGetAuthenticationInfo==========");
        //将AuthenticationToken强转成UsernamePasswordToken 这样获取账号和密码更加的方便
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        Object credentials = authenticationToken.getCredentials();
        //获取用户在浏览器中输入的账号
        String userName = token.getUsername();
        String password = new String(token.getPassword());
        //认证账号,正常情况我们需要这里从数据库中获取账号的信息，以及其他关键数据，例如账号是否被冻结等等
        Account admin = adminService.getOne(new LambdaQueryWrapper<Account>().eq(Account::getAccountName, userName));
        if (admin == null)   throw new UnknownAccountException("账号不存在");//判断用户账号是否存在
        if (admin.getStatus() == -1) throw new LockedAccountException("账号被冻结");
        if (!admin.getAccountPassword().equals(password)) throw new IncorrectCredentialsException("账号或密码错误");
        //密码认证  shiro做
        return new SimpleAuthenticationInfo(admin, credentials, getName());
    }
}
