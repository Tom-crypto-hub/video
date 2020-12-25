package com.martinwj.service;

import com.martinwj.entity.Template;

import java.util.List;

/**
 * @ClassName: TemplateService
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
public interface TemplateService {
    /**
     * 根据类型查询已选的模板
     * @param type 类型
     * @return
     */
    public Template selectByType(String type);


    /**
     * 获取类型对应的模板名称
     * @param type 类型
     * @return
     */
    public String selectNameByType(String type);


    /**
     * 查询所有的模板信息
     * @return
     */
    List<Template> selectAll();
}
