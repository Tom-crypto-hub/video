package com.martinwj.service;

import com.martinwj.entity.Account;

import java.util.List;

/**
 * @ClassName: AccountService
 * @Description: TODO 账户业务接口类
 * @author: martin-wj
 * @createDate: 2020-12-01
 */
public interface AccountService {

    /**
     * 查询所有账户信息
     * @return
     */
    List<Account> findAccount();

    /**
     * 保存账户信息
     * @param account
     */
    void saveAccount(Account account);

}
