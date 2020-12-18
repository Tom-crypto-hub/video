package com.martinwj.service.impl;

import com.martinwj.dao.template.ITemplateDAO;
import com.martinwj.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: TemplateServiceImpl
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
@Service
public class TemplateServiceImpl implements TemplateService {
    @Autowired
    private ITemplateDAO iTemplateDAO;

    /**
     * 获取类型对应的模板名称
     * @param type 类型
     * @return
     */
    public String selectNameByType(String type) {
        return iTemplateDAO.selectNameByType(type);
    }
}
