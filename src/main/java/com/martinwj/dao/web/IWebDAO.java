package com.martinwj.dao.web;

import com.martinwj.entity.Web;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: IWebDao
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
@Repository
public interface IWebDAO {


    /**
     * 查询网站信息
     * @return
     */
    Web selectWebInfo();

    /**
     * 保存站点信息配置
     * @param web
     */
    int update(Web web);
}
