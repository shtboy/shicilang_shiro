package com.shicilang.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shicilang.dao.RolePermissionDao;
import com.shicilang.entity.RolePermission;
import com.shicilang.service.RolePermissionService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色-权限 关联表 服务实现类
 * </p>
 *
 * @author shicilang
 * @since 2023-02-02
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionDao, RolePermission> implements RolePermissionService {

}
