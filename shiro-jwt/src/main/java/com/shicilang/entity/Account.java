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
 * 管理员管理表
 * </p>
 *
 * @author song
 * @since 2023-02-02
 */
@Getter
@Setter
@Accessors(chain = true)
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 登录用户名
     */
    private String accountName;

    /**
     * 登录密码
     */
    @TableField("account_password")
    private String accountPassword;

    /**
     * 加密盐
     */
    @TableField("secret")
    private String secret;

    Short status;
}
