package com.martinwj.service;

import com.martinwj.entity.Web;

/**
 * @ClassName: WebService
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
public interface WebService {
    /**
     * 查询网站信息
     * @return
     */
    Web selectWebInfo();

    /**
     * 保存站点信息配置
     * @param web
     */
    public void save(Web web);
}
