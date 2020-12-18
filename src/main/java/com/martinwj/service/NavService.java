package com.martinwj.service;

import com.martinwj.entity.Nav;

import java.util.List;

/**
 * @ClassName: NavService
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
public interface NavService {
    /**
     * 可用导航列表
     * @return
     */
    public List<Nav> listIsUse();
}
