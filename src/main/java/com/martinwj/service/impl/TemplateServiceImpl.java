package com.martinwj.service.impl;

import com.martinwj.dao.template.ITemplateDAO;
import com.martinwj.entity.Template;
import com.martinwj.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
     * 根据类型查询已选的模板
     * @param type 类型
     * @return
     */
    public Template selectByType(String type) {
        return iTemplateDAO.selectByType(type);
    }


    /**
     * 获取类型对应的模板名称
     * @param type 类型
     * @return
     */
    public String selectNameByType(String type) {
        return iTemplateDAO.selectNameByType(type);
    }

    @Override
    public List<Template> selectAll() {
        return iTemplateDAO.selectAll();
    }
}
