package com.martinwj.dao.group;/*
 * @Author oscar
 * @Date 2020/12/25 9:47
 * @Version 1.0
 */

import com.martinwj.entity.Group;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IGroupDAO {
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
