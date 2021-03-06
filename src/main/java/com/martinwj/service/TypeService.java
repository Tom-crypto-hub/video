package com.martinwj.service;

import com.martinwj.entity.Type;

import java.util.List;

/**
 * @ClassName: TypeService
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-23
 */
public interface TypeService {

    /**
     * 查询分类列表
     */
    List<Type> list();

    /**
     * 根据主键查询分类信息
     * @param id 主键
     * @return
     */
    Type selectById(String id);
}
