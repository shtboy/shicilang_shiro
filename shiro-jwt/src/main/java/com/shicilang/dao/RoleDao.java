package com.shicilang.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shicilang.dos.RoleNameJriDo;
import com.shicilang.entity.Role;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

/**
 * <p>
 * 角色 Mapper 接口
 * </p>
 *
 * @author song
 * @since 2023-02-02
 */
public interface RoleDao extends BaseMapper<Role> {

    @Select("        select j.uri,r.role_name from t_permission j inner join t_role_permission rj on j.id = rj.permission_id inner join t_role r on r.id = rj.role_id where r.role_name = #{roleName}\n")
    Set<RoleNameJriDo> getPermissionsByRoleName(String roleName);
}
