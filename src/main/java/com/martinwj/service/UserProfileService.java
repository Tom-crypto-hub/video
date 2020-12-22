package com.martinwj.service;

import com.martinwj.entity.UserProfile;

/**
 * @ClassName: UserProfileService
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-21
 */
public interface UserProfileService {
    /**
     * 保存用户扩展信息
     * @param userProfile
     */
    public void save(UserProfile userProfile);
}
