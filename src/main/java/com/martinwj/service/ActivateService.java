package com.martinwj.service;

import com.martinwj.entity.Activate;

/**
 * @ClassName: ActivateService
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
public interface ActivateService {

    /**
     * 保存认证记录
     * @param activate
     */
    public void save(Activate activate);
}
