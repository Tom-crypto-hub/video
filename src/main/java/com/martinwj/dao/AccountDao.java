package com.martinwj.dao;

import com.martinwj.entity.Account;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: AccountDao
 * @Description: TODO 账户接口类
 * @author: martin-wj
 * @createDate: 2020-12-01
 */
@Repository
public interface AccountDao {

    /**
     * 查询所有账户信息
     * @return
     */
    @Select("select * from account")
    List<Account> findAccount();

    /**
     * 保存账户信息
     * @param account
     */
    @Insert("insert into account(name, money) values(#{name}, #{money})")
    void saveAccount(Account account);

}
