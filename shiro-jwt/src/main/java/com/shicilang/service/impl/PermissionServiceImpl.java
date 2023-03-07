package com.shicilang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shicilang.dao.PermissionDao;
import com.shicilang.entity.Permission;
import com.shicilang.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author shicilang
 * @since 2023-02-02
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionDao, Permission> implements PermissionService {
    @Autowired
    private PermissionDao jurisdictionDao;
    @Override
    public Set<Permission> getJurisdictionByAccountName(String accountName) {
        return jurisdictionDao.getPermissionByAccountName(accountName);
    }
}
