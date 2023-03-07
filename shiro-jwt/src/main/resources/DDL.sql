DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
                           `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                           `account_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '登录用户名',
                           `account_password` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '登录密码',
                           `secret` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '加密盐',
                           PRIMARY KEY (`id`),
                           UNIQUE KEY `uniq_accounname` (`account_name`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='账号表';

DROP TABLE IF EXISTS `account_role`;
CREATE TABLE `account_role` (
                                `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
                                `account_id` bigint(20) DEFAULT NULL COMMENT '账号id',
                                `role_id` bigint(20) DEFAULT NULL COMMENT '角色id',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='账号-角色 关联表';

DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
                              `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                              `permission_portrayal` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '权限描述',
                              `parent_id` bigint(20) unsigned DEFAULT NULL COMMENT '父权限ID',
                              `uri` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '请求uri',
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='权限';

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
                        `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
                        `role_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '角色名',
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色';


DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
                                   `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                   `role_id` bigint(20) NOT NULL COMMENT '角色ID',
                                   `permission_id` bigint(20) NOT NULL COMMENT '权限ID',
                                   PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色-权限 关联表';
-- 初始化账号
insert into account(account_name,account_password) values ('root','root');