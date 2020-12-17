package com.martinwj.service.impl;

import com.martinwj.dao.AccountDao;
import com.martinwj.entity.Account;
import com.martinwj.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: AccountServiceImpl
 * @Description: TODO 账户业务实现类
 * @author: martin-wj
 * @createDate: 2020-12-01
 */
@Service("accountService")
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Override
    public List<Account> findAccount() {
        System.out.println("业务层查询所有账户");
        System.out.println(accountDao);
        return accountDao.findAccount();
    }

    @Override
    public void saveAccount(Account account) {
        System.out.println("业务层保存账户");
        accountDao.saveAccount(account);
    }
}
