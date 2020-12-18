package com.martinwj.dao.userProfile;

import com.martinwj.entity.UserProfile;

import java.util.Map;

/**
 * @ClassName: IUserProfileDAO
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
public interface IUserProfileDAO {
    /**
     * 注册新用户
     * @param userProfile
     */
    int insert(UserProfile userProfile);

    /**
     * 查询指定用户组的数量
     * @param groupId 用户组主键
     * @return
     */
    int countByGroupId(String groupId);

    /**
     * 保存用户信息
     * @param userProfile
     */
    int update(UserProfile userProfile);

    /**
     * 批量更新用户
     * @param param
     */
    int batchUpdate(Map<String, Object> param);

}
