package com.martinwj.service;/*
 * @Author oscar
 * @Date 2020/12/25 9:57
 * @Version 1.0
 */

import com.martinwj.entity.Group;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GroupService {
    /**
     * 查询用户组列表
     */
    List<Group> list();

    /**
     * 插入用户信息
     * @param group
     * @return
     */
    int insert(Group group);

    /**
     * 更新用户信息
     * @param group
     * @return
     */
    int update(Group group);

    /**
     * 批量删除用户信息
     * @param idArr
     * @return
     */
    int deleteById(@Param("idArr") String[] idArr);

    /**
     * 保存
     * @return
     */
    int save(Group group);
}

