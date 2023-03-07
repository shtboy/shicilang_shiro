package com.shicilang.config.resolver;

import com.shicilang.config.permission.UrlPermission;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UrlPermissionResolver implements PermissionResolver {

    private static final Logger logger = LoggerFactory.getLogger(UrlPermissionResolver.class);

    /**
     * 经过调试发现
     * subject.isPermitted(url) 中传入的字符串
     * 和自定义 Realm 中传入的权限字符串集合都要经过这个 resolver
     */
    @Override
    public Permission resolvePermission(String s) {
        logger.info("s => " + s);
        if (s.startsWith("/")) return new UrlPermission(s);
        return new WildcardPermission(s);
    }
}