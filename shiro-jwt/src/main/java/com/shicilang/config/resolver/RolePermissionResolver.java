package com.shicilang.config.resolver;


import com.shicilang.config.permission.UrlPermission;
import com.shicilang.dos.RoleNameJriDo;
import com.shicilang.service.RoleService;
import org.apache.shiro.authz.Permission;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

/**角色-权限 解析器  相当于一次幂等校验  考虑优化取消
 * @author Song
 * @date 2023/2/7 14:00
 * @Version 1.0
 */
public class RolePermissionResolver implements org.apache.shiro.authz.permission.RolePermissionResolver {
    @Autowired
    RoleService roleService;

    @Override
    public Collection<Permission> resolvePermissionsInRole(String roleString) {
        Set<RoleNameJriDo> jurisdictionsByRoleName = roleService.getJurisdictionsByRoleName(roleString);
        Collection<Permission> permissions =new ArrayList<>();
        for (RoleNameJriDo roleNameJriDo : jurisdictionsByRoleName) {
            if ((roleString+"").equals(roleNameJriDo.getRoleName())){
                UrlPermission urlPermission = new UrlPermission(roleNameJriDo.getUri());
                permissions.add(urlPermission);
            }
        }
        return permissions;
    }
}
