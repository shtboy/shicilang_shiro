package com.shicilang.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shicilang.dao.AccountDao;
import com.shicilang.dao.RoleDao;
import com.shicilang.dos.RoleNameJriDo;
import com.shicilang.entity.Role;
import com.shicilang.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author shicilang
 * @since 2023-02-02
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleDao, Role> implements RoleService {
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private RoleDao roleDao;


    @Override
    public Set<String> getRolesByAccountName(String adminName) {
        return accountDao.getRolesByAccountName(adminName);
    }

    @Override
    public Set<RoleNameJriDo> getJurisdictionsByRoleName(String roleName) {
        return roleDao.getPermissionsByRoleName(roleName);
    }
}
