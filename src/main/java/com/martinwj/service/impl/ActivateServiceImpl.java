package com.martinwj.service.impl;

import com.martinwj.dao.activate.IActivateDAO;
import com.martinwj.entity.Activate;
import com.martinwj.service.ActivateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: ActivateServiceImpl
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
@Service
public class ActivateServiceImpl implements ActivateService {

    @Autowired
    private IActivateDAO iActivateDAO;

    /**
     * 保存认证记录
     * @param activate
     */
    public void save(Activate activate) {
        // 1.0 根据用户id和验证类型，判断认证是否已存在
        Activate activateTemp = iActivateDAO.selectByUserIdAndType(activate.getUserId(), activate.getType());

        // 判断是否已存在
        if (activateTemp==null) {
            // 不存在，做插入操作
            iActivateDAO.insert(activate);
        } else {
            // 存在，做更新操作
            activate.setId(activateTemp.getId());

            iActivateDAO.update(activate);
        }
    }

}
