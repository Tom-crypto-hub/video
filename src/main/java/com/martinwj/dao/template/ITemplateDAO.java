package com.martinwj.dao.template;

import org.springframework.stereotype.Repository;

/**
 * @ClassName: ITemplateDAO
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
@Repository
public interface ITemplateDAO {
    /**
     * 获取类型对应的模板名称
     * @param type 类型
     * @return
     */
    String selectNameByType(String type);
}
