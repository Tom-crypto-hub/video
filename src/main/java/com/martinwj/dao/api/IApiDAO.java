package com.martinwj.dao.api;

import com.martinwj.entity.Api;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: IApiDAO
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-22
 */
@Repository
public interface IApiDAO {
    /**
     * 查询指定类型的接口列表
     * @param type 接口类型
     * @return
     */
    List<Api> listByType(String type);
}
