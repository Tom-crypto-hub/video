package com.martinwj.service;

import com.martinwj.entity.FieldProfile;

import java.util.List;

/**
 * @ClassName: FieldProfileService
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-25
 */
public interface FieldProfileService {
    /**
     * 根据字段主键查询字段详情列表
     * @param fieldId 字段主键
     */
    public List<FieldProfile> listByFieldId(String fieldId);

    void save(String fieldId, List<FieldProfile> fieldProfileList);

    void delete(String fieldId, String[] idArr);
}
