package com.martinwj.service.impl;

import com.martinwj.dao.type.ITypeDAO;
import com.martinwj.entity.Type;
import com.martinwj.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: TypeServiceImpl
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-23
 */
@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private ITypeDAO iTypeDAO;

    /**
     * 查询分类列表
     */
    public List<Type> list() {
        return iTypeDAO.list();
    }

    /**
     * 根据主键查询分类信息
     * @param id 主键
     * @return
     */
    public Type selectById(String id) {
        return iTypeDAO.selectById(id);
    }

}
