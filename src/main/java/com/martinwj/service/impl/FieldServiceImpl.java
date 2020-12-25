package com.martinwj.service.impl;

import com.martinwj.dao.field.IFieldDAO;
import com.martinwj.entity.Field;
import com.martinwj.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: FieldServiceImpl
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-24
 */
@Service
public class FieldServiceImpl implements FieldService {

    @Autowired
    private IFieldDAO iFieldDAO;

    /**
     * 查询字段列表
     */
    public List<Field> list() {
        return iFieldDAO.list();
    }

    /**
     * 查询分类下的字段列表
     * @param typeId 分类信息主键
     * @return
     */
    public List<Field> listByTypeId(String typeId) {
        return iFieldDAO.listByTypeId(typeId);
    }

}
