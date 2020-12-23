package com.martinwj.service.impl;

import com.martinwj.dao.web.IWebDAO;
import com.martinwj.entity.Web;
import com.martinwj.service.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: WebServiceImpl
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
@Service
public class WebServiceImpl implements WebService {
    @Autowired
    private IWebDAO iWebDAO;

    /**
     * 查询网站信息
     * @return
     */
    public Web selectWebInfo() {
        return iWebDAO.selectWebInfo();
    }

    /**
     * 保存站点信息配置
     * @param web
     */
    public void save(Web web) {
        iWebDAO.update(web);
    }
}
