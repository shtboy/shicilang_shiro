package com.shicilang.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shicilang.dao.AccountDao;
import com.shicilang.entity.Account;
import com.shicilang.service.AccountService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员管理表 服务实现类
 * </p>
 *
 * @author shicilang
 * @since 2023-02-02
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountDao, Account> implements AccountService {

}
