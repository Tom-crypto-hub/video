package com.martinwj.service.impl;

import com.martinwj.dao.tag.ITagDAO;
import com.martinwj.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: TagServiceImpl
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-24
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private ITagDAO iTagDAO;

    /**
     * 根据主键数组，查询名称集合
     * @param idArr 主键数组
     * @return
     */
    public List<String> listNameByIdArr(String[] idArr) {
        return iTagDAO.listNameByIdArr(idArr);
    }

}
