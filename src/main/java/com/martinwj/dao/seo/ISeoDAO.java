package com.martinwj.dao.seo;

import com.martinwj.entity.Seo;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: ISeoDAO
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
@Repository
public interface ISeoDAO {
    /**
     * 根据类型，查询seo信息
     * @param type 类型
     * @return
     */
    Seo selectByType(String type);

    /**
     * seo信息
     * @param seo
     */
    int update(Seo seo);
}
