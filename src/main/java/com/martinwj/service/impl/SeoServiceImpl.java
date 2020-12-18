package com.martinwj.service.impl;

import com.martinwj.dao.seo.ISeoDAO;
import com.martinwj.entity.Seo;
import com.martinwj.service.SeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: SeoServiceImpl
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
@Service
public class SeoServiceImpl implements SeoService {

    @Autowired
    private ISeoDAO iSeoDAO;

    /**
     * 根据类型，查询seo信息
     * @param type
     * @return
     */
    public Seo selectByType(String type) {
        return iSeoDAO.selectByType(type);
    }

    @Override
    public void save(Seo seo) {
        iSeoDAO.update(seo);
    }

}
