package com.shicilang.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 账号-角色
 * </p>
 *
 * @author song
 * @since 2023-02-02
 */
@Getter
@Setter
@Accessors(chain = true)
public class AccountRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 账号idid
     */

    private Long accountId;

    /**
     * 角色id
     */
    @TableField("role_id")
    private Long roleId;


}
