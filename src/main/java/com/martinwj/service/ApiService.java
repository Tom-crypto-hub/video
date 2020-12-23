package com.martinwj.service;

import com.martinwj.entity.Api;
import com.martinwj.exception.SysException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: ApiService
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-22
 */
public interface ApiService {

    /**
     * 查询指定类型的接口列表
     * @param type 接口类型
     * @return
     */
    public List<Api> listByType(String type);


}
