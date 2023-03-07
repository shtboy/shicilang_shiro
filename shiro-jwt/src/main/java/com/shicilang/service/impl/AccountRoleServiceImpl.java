package com.shicilang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shicilang.dao.AccountRoleDao;
import com.shicilang.entity.AccountRole;
import com.shicilang.service.AccountRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员-角色 关联表 服务实现类
 * </p>
 *
 * @author shicilang
 * @since 2023-02-02
 */
@Service
public class AccountRoleServiceImpl extends ServiceImpl<AccountRoleDao, AccountRole> implements AccountRoleService {

}
