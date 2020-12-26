package com.martinwj.service.impl;/*
 * @Author oscar
 * @Date 2020/12/25 14:48
 * @Version 1.0
 */

import com.martinwj.dao.user.user_info.IUserInfoDAO;
import com.martinwj.entity.User;
import com.martinwj.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private IUserInfoDAO iUserInfoDAO;

    @Override
    public List<User> listBan(String groupId, String keyWord) {
        return iUserInfoDAO.listBan(groupId,keyWord);
    }

    @Override
    public List<User> listNormal(String groupId, String keyWord  ) {
        return iUserInfoDAO.listNormal(groupId,keyWord);
    }

    @Override
    public List<User> listNotActive(String groupId, String keyWord ) {
        return iUserInfoDAO.listNotActive(groupId,keyWord);
    }

    @Override
    public int update(String[] idArr, String status) {
        return iUserInfoDAO.update(idArr,status);
    }


}
