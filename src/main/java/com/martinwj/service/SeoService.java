package com.martinwj.service;

import com.martinwj.entity.Seo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName: SeoService
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
public interface SeoService {
    /**
     * 根据类型，查询seo信息
     * @param type
     * @return
     */
    Seo selectByType(String type);

    /**
     * 保存seo信息配置
     * @param seo
     */
    public void save(Seo seo);

}
