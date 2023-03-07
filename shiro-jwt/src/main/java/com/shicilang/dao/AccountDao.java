package com.shicilang.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shicilang.entity.Account;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

/**
 * <p>
 * 管理员管理表 Mapper 接口
 * </p>
 *
 * @author shicilang
 * @since 2023-02-02
 */
public interface AccountDao extends BaseMapper<Account> {

    @Select("        select r.`role_name`\n" +
            "        from t_account a\n" +
            "                 inner join t_account_role ar on a.id = ar.account_id\n" +
            "                 inner join t_role r on r.`id` = ar.`role_id` where a.account_name = #{accountName};\n" +
            "        -- 根据账号名查询角色")
    Set<String> getRolesByAccountName(String accountName);

}
