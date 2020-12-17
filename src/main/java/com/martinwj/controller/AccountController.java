package com.martinwj.controller;

import com.martinwj.EnumClass.MsgTips;
import com.martinwj.entity.Account;
import com.martinwj.exception.SysException;
import com.martinwj.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @ClassName: AccountController
 * @Description: TODO 账户web
 * @author: martin-wj
 * @createDate: 2020-12-01
 */
@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @RequestMapping("/findAll.action")
    public String findAll(Model model) throws SysException {
        System.out.println("表现层，查询所有的方法！");

        // 测试404和500和消息提示页面是否可用
//        try {
//            int i = 10 / 0;
//            System.out.println("...");
//        } catch (Exception e) {
//            throw new SysException("出现了错误");
//        }

        List<Account> list = accountService.findAccount();
        System.out.println(list);
        for(Account account : list){
            System.out.println(account);
        }
        model.addAttribute("accountList", list);
        return "list";
    }

    @RequestMapping("/save")
    private void saveAccount(Account account, HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("保存账户信息");
        accountService.saveAccount(account);
        // 跳转到提示信息页面
        response.sendRedirect(request.getContextPath() + "/account/findAll");
        return;
    }



}
