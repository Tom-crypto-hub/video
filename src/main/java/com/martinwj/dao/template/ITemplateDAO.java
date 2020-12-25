package com.martinwj.dao.template;

import com.martinwj.entity.Template;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: ITemplateDAO
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
@Repository
public interface ITemplateDAO {

    /**
     * 根据类型查询已选的模板
     * @param type 类型
     * @return
     */
    Template selectByType(String type);

    /**
     * 获取类型对应的模板名称
     * @param type 类型
     * @return
     */
    String selectNameByType(String type);

    /**
     * 查询所有的模板
     * @return
     */
    List<Template> selectAll();
}
