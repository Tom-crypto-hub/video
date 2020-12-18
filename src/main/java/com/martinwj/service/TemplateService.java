package com.martinwj.service;

/**
 * @ClassName: TemplateService
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
public interface TemplateService {
    /**
     * 获取类型对应的模板名称
     * @param type 类型
     * @return
     */
    public String selectNameByType(String type);
}
