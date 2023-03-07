package com.shicilang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shicilang.entity.Permission;

import java.util.Set;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author shicilang
 * @since 2023-02-02
 */
public interface PermissionService extends IService<Permission> {
    /**
     * 根据账号名查询权限
     */
    Set<Permission> getJurisdictionByAccountName(String accountName);

}
