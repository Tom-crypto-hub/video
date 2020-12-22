package com.martinwj.service.impl;

import com.martinwj.dao.userProfile.IUserProfileDAO;
import com.martinwj.entity.UserProfile;
import com.martinwj.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: UserProfileServiceImpl
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-21
 */
@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private IUserProfileDAO iUserProfileDAO;

    /**
     * 保存用户扩展信息
     * @param userProfile
     */
    public void save(UserProfile userProfile) {
        iUserProfileDAO.update(userProfile);
    }
}
