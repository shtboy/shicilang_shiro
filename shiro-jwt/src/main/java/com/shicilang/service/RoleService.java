package com.shicilang.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.shicilang.dos.RoleNameJriDo;
import com.shicilang.entity.Role;

import java.util.Set;

/**
 * <p>
 * 角色 服务类
 * </p>
 *
 * @author shicilang
 * @since 2023-02-02
 */
public interface RoleService extends IService<Role> {

    /**
     * 根据账号名查找所有角色信息
     */
    Set<String> getRolesByAccountName(String accountName);

    /**
     * 根据角色名查看对应权限
     */
    Set<RoleNameJriDo> getJurisdictionsByRoleName(String roleName);
}
