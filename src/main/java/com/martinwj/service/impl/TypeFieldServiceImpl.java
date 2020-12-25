package com.martinwj.service.impl;

import com.martinwj.dao.typefield.ITypeFieldDAO;
import com.martinwj.service.TypeFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: TypeFieldServiceImpl
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-24
 */
@Service
public class TypeFieldServiceImpl implements TypeFieldService {

    @Autowired
    private ITypeFieldDAO iTypeFieldDAO;

    /**
     * 检索指定字段是否必填
     * @param typeId 分类信息主键
     * @param varName 字段变量名
     * @return
     */
    public String selectIsRequired(String typeId, String varName) {
        return iTypeFieldDAO.selectIsRequired(typeId, varName);
    }
}
