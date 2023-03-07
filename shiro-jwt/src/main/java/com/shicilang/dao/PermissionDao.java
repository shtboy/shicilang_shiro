package com.shicilang.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shicilang.entity.Permission;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

/**
 * <p>
 * 权限 Mapper 接口
 * </p>
 *
 * @author shicilang
 * @since 2023-02-02
 */
public interface PermissionDao extends BaseMapper<Permission> {

    @Select(" SELECT j.* FROM t_account a\n" +
            "                            INNER JOIN t_account_role ar\n" +
            "                                       ON a.id = ar.account_id\n" +
            "                            INNER JOIN t_role r ON r.`id`\n" +
            "            = ar.`role_id`\n" +
            "                            INNER JOIN t_role_permission rj ON r.id = rj.`role_id`\n" +
            "                            INNER JOIN t_permission j ON j.`id` = rj.`permission_id` where a.account_name = #{accountName};")
    Set<Permission> getPermissionByAccountName(String accountName);

}
