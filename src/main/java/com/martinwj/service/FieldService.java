package com.martinwj.service;

import com.martinwj.entity.Field;

import java.util.List;

/**
 * @ClassName: FieldService
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-24
 */
public interface FieldService {
    /**
     * 查询字段列表
     */
    public List<Field> list();

    /**
     * 查询分类下的字段列表
     * @param typeId 分类信息主键
     * @return
     */
    public List<Field> listByTypeId(String typeId);

}
