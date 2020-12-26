package com.martinwj.service;/*
 * @Author oscar
 * @Date 2020/12/25 14:46
 * @Version 1.0
 */

import com.martinwj.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserInfoService {
    /**
     * 查询吧被封禁用户列表
     */
    List<User> listBan(String groupId,String keyWord);
    /**
     * 查询正常用户列表
     */
    List<User> listNormal(String groupId,String keyWord);
    /**
     * 查询未激活用户列表
     */
    List<User> listNotActive(String groupId,String keyWord);

    /**
     * 批量更新用户
     * 修改status
     * @param idArr
     * @param status
     * @return
     */
    int update(@Param("idArr") String[] idArr, @Param("status") String status);
}
