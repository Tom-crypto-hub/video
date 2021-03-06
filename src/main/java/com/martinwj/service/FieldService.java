package com.martinwj.service;

import com.martinwj.entity.Field;
import com.martinwj.exception.SysException;

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

    /**
     * 保存字段
     * @param fieldList
     * @throws SysException
     */
    void save(List<Field> fieldList) throws SysException;

    /**
     * 获取分类筛选字段的信息2
     * @param typeId 分类信息的主键
     * @param fieldName 字段名
     * @param fieldValue 字段选项名
     * @return
     */
    List<Field> getListField(String typeId, String fieldName, String fieldValue);
}
